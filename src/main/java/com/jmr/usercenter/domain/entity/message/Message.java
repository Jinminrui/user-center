package com.jmr.usercenter.domain.entity.message;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "pk_id")
    private String pkId;

    /**
     * 发送者id
     */
    @Column(name = "sender_id")
    private String senderId;

    /**
     * 发送者角色
     */
    @Column(name = "sender_role")
    private Integer senderRole;

    /**
     * 接受者id
     */
    @Column(name = "receiver_id")
    private String receiverId;

    /**
     * 消息类型
     */
    private Integer type;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 消息内容
     */
    @Column(name = "message_text_id")
    private String messageTextId;

    /**
     * 消息状态：已读或未读
     */
    private Boolean status;
}