package com.baihzi.gqf.sercice;

import com.baihzi.gqf.dao.AdminDao;
import com.baihzi.gqf.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service("adminService")
@Transactional
public class AdminSerciceImpl implements AdminService{
    @Autowired
    private AdminDao adminDao;
    @Autowired
    HttpServletRequest request;
    @Override
    public String selectOne(Admin admin) {
        String massge =null;

        Admin admin1 = adminDao.selectOne(admin);

        if(admin1 != null){
            request.getSession().setAttribute("admin",admin1);
            massge = "输入正确";
        }else{
            massge = "用户名账号或密码错误";
        }

        return massge;
    }
}
