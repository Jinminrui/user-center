package com.jmr.usercenter.domain.entity.user;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "pk_id")
    private String pkId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

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
    @Column(name = "wx_name")
    private String wxName;

    /**
     * 性别
     */
    private Boolean gender;

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

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String description;
}