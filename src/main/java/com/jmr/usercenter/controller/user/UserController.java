package com.jmr.usercenter.controller.user;

import com.jmr.usercenter.auth.CheckLogin;
import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.user.*;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.service.user.UserService;
import com.jmr.usercenter.utils.JwtOperator;
import com.jmr.usercenter.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3009", maxAge = 3600)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final JwtOperator jwtOperator;
    private final RedisUtil redisUtil;

    /**
     * 获取user信息
     *
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

    @PostMapping("/loginByPhone")
    public CommonResponseDTO<LoginResponseDTO> loginByPhone (@RequestBody UserLoginDTO userLoginDTO){
        String phoneNum = userLoginDTO.getPhoneNum();
        String code = userLoginDTO.getCode();
        if(redisUtil.get(phoneNum).equals(code)){
            User user = userService.loginByPhone(userLoginDTO);
            return getSuccessLoginResponseDTO(user);
        }
        return CommonResponseDTO.<LoginResponseDTO>builder().code(400).desc("验证码无效").build();
    }


    /**
     * 账号密码登录
     *
     * @param userLoginDTO
     * @return CommonResponseDTO
     */
    @PostMapping("/loginByAccount")
    public CommonResponseDTO<LoginResponseDTO> loginByAccount(@RequestBody UserLoginDTO userLoginDTO) {
        User user = this.userService.loginByAccount(userLoginDTO);
        return getSuccessLoginResponseDTO(user);
    }

    private CommonResponseDTO<LoginResponseDTO> getSuccessLoginResponseDTO(User user) {
        if (user == null) {
            return CommonResponseDTO.<LoginResponseDTO>builder()
                    .code(400)
                    .data(null)
                    .desc("用户信息错误！")
                    .build();
        }
        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put("id", user.getPkId());
        String token = jwtOperator.generateToken(userInfo);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
        String expirationTime =  sdf.format(jwtOperator.getExpirationDateFromToken(token));

        return CommonResponseDTO.<LoginResponseDTO>builder()
                .code(200)
                .data(LoginResponseDTO
                        .builder()
                        .token(JwtTokenResponseDTO.builder()
                                .token(token)
                                .expirationTime(expirationTime)
                                .build())
                        .user(userResponseDTO)
                        .build())
                .build();
    }

    @PostMapping("/update")
    @CheckLogin
    public CommonResponseDTO<Object> update(@RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        if (userService.update(userUpdateRequestDTO).equals(1)) {
            return CommonResponseDTO.builder().code(200).desc("更新成功").build();
        } else {
            return CommonResponseDTO.builder().code(200).desc("更新失败").build();
        }
    }
}
