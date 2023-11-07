package com.xianglan.qnytv.aspect;

import com.xianglan.qnytv.domain.constant.StatusEnum;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.exception.AuthException;
import com.xianglan.qnytv.util.SingletonMapCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Order(0)
@Component
@Aspect
@Slf4j
public class LoginAspect {


    public static final String X_ACCESS_TOKEN = "X-Access-Token";


    @Before("execution(* com.xianglan.qnytv.controller.*.*(..))")
    public void point(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        log.info("方法执行前： {}", joinPoint.getSignature().getName());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader(X_ACCESS_TOKEN);
        //查不到token就表示登录失败
        //todo 改成走redis查询
        Boolean existToken = existToken(token);

        boolean skipAuth = isSkipAuth(joinPoint);
        if (skipAuth) {
            return;
        }

        //不存在且是没跳过登录
        if (!existToken) {
            throw new ConditionException(StatusEnum.AUTH_FAIL.getCode(), StatusEnum.AUTH_FAIL.getMsg());
        }

    }

    /**
     * 不存在token
     *
     * @param token
     * @return
     */
    private static Boolean existToken(String token) {

        return SingletonMapCache.getInstance().containsKey(token);
    }

    /**
     * 是否是跳过校验的方法
     *
     * @param joinPoint
     * @return
     */
    private static boolean isSkipAuth(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Annotation[] annotations = method.getDeclaredAnnotations();
        boolean skipAuth = false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(SkipAuth.class)) {
                skipAuth = true;
                break;
            }
        }
        return skipAuth;
    }
}
