package com.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.model.JobModel;

@Repository
public interface JobDao {

	List<JobModel> selectAll();
	
	void save(JobModel jobModel);
	
	void delete(int jobId);
}
