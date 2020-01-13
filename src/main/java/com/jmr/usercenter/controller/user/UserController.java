package com.jmr.usercenter.controller.user;

import com.jmr.usercenter.auth.CheckLogin;
import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.user.JwtTokenResponseDTO;
import com.jmr.usercenter.domain.dto.user.LoginResponseDTO;
import com.jmr.usercenter.domain.dto.user.UserLoginDTO;
import com.jmr.usercenter.domain.dto.user.UserResponseDTO;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.service.user.UserService;
import com.jmr.usercenter.utils.JwtOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3009",maxAge = 3600)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final JwtOperator jwtOperator;

    /**
     * 获取user信息
     * @param id
     * @return User
     */
    @GetMapping("/{id}")
    @CheckLogin
    public CommonResponseDTO<UserResponseDTO> getUserById(@PathVariable Integer id) {
        User user = this.userService.findById(id);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);
        return CommonResponseDTO.<UserResponseDTO>builder().code(200).data(userResponseDTO).desc("success").build();
    }

    /**
     * 账号密码登录
     * @param userLoginDTO
     * @return CommonResponseDTO
     */
    @PostMapping("/loginByAccount")
    public CommonResponseDTO<LoginResponseDTO> loginByAccount(@RequestBody UserLoginDTO userLoginDTO) {
        User user = this.userService.loginByAccount(userLoginDTO);
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

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);

        return CommonResponseDTO.<LoginResponseDTO>builder()
                .code(200)
                .data(LoginResponseDTO
                        .builder()
                        .token(JwtTokenResponseDTO.builder()
                                .token(token)
                                .expirationTime(jwtOperator.getExpirationDateFromToken(token))
                                .build())
                        .user(userResponseDTO)
                        .build())
                .build();
    }

//    @PostMapping("/loginByPhone")
//    public CommonResponseDTO<LoginResponseDTO> loginByPhone (@RequestBody String verifyCode){
//
//    }
}
