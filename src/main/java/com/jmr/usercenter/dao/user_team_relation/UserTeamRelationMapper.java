package com.jmr.usercenter.dao.user_team_relation;

import com.jmr.usercenter.domain.entity.user_team_relation.UserTeamRelation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface UserTeamRelationMapper extends Mapper<UserTeamRelation> {
    List<String> selectTeamIdByUserId(@Param("userId") String userId);
}