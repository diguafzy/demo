package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
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

	public void save(JobModel jobModel) {
		jobDao.save(jobModel);
	}
	
	public void delete(int id) {
		
	}
	
	public List<JobModel> selectAll() {
		return jobDao.selectAll();
	}
	
	/**
	 * 系统初始加载任务
	 */
	public void loadJob() throws Exception {
		
		List<JobModel> jobList = new ArrayList<JobModel>();
		
		for(JobModel job : jobList) {
			if("禁用".equals(job.getIsEnable())) continue; 
			
			try {
				JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
						.withIdentity(job.getJobName(), Scheduler.DEFAULT_GROUP)
						.usingJobData("name", "abc")
						.build();
				// 添加任务参数
				CronTrigger cronTrigger = TriggerBuilder.newTrigger()
		                .withIdentity("cron trigger", Scheduler.DEFAULT_GROUP)
		                .withSchedule(
		                    //每5秒执行一次                       
		                    CronScheduleBuilder.cronSchedule(job.getCronExpression())
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
}