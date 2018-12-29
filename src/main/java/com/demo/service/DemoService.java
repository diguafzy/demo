package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.math.RandomUtils;

public class DemoService {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newFixedThreadPool(10);
		CountDownLatch count = new CountDownLatch(10);

		List<Future<String>> futureList = new ArrayList<>();

		Future<String> future = null;
		for(int i=0;i<10;i++) {
			final int num = i;
			future = pool.submit(new Callable<String>(){

				@Override
				public String call() throws Exception {
					
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
					Thread.sleep(RandomUtils.nextInt(10)*1000);
					count.countDown();
					return "线程"+num+":"+format.format(new Date());
				}
			});
			
			futureList.add(future);
		}

		count.await();
		pool.shutdown();
		
		for(int i=0;i<futureList.size();i++) {
			System.out.println(futureList.get(i).get());
		}
	}
}
