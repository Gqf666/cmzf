package com.baihzi.gqf.sercice;

import com.baihzi.gqf.entity.MapDto;

import java.util.List;

public interface UserService {
    //查询在某个时间段内男女用户注册的数量
    public Integer rigestUser(String sex,Integer day);
    //查询地址
    public List<MapDto> selectLocation(String sex);
}
