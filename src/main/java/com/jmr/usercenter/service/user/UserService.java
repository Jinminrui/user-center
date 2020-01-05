package com.jmr.usercenter.service.user;

import com.jmr.usercenter.dao.user.UserMapper;
import com.jmr.usercenter.domain.dto.user.UserLoginDTO;
import com.jmr.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import static sun.util.logging.LoggingSupport.log;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserMapper userMapper;

    public User findById(Integer id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    public User login(UserLoginDTO userLoginDTO) {
        log.info("用户名{}, 密码{}", userLoginDTO.getUsername(),userLoginDTO.getPassword());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userLoginDTO.getUsername());
        User user = this.userMapper.selectOneByExample(example);
        if (user.getPassword().equals(userLoginDTO.getPassword())) {
            return user;
        }
        return null;
    }
}
