package com.baihzi.gqf.dao;

import com.baihzi.gqf.entity.MapDto;
import com.baihzi.gqf.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {
    public Integer rigestUser(@Param("sex")String sex,@Param("day")Integer day);
    //查询地址
    public List<MapDto> selectLocation(String sex);
}
