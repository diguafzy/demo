package com.demo.schedule;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.dao.FileDao;
import com.demo.model.FileModel;

public class TimingSchedule {

    @Autowired
    FileDao fileDao;
    
    //定时执行的方法
    public void execute(){

        System.out.println("-------------------执行时间"+ new Date());
        
        List<FileModel> fileModels = fileDao.selectAll();
        
        if(fileModels.isEmpty()) return; 
    	File folder = new File(fileModels.get(0).getFilepath());
    	
    	File[] files = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				if(file.isFile()) return true; 
				return false;
			}
    		
    	});

    	List<String> fileNameList = new ArrayList<String>();
		for(FileModel model : fileModels) {
			fileNameList.add(model.getFilename());
		}
		
    	for(File file : files) {
    		if(!fileNameList.contains(file.getName())) {
    			file.delete();
    		}
    	}
    }
}
