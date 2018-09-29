package com.demo.dao;

import org.springframework.stereotype.Repository;

import com.demo.model.User;

@Repository
public interface UserDao {

	User findOneByUserName(String name);
}