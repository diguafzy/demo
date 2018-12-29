package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.model.JobModel;
import com.demo.service.JobService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * Created by fzy on 2017/9/28.
 */
@Controller
public class JobController {

    @Autowired
    JobService jobService;
	
    // 查看所有订单
    @RequestMapping(value = "/system/job", method = RequestMethod.GET)
    public String showOrders(ModelMap modelMap) {

    	Integer pageNum = 1;
    	Integer pageSize = 10;
    	
    	List<JobModel> jobList;
    	if(pageNum != null && pageSize != null) {
            Page<?> page = PageHelper.startPage(pageNum, pageSize, true);
    		jobList = jobService.selectAll();
    		
        	modelMap.put("total", page.getTotal());
    	} else {
    		jobList = new ArrayList<JobModel>();
    	}
    	
    	modelMap.put("jobList", jobList);
    	modelMap.put("currentPage", 1);
    	modelMap.put("totalPage", 1);
    	modelMap.put("showData", 10);
    	
        return "system/job";
    }

    @RequestMapping(value = "/system/job/add", method = RequestMethod.GET)
    public String add() {
        return "system/addJob";
    }

    @RequestMapping(value = "/system/job/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("job")JobModel jobModel) {
    	jobService.save(jobModel);
    	return "redirect:/system/job";
    }

    @RequestMapping(value = "/system/job/delete/{jobId}", method = RequestMethod.GET)
    public String delete(@PathVariable("jobId") int jobId) {
    	jobService.delete(jobId);
    	return "redirect:/system/job";
    }
}
