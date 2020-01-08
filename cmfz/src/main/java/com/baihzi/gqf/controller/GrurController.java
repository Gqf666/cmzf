package com.baihzi.gqf.controller;

import com.baihzi.gqf.dao.GuruDao;
import com.baihzi.gqf.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("guru")
public class GrurController {
    @Autowired
    GuruDao gd;
    @RequestMapping("showAllGuru")
    public List<Guru> showAllGuru(){
        List<Guru> gurus = gd.selectAll();
        return gurus;
    }
}
