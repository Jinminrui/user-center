package com.jmr.usercenter;

import com.jmr.usercenter.domain.entity.user.User;
import com.jmr.usercenter.service.message.MessageService;
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
    private final MessageService messageService;

    @GetMapping("/message/{id}")
    public Integer test(@PathVariable String id){
        return messageService.getUnReadNum(id);
    }
}
