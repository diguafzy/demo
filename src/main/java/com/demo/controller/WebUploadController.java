package com.demo.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.demo.dao.FileDao;
import com.demo.model.FileModel;
import com.demo.util.FileUtil;

/**
 * Created by fzy on 2017/9/28.
 */
@Controller
public class WebUploadController {

    private static final String FILEPATHIMG = "images/"; //图片上传路径
    private static final String[] IMG_EXT = {"gif","jpg","jpeg","bmp","png"}; //图片上传路径

    @Autowired
    FileDao fileDao;

    @RequestMapping(value = "/system/upload")
    public String upload(ModelMap modelMap) {
        return "system/upload";
    }

    @RequestMapping(value="/system/upload/add", method = RequestMethod.POST)
    public String add(String filenames) {
    	
    	String[] filenameArray = filenames.split(",");
    	
    	for(String filename : filenameArray) {
    		if(filename.isEmpty()) continue;
    		
        	FileModel fileModel = new FileModel();
        	fileModel.setFilename(filename);
        	fileModel.setFilepath(FileUtil.getClasspath() + FILEPATHIMG);
        	
        	fileDao.insert(fileModel);
    	}
    	
    	return "redirect:/system/upload";
    }
    
    /**
     * 新增
     */
    @RequestMapping(value="/system/upload/save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> save(@RequestParam(required=false) MultipartFile file) throws Exception{
        Map<String,String> resultMap = new HashMap<String,String>();
        String filename = "";

        if (null != file && !file.isEmpty()) {
            String filePath = FileUtil.getClasspath() + FILEPATHIMG;      //文件上传路径
            
            filename = FileUtil.fileUp(file, filePath, UUID.randomUUID().toString().trim().replaceAll("-", ""));             //执行上传
        }
        String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        for(String ext : IMG_EXT) {

            if(ext.equals(extName)) {
                resultMap.put("picturename", filename);
                break;
            }
        }
        resultMap.put("filename", filename);
        return resultMap;
    }

    /**
     * 删除
     * @param filename
     */
    @RequestMapping(value="/system/upload/delete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, String> delete(String filename) {
    	Map<String, String> resultMap = new HashMap<String, String>();
    	
        String filePath = FileUtil.getClasspath() + FILEPATHIMG;      //文件上传路径
        
        File file = new File(filePath+filename);
        if(file.exists()) {
        	file.delete();
        }
        
        return resultMap;
    }
}
