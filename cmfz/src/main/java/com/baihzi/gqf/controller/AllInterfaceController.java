package com.baihzi.gqf.controller;

import com.baihzi.gqf.dao.*;
import com.baihzi.gqf.entity.*;
import com.baihzi.gqf.sercice.AlbumService;
import com.baihzi.gqf.sercice.BannerService;
import com.baihzi.gqf.util.SendSmsUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("allInterface")
public class AllInterfaceController {
    @Autowired
    UserDao ud;
    @Autowired
    BannerService bs;
    @Autowired
    ArticleDao ad;
    @Autowired
    AlbumService as;
    @Autowired
    AlbumDao a1;
    @Autowired
    CourseDao cd;
    @Autowired
    CounterDao counterDao;
    //登录接口i
    @RequestMapping("userLogin")
    public Map userLogin(String phone,String password){
        HashMap map = new HashMap();
        User user1 = new User();
        user1.setPhone(phone);
        User user = ud.selectOne(user1);
        if(user!=null){
            if(password.equals(user.getPassword())){
                map.put("user",user);
                map.put("status","200");
                return map;
            }else{
                map.put("status","-200");
                map.put("message","账号或密码错误");
                return map;
            }
        }else{
            map.put("status","-200");
            map.put("message","账号或密码错误");
            return map;
        }
    }
    //注册发送验证码
    @RequestMapping("sendCode")
    public Map sendCode(String phone){
        String code = String.format("%04d",new Random().nextInt(9999));
        HashMap map = new HashMap();
        try{
            SendSmsUtil.send(phone,code);
            //将phone，code 存入 redis
            map.put("message","发送成功");
            return map;
        }catch(Exception e){
            map.put("message","发送失败");
            return map;
        }

    }
    //注册接口
    @RequestMapping("registerUser")
    public Map registerUser(String phone,String code){
        //从 redis 获取数据
        return null;
    }
    //补充个人信息接口  添加
    @RequestMapping("supplyData")
    public Map supplyData(User user){
        HashMap map = new HashMap();
        try{
            //从 redis 数据库中拿出 注册使用的手机号 set 进去
           ud.updateByPrimaryKeySelective(user);
           map.put("status","200");
           map.put("user",user);
           return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","添加失败");
            return map;
        }
    }
    //一级页面展示接口
    @RequestMapping("onePage")
    public Map onePage(String uid,String type,String sub_type){
        HashMap map = new HashMap();
        try{
            if(type.equals("all")){
                //首页
                List<Banner> banners = bs.selectRowsByTime();
                AlbumDTO dto = as.selectByRowBounds(null,6,0);
                List<Article> articles = ad.selectByRowBounds(null, new RowBounds(0, 6));
                map.put("status","200");
                map.put("banners",banners);
                map.put("albums",dto.getRows());
                map.put("articles",articles);
                return map;
            }else if(type.equals("wen")){
                //专辑
                AlbumDTO dto = as.selectByRowBounds(null, 5, 0);
                map.put("albums",dto);
                map.put("status","200");
                return map;
            }else {
                if(sub_type.equals("ssjy")){
                    List<Article> articles = ad.selectAll();
                    map.put("articles",articles);
                    map.put("status","200");
                    return map;
                }else {
                    List<Article> articles = ad.selectAll();
                    map.put("articles",articles);
                    map.put("status","200");
                    return map;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("message","error");
            map.put("status","-200");
            return map;
        }
    }
    //文章详情接口 (查一篇文章)
    @RequestMapping("articleDetails")
    public Map articleDetails(String uid,String aid){
        HashMap map = new HashMap();
        try{
            Article article = ad.selectByPrimaryKey(aid);
            map.put("status","200");
            map.put("article",article);
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //专辑详情接口
    @RequestMapping("albumDetails")
    public Map albumDetails(String uid,String aid){
        HashMap map = new HashMap();
        try{
            Album album = a1.selectByPrimaryKey(aid);
            map.put("status","200");
            map.put("article",album);
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //展示功课
    @RequestMapping("showHomework")
    public Map showHomework(String uid){
        HashMap map = new HashMap();
        try{
            Course course = new Course();
            course.setUserId(uid);
            List<Course> courses = cd.select(course);
            map.put("status","200");
            map.put("courses",courses);
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //添加功课
    @RequestMapping("addHomework")
    public Map addHomework(String uid,String title){
        HashMap map = new HashMap();
        try{
            Course course = new Course();
            course.setId(UUID.randomUUID().toString());
            course.setUserId(uid);
            course.setTitle(title);
            course.setCreateDate(new Date());
            cd.insert(course);
            List<Course> courses = cd.selectAll();
            map.put("course",courses);
            map.put("status","200");
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //删除功课
    @RequestMapping("deleteHomework")
    public Map deleteHomework(String uid,String id){
        HashMap map = new HashMap();
        try{
            cd.deleteByPrimaryKey(id);
            Course course = new Course();
            course.setUserId(uid);
            List<Course> courses = cd.selectAll();
            map.put("status","200");
            map.put("courses",courses);
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //展示计数器
    @RequestMapping("showCounter")
    public Map showCounter(String uid,String cid){
        HashMap map = new HashMap();
        try{
            Counter counter = new Counter();
            counter.setUserId(uid);
            counter.setCourseId(cid);
            List<Counter> counters = counterDao.select(counter);
            map.put("status","200");
            map.put("counters",counters);
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //添加计数器
    @RequestMapping("addCounter")
    public Map addCounter(String uid,Integer count,String title,String cid){
        HashMap map = new HashMap();
        try{
            Counter counter = new Counter();
            counter.setId(UUID.randomUUID().toString());
            counter.setUserId(uid);
            counter.setCount(count);
            counter.setCourseId(cid);
            counter.setTitle(title);
            counter.setCreateDate(new Date());
            counterDao.insert(counter);
            map.put("status","200");
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //删除计数器
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String id){
        HashMap map = new HashMap();
        try{
            counterDao.deleteByPrimaryKey(id);
            map.put("status","200");
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //表更计数器
    @RequestMapping("updateCounter")
    public Map updateCounter(String id,Integer count){
        HashMap map = new HashMap();
        try{
            Counter counter = new Counter();
            counter.setId(id);
            counter.setCount(count);
            counterDao.updateByPrimaryKeySelective(counter);
            map.put("status","200");
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","error");
            return map;
        }
    }
    //修改个人信息
    @RequestMapping("updateUserData")
    public Map updateUserData(User user){
        HashMap map = new HashMap();
        try{
            user.setPhone("1234567");
            ud.updateByPrimaryKeySelective(user);
            map.put("status","200");
            map.put("user",user);
            return map;
        }catch (Exception e){
            map.put("status","-200");
            map.put("message","添加失败");
            return map;
        }
    }
    //金刚道友 (查 询 五 个 用 户 且 没 有 自 己 )

    //展示上师列表( )

    //添加关注上师
}
