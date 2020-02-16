package com.jmr.usercenter.domain.entity.message_text;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message_text")
public class MessageText {
    @Id
    @Column(name = "pk_id")
    private String pkId;

    private String title;

    private String content;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
}