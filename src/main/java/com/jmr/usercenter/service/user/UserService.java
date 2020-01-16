package com.jmr.usercenter.service.user;

import com.jmr.usercenter.dao.user.UserMapper;
import com.jmr.usercenter.domain.dto.user.UserLoginDTO;
import com.jmr.usercenter.domain.dto.user.UserUpdateRequestDTO;
import com.jmr.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

import static sun.util.logging.LoggingSupport.log;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserMapper userMapper;

    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    public User loginByAccount(UserLoginDTO userLoginDTO) {
        log.info("用户名{}, 密码{}", userLoginDTO.getUsername(),userLoginDTO.getPassword());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userLoginDTO.getUsername());
        User user = userMapper.selectOneByExample(example);
        if (user.getPassword().equals(userLoginDTO.getPassword())) {
            return user;
        }
        return null;
    }

    public User loginByPhone(UserLoginDTO userLoginDTO){
        log.info("手机号{}, 验证码{}", userLoginDTO.getPhoneNum(),userLoginDTO.getCode());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", userLoginDTO.getPhoneNum());
        User user = userMapper.selectOneByExample(example);
        if (user.getPhone().equals(userLoginDTO.getPhoneNum())) {
            return user;
        }
        return null;
    }

    public Integer update(UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequestDTO, user);
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
