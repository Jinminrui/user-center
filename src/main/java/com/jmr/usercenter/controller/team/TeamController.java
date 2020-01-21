package com.jmr.usercenter.controller.team;

import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.entity.team.Team;
import com.jmr.usercenter.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "http://localhost:3009", maxAge = 3600)
@RestController
@RequestMapping("/team")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/create")
    public CommonResponseDTO<Object> create(@RequestBody Team team) {
        teamService.create(team);
        return CommonResponseDTO.builder().code(200).desc("创建成功").build();
    }

    @PostMapping("/update")
    public CommonResponseDTO<Object> update(@RequestBody Team team) {
        teamService.update(team);
        return CommonResponseDTO.builder().code(200).desc("更新成功").build();
    }
}
