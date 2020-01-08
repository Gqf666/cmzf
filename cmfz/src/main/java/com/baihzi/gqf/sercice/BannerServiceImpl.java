package com.baihzi.gqf.sercice;


import com.baihzi.gqf.dao.BannerDao;
import com.baihzi.gqf.entity.Banner;
import com.baihzi.gqf.entity.BannerDTO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bd;
    @Override
    public void insert(Banner banner) {
        bd.insert(banner);
    }

    @Override
    public void updateBanner(Banner banner) {
        bd.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void deleteByPrimaryKey(String id) {
        bd.deleteByPrimaryKey(id);
    }

    @Override
    public BannerDTO selectByRowBounds(Banner banner, int rows, int page) {
        //计算总页数
        BannerDTO dto = new BannerDTO();
        int i = bd.selectCount(null);
        dto.setRecords(i);
        dto.setPage(page);
        if(dto.getRecords()%rows==0){
            dto.setTotal(dto.getRecords()/rows);
        }else{
            dto.setTotal(dto.getRecords()/rows+1);
        }
        List<Banner> banners = bd.selectByRowBounds(banner,new RowBounds((page-1)*rows,rows));
        dto.setRows(banners);
        return dto;
    }

    @Override
    public List<Banner> selectAll() {
        List<Banner> banners = bd.selectAll();
        return banners;
    }

    @Override
    public List<Banner> selectRowsByTime() {
        Example example = new Example(Banner.class);
        example.orderBy("createDate").desc();
        List<Banner> banners = bd.selectByExampleAndRowBounds(example, new RowBounds(0, 5));
        return banners;
    }

}
