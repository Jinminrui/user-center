package com.jmr.usercenter.auth;

import com.jmr.usercenter.exceptions.SecurityException;
import com.jmr.usercenter.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckLoginAspect {
    private final JwtOperator jwtOperator;
    @Around("@annotation(com.jmr.usercenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) {
        try {
            // 1. 从header里获取token
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("UserToken");
            log.info("登录token为：{}",token);

            // 2. 判断token是否有效
            Boolean isValid = jwtOperator.validateToken(token);
            log.info("token是否有效：{}",isValid);
            if(!isValid){
                throw new SecurityException("Token 不合法");
            }

            // 3. 如果校验成功 就将用户的信息设置到request的attribute里面
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.get("id"));

            return point.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException("Token 不合法");
        }
    }
}
