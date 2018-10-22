package com.demo.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.JobDao;
import com.demo.job.HelloJob;
import com.demo.model.JobModel;

/**
 * 定时调度任务Service
 * 
 */
@Service
public class JobService {

	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private JobDao jobDao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public void save(JobModel JobModel) {
		
	}
	
	/**
	 * 系统初始加载任务
	 */
	public void loadJob() throws Exception {
		try {
			JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
					.withIdentity("helloJob", Scheduler.DEFAULT_GROUP)
					.usingJobData("name", "abc")
					.build();
			// 添加任务参数
			CronTrigger cronTrigger = TriggerBuilder.newTrigger()
	                .withIdentity("cron trigger", Scheduler.DEFAULT_GROUP)
	                .withSchedule(
	                    //每5秒执行一次                       
	                    CronScheduleBuilder.cronSchedule("0/5 * * ? * *")
	                ).build();

			// 调度任务
			scheduler.scheduleJob(jobDetail, cronTrigger);
		} catch (SchedulerException e) {
			logger.error("JobService SchedulerException", e);
		} catch (Exception e) {
			logger.error("JobService Exception", e);
		}
	}
}