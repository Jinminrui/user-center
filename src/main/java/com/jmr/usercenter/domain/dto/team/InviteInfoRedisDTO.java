package com.jmr.usercenter.domain.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InviteInfoRedisDTO {
    private String targetPhone;
    private String teamId;
    private String sender;
}
