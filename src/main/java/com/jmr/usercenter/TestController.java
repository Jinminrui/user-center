package com.jmr.usercenter;

import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {
    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping("/redis/{id}")
    public void redisTest(@PathVariable Integer id){
        stringRedisTemplate.opsForValue().set("aaaa", String.valueOf(id));
    }
}
