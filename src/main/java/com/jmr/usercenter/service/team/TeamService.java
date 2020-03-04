package com.jmr.usercenter.service.team;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.jmr.usercenter.controller.message.WebSocketServer;
import com.jmr.usercenter.dao.team.TeamMapper;
import com.jmr.usercenter.dao.user_team_relation.UserTeamRelationMapper;
import com.jmr.usercenter.domain.dto.message.MessageResponseDTO;
import com.jmr.usercenter.domain.dto.team.InviteInfoRedisDTO;
import com.jmr.usercenter.domain.entity.message.Message;
import com.jmr.usercenter.domain.entity.team.Team;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.domain.entity.user_team_relation.UserTeamRelation;
import com.jmr.usercenter.service.message.MessageService;
import com.jmr.usercenter.service.sms.SmsService;
import com.jmr.usercenter.service.user.UserService;
import com.jmr.usercenter.utils.RedisUtil;
import com.jmr.usercenter.utils.UUIDOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamService {
    private final TeamMapper teamMapper;
    private final UserTeamRelationMapper userTeamRelationMapper;
    private final UserService userService;
    private final MessageService messageService;
    private final SmsService smsService;
    private final RedisUtil redisUtil;

    private final UUIDOperator uuidOperator;

    public Team getTeamInfoById(String id) {
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
                .userRole(1)
                .createTime(new Date())
                .updateTime((new Date()))
                .build();
        userTeamRelationMapper.insertSelective(userTeamRelation);
    }

    public String getTeamIdByUserId(String userId) {
        Example example = new Example(UserTeamRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        UserTeamRelation userTeamRelation = userTeamRelationMapper.selectOneByExample(example);
        if (userTeamRelation == null) {
            return null;
        }
        return userTeamRelation.getTeamId();
    }

    public void update(Team team) {
        team.setUpdateTime(new Date());
        teamMapper.updateByPrimaryKeySelective(team);
    }


    public int deleteMember(String teamId, String userId) {
        log.info("删除的teamID:{}, userID:{}", teamId, userId);
        Example example = new Example(UserTeamRelation.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("teamId", teamId);
        return userTeamRelationMapper.deleteByExample(example);
    }

    public void inviteMember(String senderId, String teamId, List<String> userPhones) throws IOException, ClientException {
        User sender = userService.findById(senderId);
        for (String phone : userPhones) {
            User user = userService.findUserByPhone(phone);
//            String result = smsService.sendInviteInfo(phone, sender.getUsername());
//            log.info("邀请短信发送结果：{}", result);
            if (user != null) {
                String messageId = uuidOperator.getUUid();
                Date sendTime = new Date();
                Message message = Message.builder()
                        .pkId(messageId)
                        .receiverId(user.getPkId())
                        .senderId(senderId)
                        .title(sender.getUsername() + "邀请您加入团队")
                        .type(2)
                        .senderRole(1)
                        .status(false)
                        .createTime(sendTime)
                        .updateTime(new Date())
                        .build();
                messageService.insertMessage(message);

                MessageResponseDTO messageResponseDTO = MessageResponseDTO.builder()
                        .messageId(messageId)
                        .receiverName(user.getUsername())
                        .senderName(sender.getUsername())
                        .title(sender.getUsername() + "邀请您加入团队")
                        .type(2)
                        .sendTime(sendTime)
                        .status(false)
                        .build();

                WebSocketServer.sendInfo(JSON.toJSONString(messageResponseDTO), user.getPkId());
            }

            InviteInfoRedisDTO inviteInfoRedisDTO = InviteInfoRedisDTO.builder()
                    .sender(senderId)
                    .targetPhone(phone)
                    .teamId(teamId)
                    .build();
            redisUtil.set("invite:" + phone, inviteInfoRedisDTO, 43200);
        }
    }

    public int sureToJoinTeam(String phone) {
        String inviteRedisKey =  "invite:" + phone;
        if(redisUtil.hasKey(inviteRedisKey)) {
            InviteInfoRedisDTO inviteInfoRedisDTO = (InviteInfoRedisDTO) redisUtil.get(inviteRedisKey);
            UserTeamRelation userTeamRelation = UserTeamRelation.builder()
                    .userId(userService.findUserByPhone(phone).getPkId())
                    .teamId(inviteInfoRedisDTO.getTeamId())
                    .userRole(2)
                    .createTime(new Date())
                    .updateTime(new Date())
                    .build();
            userTeamRelationMapper.insertSelective(userTeamRelation);
            redisUtil.del(inviteRedisKey);
            return 1;
        }
        return 0;
    }
}
