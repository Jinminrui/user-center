package com.jmr.usercenter.dao.user;

import com.jmr.usercenter.domain.entity.user.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<User> getAllUsersByTeam (@Param("teamId") String teamId);
}