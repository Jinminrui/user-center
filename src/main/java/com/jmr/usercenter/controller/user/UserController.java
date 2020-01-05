package com.jmr.usercenter.controller.user;

import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.user.JwtTokenResponseDTO;
import com.jmr.usercenter.domain.dto.user.LoginResponseDTO;
import com.jmr.usercenter.domain.dto.user.UserLoginDTO;
import com.jmr.usercenter.domain.dto.user.UserResponseDTO;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.service.user.UserService;
import com.jmr.usercenter.utils.JwtOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final JwtOperator jwtOperator;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return this.userService.findById(id);
    }

    @PostMapping("/login")
    public CommonResponseDTO<LoginResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        User user = this.userService.login(userLoginDTO);
        if (user == null) {
            return CommonResponseDTO.<LoginResponseDTO>builder()
                    .code(400)
                    .data(null)
                    .desc("用户名错误！")
                    .build();
        }
        Map<String,Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getPkId());
        String token = jwtOperator.generateToken(userInfo);

        return CommonResponseDTO.<LoginResponseDTO>builder()
                .code(200)
                .data(LoginResponseDTO
                        .builder()
                        .token(JwtTokenResponseDTO.builder()
                                .token(token)
                                .expirationTime(jwtOperator.getExpirationDateFromToken(token))
                                .build())
                        .user(UserResponseDTO.builder()
                                .id(user.getPkId())
                                .build())
                        .build())
                .build();

    }
}
