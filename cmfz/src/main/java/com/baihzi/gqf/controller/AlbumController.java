package com.baihzi.gqf.controller;
import com.baihzi.gqf.entity.Album;
import com.baihzi.gqf.entity.AlbumDTO;
import com.baihzi.gqf.sercice.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService as;

    //分页
    @ResponseBody
    @RequestMapping("selectByRows")
    public AlbumDTO selectByRows(Album album,int rows,int page){
        AlbumDTO dto = as.selectByRowBounds(album, rows, page);
        return dto;
    }
    //增删改
    @RequestMapping("edit")
    @ResponseBody
    public Map edit(Album album,String oper){
        HashMap hashMap = new HashMap();
        if(oper.equals("edit")){
            hashMap.put("albumId",album.getId());
            System.out.println(album);
            as.updateAlbum(album);
        }else if(oper.equals("add")){
            String id = UUID.randomUUID().toString();
            album.setId(id);
            album.setCreateDate(new Date());
            hashMap.put("albumId",id);
            as.insert(album);
        }else{
            hashMap.put("albumId",album.getId());
            as.deleteById(album.getId());
        }
        return hashMap;
    }
    //上传封面
    @RequestMapping("upload")
    @ResponseBody
    public Map upload(MultipartFile cover, String albumId, HttpSession session){
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img");
        //判断该文件是否已经存在
        File file = new File(realPath);
        if(!file.exists()){
            //多级创建
            file.mkdirs();
        }
        //防止文件重名加上时间戳
        String newName = cover.getOriginalFilename() + "_" + new Date().getTime();
        try {
            cover.transferTo(new File(realPath,newName));

            Album album = new Album();
            album.setId(albumId);
            album.setCover(newName);
            as.updateAlbum(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
