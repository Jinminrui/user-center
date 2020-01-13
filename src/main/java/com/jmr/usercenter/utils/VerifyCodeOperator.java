package com.jmr.usercenter.utils;

import org.springframework.stereotype.Component;

/**
 * 短信验证逻辑梳理
 * 登录：1、客户端调用获取验证码接口
 *      2、后端将发送验证码的任务放到消息队列中执行
 *      3、消息队列发送验证码后，将验证码存到redis缓存
 *      4、客户端携带手机号和验证码调用登录接口
 *      5、后端从redis中取出手机号对应的验证码进行验证是否有效
 *      6、如果有效，颁发token，返回数据
 *
 * 注册：前3步与登录相同
 *      4、客户端携带手机号和验证码调用注册接口
 *      5、后端从redis中取出手机号对应的验证码进行验证是否有效
 *      6、如果有效，执行插入数据操作并返回token，用户获得登录权限。
 */
@Component
public class VerifyCodeOperator {
    public String generateVerifyCode() {
        StringBuilder code = new StringBuilder();
        for(int i = 0; i < 6; i++){
            int random = (int) (Math.random() * 10);
            code.append(random);
        }
        return code.toString();
    }

    public static void main(String[] args) {
        VerifyCodeOperator verifyCodeOperator = new VerifyCodeOperator();
        System.out.println(verifyCodeOperator.generateVerifyCode());
    }
}
