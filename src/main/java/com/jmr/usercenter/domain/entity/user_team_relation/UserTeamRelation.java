package com.jmr.usercenter.domain.entity.user_team_relation;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_team_relation")
public class UserTeamRelation {
    @Id
    @Column(name = "pk_id")
    private Integer pkId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "team_id")
    private String teamId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}