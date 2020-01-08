package com.baihzi.gqf.dao;

import com.baihzi.gqf.entity.Course;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CourseDao extends Mapper<Course> {
    //添加
    public void insertCourse(@Param("uid")String uid,@Param("title")String title);
}
