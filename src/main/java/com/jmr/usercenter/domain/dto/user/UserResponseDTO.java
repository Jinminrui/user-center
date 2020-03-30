package com.jmr.usercenter.domain.dto.user;

import com.jmr.usercenter.domain.entity.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponseDTO {
    private String pkId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 微信名称

     */
    private String wxName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 职位
     */
    private String position;

    /**
     * 个人描述
     */
    private String description;

    /**
     * 所属团队id
     */
    private List<String> teams;

    private Date createTime;

    private Date updateTime;
}
