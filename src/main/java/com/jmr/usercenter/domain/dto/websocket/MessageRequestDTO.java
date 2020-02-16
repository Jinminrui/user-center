package com.jmr.usercenter.domain.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageRequestDTO {
    private List<String> mentions;
    private String messageTitle;
    private String messageContent;
    private Integer type;
}
