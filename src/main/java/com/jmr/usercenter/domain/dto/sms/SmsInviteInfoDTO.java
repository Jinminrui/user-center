package com.jmr.usercenter.domain.dto.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SmsInviteInfoDTO {
    private String invitor;
}
