package com.jmr.usercenter.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDTO {
    private String messageId;
    private String receiverName;
    private String senderName;
    private String title;
    private String content;
    private int type;
    private boolean status;
    private Date sendTime;
}
