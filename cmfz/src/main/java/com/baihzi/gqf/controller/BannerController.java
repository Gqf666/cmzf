package com.baihzi.gqf.controller;

import com.alibaba.excel.EasyExcel;
import com.baihzi.gqf.entity.Banner;
import com.baihzi.gqf.entity.BannerDTO;
import com.baihzi.gqf.sercice.BannerService;
import com.baihzi.gqf.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bs;
    @ResponseBody
    @RequestMapping("selectAll")
    public List<Banner> selectAll(){
        List<Banner> banners = bs.selectAll();
        return banners;
    }
    @RequestMapping("updateBanner")
    public void updateBanner(Banner banner){
        bs.updateBanner(banner);
    }
    //分页
    @RequestMapping("selectByRowBounds")
    @ResponseBody
    public BannerDTO selectByRowBounds(Banner banner,int rows,int page){
        BannerDTO dto = bs.selectByRowBounds(banner, rows,page);
        return dto;
    }
    //增删改
    @ResponseBody
    @RequestMapping("edit")
    public Map edit(Banner banner,String oper){
        HashMap hashMap = new HashMap();
        if (oper.equals("edit")){
            hashMap.put("bannerId",banner.getId());
            bs.updateBanner(banner);
        }else if(oper.equals("add")){
            String id = UUID.randomUUID().toString();
            banner.setId(id);
            hashMap.put("bannerId",id);
            banner.setCreateDate(new Date());
            bs.insert(banner);
        }else{
            bs.deleteByPrimaryKey(banner.getId());
        }
        return hashMap;
    }
    @RequestMapping("upload")
    @ResponseBody
    //MultipartFile url(上传的文件)bannerId(轮播图Id,更新使用)
    public Map uploadBanner(MultipartFile url, HttpServletRequest request, String bannerId, HttpSession session){
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断该文件夹是否存在
        File file = new File(realPath);
        if(!file.exists()){
            //mkdirs() 多级创建
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(url,request,"/upload/img/");
        Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl(http);
            //把数据库中的 URL 路径 名修改为 newName;
            bs.updateBanner(banner);
                return null;
    }
    //导出 excel 表格
    @RequestMapping("excelExport")
    public void excelExport(HttpServletResponse response) throws IOException {
        List<Banner> banners = bs.selectAll();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String encode = URLEncoder.encode("轮播图信息", "UTF-8");
        String fileName = encode.replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(),Banner.class)
                .sheet("轮播图")
                .doWrite(banners);
    }
    //导入 excel 表格
    @RequestMapping("excelImport")
    public void excelImport(){

    }
}
