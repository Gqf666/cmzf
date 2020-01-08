package com.baihzi.gqf.sercice;

import com.baihzi.gqf.entity.Album;
import com.baihzi.gqf.entity.AlbumDTO;

public interface AlbumService {
    //添加
    void insert(Album album);
    //删除
    void deleteById(String id);
    //修改
    void updateAlbum(Album album);
    //分页查所有
    AlbumDTO selectByRowBounds(Album album,int rows,int pages);
    //通过条件 查6条

}
