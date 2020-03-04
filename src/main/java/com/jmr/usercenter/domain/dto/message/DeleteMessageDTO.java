package com.jmr.usercenter.domain.dto.message;

import lombok.Data;

@Data
public class DeleteMessageDTO {
    private String receiver;
    private Integer type;
}
