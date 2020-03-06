package com.jmr.usercenter.controller.user;

import com.jmr.usercenter.auth.CheckLogin;
import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.user.*;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.service.team.TeamService;
import com.jmr.usercenter.service.user.UserService;
import com.jmr.usercenter.utils.JwtOperator;
import com.jmr.usercenter.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;
    private final TeamService teamService;
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
    public CommonResponseDTO<UserResponseDTO> getUserById(@PathVariable String id) {
        User user = userService.findById(id);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        if(user.getPassword() == null) {
            userResponseDTO.setHasPassword(false);
        }
        userResponseDTO.setHasPassword(true);
        String teamId = teamService.getTeamIdByUserId(id);

        // TODO 查询太多次数据库了，可以调优

        if(teamId == null) {
            userResponseDTO.setTeam(null);
        } else {
            userResponseDTO.setTeam(teamService.getTeamInfoById(teamId));
        }

        BeanUtils.copyProperties(user, userResponseDTO);
        return CommonResponseDTO.<UserResponseDTO>builder().code(200).data(userResponseDTO).desc("success").build();
    }

    @PostMapping("/loginByPhone")
    public CommonResponseDTO<LoginResponseDTO> loginByPhone (@RequestBody UserLoginDTO userLoginDTO) throws IOException {
        String phoneNum = userLoginDTO.getPhoneNum();
        String code = userLoginDTO.getCode();
        // 从redis缓存里取出手机号对应的验证码，如果正确，进行登录的逻辑
        if(redisUtil.get(phoneNum) == null) {
            return CommonResponseDTO.<LoginResponseDTO>builder().code(400).desc("验证码无效").build();
        }
        if(redisUtil.get(phoneNum).equals(code)){
            User user = userService.loginByPhone(userLoginDTO);
            return getSuccessLoginResponseDTO(user);
        }
        return CommonResponseDTO.<LoginResponseDTO>builder().code(500).desc("登录失败").build();
    }


    /**
     * 账号密码登录
     *
     * @param  userLoginDTO
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
            return CommonResponseDTO.builder().code(500).desc("更新失败").build();
        }
    }

    /**
     * 根据team获取成员列表
     * @param teamId
     * @return List<User>
     */
    @GetMapping("/getAllUsersByTeam")
    @CheckLogin
    public CommonResponseDTO<List<Map<String,Object>>> getAllUsersByTeam(@RequestParam(value = "teamId", required = true) String teamId){
        List<Map<String, Object>> userList = userService.getAllUsersByTeam(teamId);
        return CommonResponseDTO.<List<Map<String, Object>>>builder().code(200).data(userList).desc("success").build();
    }
}
