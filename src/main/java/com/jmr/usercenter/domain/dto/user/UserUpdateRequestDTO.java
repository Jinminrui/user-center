package com.jmr.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserUpdateRequestDTO {
    private Integer pkId;

    private String username;

    private String phone;

    private String email;

    private String avatar;

    private String position;

    private String description;
}
