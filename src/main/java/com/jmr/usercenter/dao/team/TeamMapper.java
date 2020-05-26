package com.jmr.usercenter.dao.team;

import com.jmr.usercenter.domain.dto.team.TeamDTO;
import com.jmr.usercenter.domain.entity.team.Team;
import tk.mybatis.mapper.common.Mapper;

public interface TeamMapper extends Mapper<Team> {
    TeamDTO getTeamInfoByUserId(String teamId, String userId);
}