package com.jmr.usercenter.domain.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private String pkId;

    private String name;

    /**
     * 团队描述
     */
    private String description;

    private String creatorId;

    private Date createTime;

    private Date updateTime;

    private Integer currentRole;
}
