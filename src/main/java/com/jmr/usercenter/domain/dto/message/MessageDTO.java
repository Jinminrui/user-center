package com.jmr.usercenter.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDTO {
    /**
     * id
     */
    private String messageId;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 发送者信息
     */
    private String sender;

    /**
     * 接受者信息
     */
    private String receiver;

    /**
     * 信息标题
     */
    private String title;

    /**
     * 信息内容
     */
    private String content;

    /**
     * 消息状态
     */
    private Boolean status;

    /**
     * 发送时间
     */
    private Date sendTime;
}
