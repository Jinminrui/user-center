package com.jmr.usercenter.dao.user;

import com.jmr.usercenter.domain.entity.user.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<User> {
    List<Map<String, Object>> getAllUsersByTeam (@Param("teamId") String teamId);
}