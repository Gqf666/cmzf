package com.baihzi.gqf.sercice;

import com.baihzi.gqf.dao.AlbumDao;
import com.baihzi.gqf.entity.Album;
import com.baihzi.gqf.entity.AlbumDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService{
    @Autowired
    private AlbumDao ad;

    @Override
    public void insert(Album album) {
        ad.insert(album);
    }

    @Override
    public void deleteById(String id) {
        ad.deleteByPrimaryKey(id);
    }

    @Override
    public void updateAlbum(Album album) {
        ad.updateByPrimaryKeySelective(album);
    }

    @Override
    public AlbumDTO selectByRowBounds(Album album, int rows, int pages) {
        AlbumDTO dto = new AlbumDTO();
        //获取总条数
        int i = ad.selectCount(null);
        dto.setRecords(i);//总数据
        dto.setPage(pages);//当前页
        //计算总页数
        if(dto.getRecords()%rows==0){
            dto.setTotal(dto.getRecords()/rows);
        }else{
            dto.setTotal(dto.getRecords()/rows+1);
        }
        //new RowBounds(起始条数, 查询条数)
        List<Album> albums = ad.selectByRowBounds(album, new RowBounds((pages - 1) * rows, rows));
        dto.setRows(albums);
        return dto;
    }
}
