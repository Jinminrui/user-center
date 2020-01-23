package com.jmr.usercenter.service.team;

import com.jmr.usercenter.dao.team.TeamMapper;
import com.jmr.usercenter.dao.user_team_relation.UserTeamRelationMapper;
import com.jmr.usercenter.domain.entity.team.Team;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.domain.entity.user_team_relation.UserTeamRelation;
import com.jmr.usercenter.utils.UUIDOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamService {
    private final TeamMapper teamMapper;
    private final UserTeamRelationMapper userTeamRelationMapper;

    private final UUIDOperator uuidOperator;

    public Team getTeamInfoById(String id){
       return teamMapper.selectByPrimaryKey(id);
    }

    public void create(Team team) {
        // 团队表插入一条数据
        team.setPkId(uuidOperator.getUUid());
        team.setCreateTime(new Date());
        team.setUpdateTime(new Date());
        teamMapper.insertSelective(team);

        // 用户-团队关系表插入一条数据
        UserTeamRelation userTeamRelation = UserTeamRelation.builder()
                .userId(team.getCreatorId())
                .teamId(team.getPkId())
                .createTime(new Date())
                .updateTime((new Date()))
                .build();
        userTeamRelationMapper.insertSelective(userTeamRelation);
    }

    public String getTeamIdByUserId(String userId){
        Example example = new Example(UserTeamRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        UserTeamRelation userTeamRelation = userTeamRelationMapper.selectOneByExample(example);
        if(userTeamRelation == null) {
            return null;
        }
        return userTeamRelation.getTeamId();
    }

    public void update(Team team) {
        team.setUpdateTime(new Date());
        teamMapper.updateByPrimaryKeySelective(team);
    }

    public int deleteMember(String teamId, String userId) {
        log.info("删除的teamID:{}, userID:{}", teamId,userId);
        Example example = new Example(UserTeamRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("teamId", teamId);
        return userTeamRelationMapper.deleteByExample(example);
    }
}
