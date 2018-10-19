package com.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.model.FileModel;

@Repository
public interface FileDao {

	void insert(FileModel fileModel);
	
	List<FileModel> selectAll();
}