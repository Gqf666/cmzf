package com.baihzi.gqf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baihzi.gqf.dao.UserDao;
import com.baihzi.gqf.entity.MapDto;
import com.baihzi.gqf.entity.User;
import com.baihzi.gqf.sercice.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService us;
    @Autowired
    UserDao userDao;
    @RequestMapping("selectRegetisCount")
    public Map selectRegetisCount(){
        HashMap map = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(us.rigestUser("1",1));
        manList.add(us.rigestUser("1",7));
        manList.add(us.rigestUser("1",30));
        manList.add(us.rigestUser("1",365));
        ArrayList womanList = new ArrayList();
        womanList.add(us.rigestUser("0",1));
        womanList.add(us.rigestUser("0",7));
        womanList.add(us.rigestUser("0",30));
        womanList.add(us.rigestUser("0",365));
        map.put("man",manList);
        map.put("woman",womanList);
        return map;
    }
    @RequestMapping("selectLocation")
    public Map selectLocation(){
        HashMap map = new HashMap();
        List<MapDto> man = us.selectLocation("1");
        List<MapDto> woman = us.selectLocation("0");
        map.put("man",man);
        map.put("woman",woman);
        return map;
    }

    @RequestMapping("addUser")
    public Map addUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setLocation("山西");
        user.setSex("1");
        user.setRigestDate(new Date());
        userDao.insert(user);
        HashMap map = new HashMap();
        List<MapDto> man = us.selectLocation("1");
        List<MapDto> woman = us.selectLocation("0");
        map.put("man",man);
        map.put("woman",woman);
        String s = JSONObject.toJSONString(map);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-8d58496859a8400a9dcbccaacf7d3bb6");
                goEasy.publish("map", s);
        Map regetisCount = selectRegetisCount();
        String s1 = JSONObject.toJSONString(regetisCount);
        GoEasy goEasy2 = new GoEasy( "http://rest-hangzhou.goeasy.io", "C-8d58496859a8400a9dcbccaacf7d3bb6");
                goEasy.publish("echarts1", s1);
        return null;
    }
}
