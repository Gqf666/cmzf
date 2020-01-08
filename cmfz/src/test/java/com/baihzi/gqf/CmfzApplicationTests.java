package com.baihzi.gqf;

import com.baihzi.gqf.dao.BannerDao;
import com.baihzi.gqf.entity.MapDto;
import com.baihzi.gqf.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class CmfzApplicationTests {
    @Autowired
    BannerDao bd;
    @Autowired
    UserDao userDao;
    @Test
    void contextLoads() {
        /*List<Admin> admins = ad.selectAll();
        for (Admin ads: admins
             ) {
            System.out.println(ads);
        }
        Admin admin = new Admin(null, "admin", null);
        Admin admin1 = ad.selectOne(admin);
        String a = null;
        if(admin.getUsername().equals("admin")){
            a = "成功";
        }else {
            a="失败";
        }
        System.out.println(a);
        Banner banner = new Banner();
        int pageSize = 3;
        int rows= 2;

        List<Banner> banners = bd.selectByRowBounds(banner, new RowBounds(rows, pageSize));
        for (Banner b:banners
             ) {
            System.out.println(b);
        }
        /*
        Banner banner = new Banner("da59cd85-384c-48f7-b187-3d827e2ea05e",null,null,null,null,null,null);
        bd.delete(banner);
        Banner banner = new Banner("da59cd85-384c-48f7-b187-3d827e2ea05e","52Hz","ccccc/ccc/cc","bb/bb/by",new Date(),"忠诚的原因,引诱力不够","2");
        bd.insert(banner);*/
        int i = bd.selectCount(null);
        System.out.println(i);

    }

    @Test
    void test02() {
        List<MapDto> mapDtos = userDao.selectLocation("0");
        for (MapDto mapDto : mapDtos) {
            System.out.println(mapDto);
        }
    }

    @Test
    void test03() {
        Integer integer = userDao.rigestUser("0", 1);
        System.out.println(integer);
    }
}
