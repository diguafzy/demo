package com.demo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.demo.dao.ZhiHuUserDao;
import com.demo.model.ZhiHuUser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ZhiHuCrawler {

	private final static String sourceSeed = "https://www.zhihu.com/people/zhongqing";
	private final static String baseUrl = "https://www.zhihu.com/people/";
	private final static int getUserNum = 100;
	private static volatile ArrayList<String> userIdList = new ArrayList<String>();
	private static LinkedBlockingQueue<String> urlQueue = new LinkedBlockingQueue<String>();
	private static ZhiHuUserDao zhiHuUserDao = null;

	public static void main(String[] args) {
		ZhiHuCrawler zhiHuCrawler = new ZhiHuCrawler();
		zhiHuCrawler.crawling(sourceSeed);
	}

	public ZhiHuCrawler () {
		 ApplicationContext context = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");   
		 zhiHuUserDao = (ZhiHuUserDao) context.getBean("zhiHuUserDao");
	}
	
	private void crawling(String seed) {

		long last = System.currentTimeMillis();
		
		urlQueue.add(seed);
		crawling();

		//创建线程池
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);
		final CountDownLatch latch = new CountDownLatch(5);
		for(int i=0;i<5;i++) {

			cachedThreadPool.execute(new Runnable() {

				public void run() {
					System.out.println("线程启动");
					try {
						while(!urlQueue.isEmpty()) {

					        //控制获取用户数量
					        if(userIdList.size()>=getUserNum) {
					        	break;
					        }
					        
							crawling();
						}
					} catch(Exception e) {
						//爬取出错线程会不断减少，速度越来越慢
						System.out.println(e);
					} finally {

						latch.countDown();
						System.out.println("剩余线程数量"+latch.getCount());
					}
				}
				
			});
		}
		
		try {
			latch.await();

			cachedThreadPool.shutdown();  
	   
	        if(!cachedThreadPool.awaitTermination(60, TimeUnit.SECONDS)){  
	            // 超时的时候向线程池中所有的线程发出中断(interrupted)。  
	        	cachedThreadPool.shutdownNow();  
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        System.out.println("耗时：" + (System.currentTimeMillis() - last));
	}

	private void crawling() {

        String visitUrl = urlQueue.poll();

        String[] temp = visitUrl.split("/");
        String userId = temp[temp.length-1];
        
        if(userId.isEmpty()) return;
        if(!userIdList.contains(userId)) {
        	userIdList.add(userId);
        }
        
        Document doc = getDoc(visitUrl);
		
		if(doc == null) return;
		
		//获取用户信息
		ZhiHuUser zhiHuUser = getUserInfo(doc);
        
		//插入数据库
    	zhiHuUserDao.insert(zhiHuUser);
        
		//获取关注用户链接
        addUserFollowingUrl(visitUrl);
	}
	
	/**
	 * jsoup获取页面
	 * @param url
	 * @return
	 */
	private Document getDoc(String url) {

		Document document = null;
		try {

			document = Jsoup.connect(url).timeout(5000).get();

			if(document == null || document.toString().trim().equals("")) {// 表示ip被拦截或者其他情况
				
				System.out.println("出现ip被拦截或者其他情况");
				HttpUtils.setProxyIp();

				document = Jsoup.connect(url).get();
			}
		} catch (Exception e) { // 链接超时等其他情况
			
			System.out.println("出现链接超时等其他情况");
			HttpUtils.setProxyIp();// 换代理ip

			try {
				// 继续爬取网页
				document = Jsoup.connect(url).get();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return document;
		
	}
    //传入用户url，获取他所关注人的url
    private void addUserFollowingUrl(String userUrl){

        String[] temp = userUrl.split("/");
        String userId = temp[temp.length-1];

        //只获取第一页用户
		String body = "";
		try {
			Response res = Jsoup.connect("https://www.zhihu.com/api/v4/members/"+userId+"/followees?offset=0&limit=20")
					.header("Content-Type", "application/json;charset=UTF-8")
					.ignoreContentType(true).execute();
			body = res.body();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject json = JSONObject.fromObject(body);
		JSONArray jsonArray = json.getJSONArray("data");
		
		if(jsonArray == null || jsonArray.size() == 0) return;
		
		for(int i=0;i<jsonArray.size();i++) {
			
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			
			//剔除机构号
			if("organization".equals(jsonObject.get("user_type"))) continue;
			urlQueue.add(baseUrl+jsonObject.get("url_token").toString());;
		}
    }
    
	private ZhiHuUser getUserInfo(Document doc) {

		ZhiHuUser zhiHuUser = new ZhiHuUser();

        //姓名
		Element nameEle = doc.select(".ProfileHeader-name").first();
        String name = nameEle==null?"":nameEle.text();
        System.out.println("取得用户:"+name);
        zhiHuUser.setName(name);

        //签名
        Element headLineEle = doc.select(".ProfileHeader-info").first();
        String headLine = headLineEle==null?"":headLineEle.text();
        zhiHuUser.setUserInfo(headLine);
        
        int size = doc.select(".ProfileHeader-infoItem").size();
        if (size == 2) {
            //工作经历
        	Element business = doc.select(".ProfileHeader-infoItem").first();
            String string1 = business==null?"":business.text();
            if (string1 != null && string1 != "") {
                String[] a = string1.split(" ");
                for (int i = 0; i < a.length; i++) {
                	//行业
                    if (a.length > 0) {
                    	zhiHuUser.setBusiness(a[0]);
                    }
                    //公司
                    if (a.length > 1) {
                    	zhiHuUser.setCompany(a[1]);
                    }
                    //职位
                    if (a.length > 2) {
                    	zhiHuUser.setPosition(a[2]);
                    }
                }
            }
            
            //教育经历
            Element education = doc.select(".ProfileHeader-infoItem").get(1);
            String string2 = education==null?"":education.text();
            if (string2 != null && string2 != "") {
                String[] a = string2.split(" ");
                //学校
                if (a.length > 0) {
                	zhiHuUser.setEducation(a[0]);
                }
                //专业
                if (a.length > 1) {
                	zhiHuUser.setMajor(a[1]);
                }
            }
        }

        //看‘关注他’中有无关键字，判断性别
        Element sex = doc.select(".ProfileHeader-contentFooter > div.MemberButtonGroup").first();
        String sexString = sex==null?"":sex.text();
        if (sexString.contains("他")) {
        	zhiHuUser.setSex("男");
        } else if (sexString.contains("她")) {
        	zhiHuUser.setSex("女");
        } else {
        	zhiHuUser.setSex("未知");
        }
        
        return zhiHuUser;
	}
}
