package com.jmr.usercenter.controller.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.domain.dto.sms.SmsRequestDTO;
import com.jmr.usercenter.service.sms.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/verifyCode")
    public CommonResponseDTO<Object> getVerifyCode(@RequestBody SmsRequestDTO smsRequestDTO) throws ClientException {
        JSONObject res = (JSONObject) JSONObject.parse(smsService.sendSmsVerifyCode(smsRequestDTO));
        if(res.getString("Code").equals("OK")){
            return CommonResponseDTO.builder().code(200).desc("ok").build();
        } else {
            return CommonResponseDTO.builder().code(500).desc("failed").data(res).build();
        }
    }
}
