package com.baihzi.gqf.sercice;

import com.baihzi.gqf.entity.MapDto;
import com.baihzi.gqf.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao ud;
    @Override
    public Integer rigestUser(String sex, Integer day) {
        Integer count = ud.rigestUser(sex, day);
        return count;
    }

    @Override
    public List<MapDto> selectLocation(String sex) {
        List<MapDto> mapDtos = ud.selectLocation(sex);
        return mapDtos;
    }
}
