package com.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.dao.UserDao;
import com.demo.dao.ZhiHuUserDao;
import com.demo.model.User;


/**
 * Created by fzy on 2017/9/28.
 */
@Controller
public class LoginController {

    @Autowired
    UserDao userDao;

    @Autowired
    ZhiHuUserDao zhiHuUserDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginin(ModelMap modelMap) {
        return "system/login";
    }

    @RequestMapping(value = "/system/login")
    public String login(@ModelAttribute("user") User user) {
        User userEntity = userDao.findOneByUserName(user.getUsername());
        if(userEntity != null
                && user.getPassword().endsWith(userEntity.getPassword())) {
            return "system/main";
        }
        return "system/login";
    }

    @RequestMapping(value = "/system/index")
    public String index(ModelMap modelMap) {
        List<Map> resultList = zhiHuUserDao.selectUserGroupBySex();

        String strXML = "";
        String[] color = new String[]{"AFD8F8","F6BD0F","8BBA00","FF8E46","008E8E","D64646","8E468E","588526","B3AA00","008ED6","9D080D","A186BE"};
        strXML += "<graph caption='"+"知乎用户行业分布' decimalPrecision='0' formatNumberScale='0'>";
        int index = 0;
        for(Map map : resultList) {
        	if(map.get("business") == null) continue; 
            strXML += "<set name='"+map.get("business")+"' value='"+map.get("businesscount")+"' color='"+color[index]+"'/>";
            index++;
            if(index == 12) index = 0;
        }
        strXML += "</graph>";

        modelMap.addAttribute("strXML", strXML);
        
        return "system/index";
    }

}
