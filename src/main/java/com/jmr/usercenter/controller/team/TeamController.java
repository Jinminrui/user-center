package com.jmr.usercenter.controller.team;

import com.aliyuncs.exceptions.ClientException;
import com.jmr.usercenter.auth.CheckLogin;
import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.team.InviteMemberDTO;
import com.jmr.usercenter.domain.entity.team.Team;
import com.jmr.usercenter.domain.entity.user_team_relation.UserTeamRelation;
import com.jmr.usercenter.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/team")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamController {
    private final TeamService teamService;

    @CheckLogin
    @GetMapping("/{id}")
    public CommonResponseDTO<Team> getTeamInfoById(@PathVariable String id) {
        Team team = teamService.getTeamInfoById(id);
        return CommonResponseDTO.<Team>builder().code(200).data(team).desc("success").build();
    }

    @CheckLogin
    @PostMapping("/create")
    public CommonResponseDTO<Object> create(@RequestBody Team team) {
        teamService.create(team);
        return CommonResponseDTO.builder().code(200).desc("创建成功").build();
    }

    @CheckLogin
    @PostMapping("/update")
    public CommonResponseDTO<Object> update(@RequestBody Team team) {
        teamService.update(team);
        return CommonResponseDTO.builder().code(200).desc("更新成功").build();
    }

    @CheckLogin
    @PostMapping("/deleteMember")
    public CommonResponseDTO<Object> deleteMember(@RequestBody UserTeamRelation userTeamRelation) {
        int result = teamService.deleteMember(userTeamRelation.getTeamId(), userTeamRelation.getUserId());
        if (result == 1) {
            return CommonResponseDTO.builder().code(200).desc("删除成功").build();
        }
        return CommonResponseDTO.builder().code(200).desc("删除失败").build();
    }

    @CheckLogin
    @PostMapping("/inviteMember")
    public CommonResponseDTO<Object> inviteMember(@RequestBody InviteMemberDTO inviteMemberDTO) throws IOException, ClientException {
        teamService.inviteMember(inviteMemberDTO.getSenderId(), inviteMemberDTO.getTeamId(), inviteMemberDTO.getPhones());
        return CommonResponseDTO.builder().code(200).desc("邀请成功").build();
    }

    @CheckLogin
    @PostMapping("/sureToJoin/{phone}")
    public CommonResponseDTO<Object> sureToJoin(@PathVariable String phone) {
        if(teamService.sureToJoinTeam(phone) == 1) {
            return CommonResponseDTO.builder().code(200).desc("加入成功").build();
        }
        return CommonResponseDTO.builder().code(500).desc("加入失败").build();
    }
}
