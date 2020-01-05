package com.jmr.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginResponseDTO {
    private JwtTokenResponseDTO token;
    private UserResponseDTO user;
}
