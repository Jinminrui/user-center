package com.jmr.usercenter.domain.entity.team;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team")
public class Team {
    @Id
    @Column(name = "pk_id")
    private String pkId;

    private String name;

    /**
     * 团队描述
     */
    private String description;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}