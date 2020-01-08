package com.baihzi.gqf.controller;

import com.baihzi.gqf.dao.ChapterDao;
import com.baihzi.gqf.entity.Chapter;
import com.baihzi.gqf.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterDao cd;

    //分页
    @ResponseBody
    @RequestMapping("selectByRows")
    public Map selectByRows(int rows,int page,String albumId){
        HashMap map = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        //总数据
        int records = cd.selectCount(chapter);
        int total = records%rows==0?records/rows:records/rows+1;
        List<Chapter> chapters = cd.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        map.put("records",records);
        map.put("page",page);
        map.put("total",total);
        map.put("rows",chapters);
        return map;
    }
    //增删改
    @ResponseBody
    @RequestMapping("edit")
    public Map edit(Chapter chapter,String oper,String albumId){
        HashMap map = new HashMap();
        if(oper.equals("edit")){
            map.put("chapterId",chapter.getId());
            cd.updateByPrimaryKeySelective(chapter);
        }else if(oper.equals("add")){
            String id = UUID.randomUUID().toString();
            chapter.setId(id);
            chapter.setAlbumId(albumId);
            chapter.setCreateTime(new Date());
            map.put("chapterId",id);
            cd.insert(chapter);
        }else{
            map.put("chapterId",chapter.getId());
            cd.deleteByPrimaryKey(chapter.getId());
        }
        return map;
    }
    //上传文件
    @RequestMapping("upload")
    @ResponseBody
    public Map upload(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        //获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/mp3/");
        //判断该文件是否存在
        File file = new File(realPath);
        if(!file.exists()){
            //多级创建
            file.mkdirs();
        }
        //设置网络路径
        String http = HttpUtil.getHttp(url, request, "/upload/mp3/");
        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        chapter.setUrl(http);
        //计算文件大小
        Double size = Double.valueOf(url.getSize()/1024/1024);

        chapter.setSize(size);
        //计算音频时长
        //使用第三方计算音频时间工具类
        String[] split = http.split("/");
        //获取文件名
        String name=split[split.length-1];
        //通过文件 AudioFile 对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        //通过音频解析对象 获取头部信息 为了信息更准确 需要将AudioFile 转为MP3AudioHeader
        MP3AudioHeader audioHeader=(MP3AudioHeader)read.getAudioHeader();
        //获取音频时长秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength/60 + "分" + trackLength%60 + "秒";

        chapter.setTime(time);

        cd.updateByPrimaryKeySelective(chapter);
        System.out.println(chapter+"1.3.0.3333");
        HashMap map = new HashMap();

        map.put("status",200);

        return map;
    }
    //下载文件
    @ResponseBody
    @RequestMapping("download")
    public void download(String url, HttpServletResponse response,HttpSession session) throws IOException {
        //处理 URL 路径找到文件
        String[] split = url.split("/");

        String realPath = session.getServletContext().getRealPath("/upload/mp3/");

        String name = split[split.length - 1];
        File file = new File(realPath, name);
        //调用该方法只能使用 location.href 不能使用ajax     ajax不支持下载

        //通过url获取本地文件
        response.setHeader("Content-Disposition","attachment;filename"+name);

        ServletOutputStream outputStream = response.getOutputStream();

        FileUtils.copyFile(file,outputStream);
        //FileUtils.copyFile("服务器文件",outputStream);
    }
}
