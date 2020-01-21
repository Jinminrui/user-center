package com.jmr.usercenter.service.sms;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.jmr.usercenter.domain.dto.sms.SmsRequestDTO;
import com.jmr.usercenter.domain.dto.sms.SmsVerifyCodeDTO;
import com.jmr.usercenter.utils.RedisUtil;
import com.jmr.usercenter.utils.VerifyCodeOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsService {
    private final VerifyCodeOperator verifyCodeOperator;

    private final RedisUtil redisUtil ;

    public String sendSmsVerifyCode(SmsRequestDTO smsRequestDTO) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fsk3ufKP5w3LN61QHhp", "zAGppLb9O9FWCaGCYi2Bl76sGGZw1T");
        IAcsClient client = new DefaultAcsClient(profile);
        String code = verifyCodeOperator.generateVerifyCode();
        SmsVerifyCodeDTO smsVerifyCodeDTO = new SmsVerifyCodeDTO(code);
        String data = JSON.toJSONString(smsVerifyCodeDTO);
        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", smsRequestDTO.getPhoneNum());
        request.putQueryParameter("SignName", "TW团队协作平台");
        request.putQueryParameter("TemplateCode", "SMS_181863390");
        request.putQueryParameter("TemplateParam", data);

        CommonResponse response = client.getCommonResponse(request);
        if(redisUtil.hasKey(smsRequestDTO.getPhoneNum())){
            redisUtil.del(smsRequestDTO.getPhoneNum());
        }
        redisUtil.set(smsRequestDTO.getPhoneNum(),  smsVerifyCodeDTO.getCode(), 360);

        return response.getData();

    }
}
