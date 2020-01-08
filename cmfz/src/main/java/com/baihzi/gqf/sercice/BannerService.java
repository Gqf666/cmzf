package com.baihzi.gqf.sercice;

import com.baihzi.gqf.entity.Banner;
import com.baihzi.gqf.entity.BannerDTO;

import java.util.List;

public interface BannerService {
    //添加
    void insert(Banner banner);
    //修改
    void updateBanner(Banner banner);
    //删除
    void deleteByPrimaryKey(String id);
    //分页查询
    BannerDTO selectByRowBounds(Banner banner,int rows,int page);
    //查所有
    List<Banner> selectAll();
    //通过条件查询 查5条数据
    List<Banner> selectRowsByTime();
}
