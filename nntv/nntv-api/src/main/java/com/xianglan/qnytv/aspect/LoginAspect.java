package com.xianglan.qnytv.aspect;

import com.xianglan.qnytv.domain.constant.StatusEnum;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.exception.AuthException;
import com.xianglan.qnytv.util.SingletonMapCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Order(0)
@Component
@Aspect
@Slf4j
public class LoginAspect {


    @Before("execution(* com.xianglan.qnytv.controller.*.*(..))")
    public void point(JoinPoint joinPoint) {
        log.info("方法执行前： {}", joinPoint.getSignature().getName());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader("X-ACCESS-Token");
        //查不到token就表示登录失败
        //todo 改成走redis查询
        Object tokenValue = SingletonMapCache.getInstance().get(token);

        if (ObjectUtils.isEmpty(tokenValue)) {
            throw new ConditionException(StatusEnum.AUTH_FAIL.getCode(), StatusEnum.AUTH_FAIL.getMsg());
        }

    }
}
