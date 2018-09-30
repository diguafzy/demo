package com.demo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.demo.model.ZhiHuUser;

@Repository
public interface ZhiHuUserDao {

	List<Map> selectUserGroupByBusiness();

	List<Map> selectUserGroupByEducation();
	
	void insert(ZhiHuUser zhiHuUser);
}