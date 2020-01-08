package com.baihzi.gqf.controller;

import com.baihzi.gqf.dao.ArticleDao;
import com.baihzi.gqf.entity.Article;
import com.baihzi.gqf.entity.Banner;
import com.baihzi.gqf.entity.Chapter;
import com.baihzi.gqf.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleDao ad;
    @RequestMapping("uploadImg")
    @ResponseBody
    //imgFile 返回文件的名称 默认的  可修改
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        //该方法需要返回的信息 状态码  error 0  成功  1 失败  成功时返回 url 图片路径
        HashMap map = new HashMap();
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/article/img/");
        File file = new File(realPath);
        //判断该文件是否存在
        if(!file.exists()){
            //多级创建
            file.mkdirs();
        }
        try{
            //设置网络路径 名
            String http = HttpUtil.getHttp(imgFile, request, "/upload/article/img/");
            map.put("error",0);
            map.put("url",http);
        }catch (Exception e){
            map.put("error",1);
            e.printStackTrace();
        }
        return map;
    }
    @ResponseBody
    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap map = new HashMap();
        map.put("current_url",request.getContextPath()+"/upload/article/img/");
        String realPath = session.getServletContext().getRealPath("/upload/article/img/");
        File file = new File(realPath);
        //  得到该文件夹下的所有文件
        File[] files = file.listFiles();
        //total_count 文件数量
        map.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1:files) {
            HashMap fileMap = new HashMap();
            //是否是一个文件夹
            fileMap.put("is_dir",false);
            //是否包含文件
            fileMap.put("has_file",false);
            //文件大小
            fileMap.put("filesize",file1.length());
            //是否是个图片
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            //文件格式
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            //通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            //设置格式
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String s = format.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",s);
            arrayList.add(fileMap);
        }
        map.put("file_list",arrayList);
        return map;
    }
    @RequestMapping("showAllArticle")
    @ResponseBody
    public Map showAllArticle(int rows,int page){
        HashMap map = new HashMap();
        Article article = new Article();
        //总数据
        int records = ad.selectCount(article);
        int total = records%rows==0?records/rows:records/rows+1;
        List<Article> articles = ad.selectByRowBounds(article, new RowBounds((page - 1) * rows, rows));
        map.put("records",records);
        map.put("page",page);
        map.put("total",total);
        map.put("rows",articles);
        return map;
    }

    @RequestMapping("insertArticle")
    @ResponseBody
    public void insertArticle(Article article,MultipartFile inputfile,HttpSession session,HttpServletRequest request){
        // {id="",}
        System.out.println(article);
        if (article.getId() == null || "".equals(article.getId())){
            // insert
            article.setId(UUID.randomUUID().toString());
            article.setCreateDate(new Date());
            article.setPublishDate(new Date());
            //获取真实路径
            String realPath = session.getServletContext().getRealPath("/upload/img/");
            //判断该文件夹是否存在
            File file = new File(realPath);
            if(!file.exists()){
                //mkdirs() 多级创建
                file.mkdirs();
            }
            String http = HttpUtil.getHttp(inputfile,request,"/upload/img/");
            article.setId(article.getId());
            article.setImg(http);
            //把数据库中的 URL 路径 名修改为 newName;
            ad.insert(article);
        }else{
            ad.updateByPrimaryKeySelective(article);
            // update
        }
    }
    @RequestMapping("deleteArticle")
    @ResponseBody
    public void deleteArticle(String id){
        ad.deleteByPrimaryKey(id);
    }
}
