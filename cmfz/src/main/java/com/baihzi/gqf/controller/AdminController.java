package com.baihzi.gqf.controller;

import com.baihzi.gqf.entity.Admin;
import com.baihzi.gqf.sercice.AdminService;
import com.baihzi.gqf.util.VerifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService as;
    @RequestMapping("selectOne")
    @ResponseBody
    public String selectOne(Admin admin, HttpSession session,String vCode){
        String code =(String) session.getAttribute("code");
        String ss = null;
        if(!code.equals(vCode)) {
            ss = "验证码错误";
            return  ss ;
        }
        String s = as.selectOne(admin);
        return s;
    }
    @RequestMapping("getCodeImge")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {

        String code = VerifyCodeUtil.generateVerifyCode(4);

        BufferedImage image = VerifyCodeUtil.getImage(255, 80, code);

        ImageIO.write(image, "png", response.getOutputStream());

        session.setAttribute("code",code);
    }
}
