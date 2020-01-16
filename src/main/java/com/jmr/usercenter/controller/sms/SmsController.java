package com.jmr.usercenter.controller.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.jmr.usercenter.domain.dto.sms.SmsRequestDTO;
import com.jmr.usercenter.service.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3009", maxAge = 3600)
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/verifyCode")
    public JSONObject getVerifyCode(@RequestBody SmsRequestDTO smsRequestDTO) throws ClientException {
        return (JSONObject) JSONObject.parse(smsService.sendSmsVerifyCode(smsRequestDTO));
    }
}
