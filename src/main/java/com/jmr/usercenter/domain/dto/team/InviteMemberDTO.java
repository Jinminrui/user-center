package com.jmr.usercenter.domain.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InviteMemberDTO {
    private List<String> phones;
    private String teamId;
    private String senderId;
}
