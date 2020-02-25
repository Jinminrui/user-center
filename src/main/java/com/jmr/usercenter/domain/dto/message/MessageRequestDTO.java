package com.jmr.usercenter.domain.dto.message;

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
    private List<Receiver> receivers;
    private String messageTitle;
    private String messageContent;
    private Integer type;
}