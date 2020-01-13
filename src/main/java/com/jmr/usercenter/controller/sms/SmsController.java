package com.jmr.usercenter.controller.sms;

import com.aliyuncs.exceptions.ClientException;
import com.jmr.usercenter.service.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/verifyCode")
    public String getVerifyCode() throws ClientException {
        return smsService.sendSmsVerifyCode();
    }
}
