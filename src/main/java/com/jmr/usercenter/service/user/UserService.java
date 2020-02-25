package com.jmr.usercenter.service.user;

import com.jmr.usercenter.dao.user.UserMapper;
import com.jmr.usercenter.domain.dto.user.UserLoginDTO;
import com.jmr.usercenter.domain.dto.user.UserUpdateRequestDTO;
import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.utils.RedisUtil;
import com.jmr.usercenter.utils.UUIDOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserMapper userMapper;
    private final UUIDOperator uuidOperator;
    private final RedisUtil redisUtil;

    public User findById(String id) {
        if (redisUtil.hasKey(id) && redisUtil.get(id) != null) {
            log.info("从redis中读取{}的信息", id);
            return (User) redisUtil.get(id);
        }
        User user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            redisUtil.set(id, user);
        }
        return user;
    }

    public User loginByAccount(UserLoginDTO userLoginDTO) {
        log.info("账号{}, 密码{}", userLoginDTO.getPhoneNum(), userLoginDTO.getPassword());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", userLoginDTO.getPhoneNum());
        User user = userMapper.selectOneByExample(example);
        if (user.getPassword() == null) {
            return null;
        }
        if (user.getPassword().equals(userLoginDTO.getPassword())) {
            return user;
        }
        return null;
    }

    public User loginByPhone(UserLoginDTO userLoginDTO) {
        log.info("手机号{}, 验证码{}", userLoginDTO.getPhoneNum(), userLoginDTO.getCode());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", userLoginDTO.getPhoneNum());
        User user = userMapper.selectOneByExample(example);
        if (user != null && user.getPhone().equals(userLoginDTO.getPhoneNum())) {
            return user;
        }
        User newUser = User.builder()
                .pkId(uuidOperator.getUUid())
                .phone(userLoginDTO.getPhoneNum())
                .avatar("http://oss.jinminrui.cn/avatars/default.svg")
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        // TODO 这里还有一段邀请的逻辑
        // 发送邀请时，以被邀请人的手机号为hash item，邀请人的信息和团队id组成json字符串为value存入redis
        // 在这里判断一下redis 是否有当前用户的被邀请信息

        register(newUser);

        return userMapper.selectOneByExample(example);
    }

    public void register(User user) {
        userMapper.insertSelective(user);
    }


    public Integer update(UserUpdateRequestDTO userUpdateRequestDTO) {
        redisUtil.del(userUpdateRequestDTO.getPkId());
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequestDTO, user);
        user.setUpdateTime(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public List<User> getAllUsersByTeam(String teamId) {
        return userMapper.getAllUsersByTeam(teamId);
    }
}
