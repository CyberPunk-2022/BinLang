package com.xianglan.qnytv.support;

import com.xianglan.qnytv.aspect.LoginAspect;
import com.xianglan.qnytv.domain.exception.ConditionException;
import com.xianglan.qnytv.service.util.TokenUtil;
import com.xianglan.qnytv.util.SingletonMapCache;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserSupport {


    @Deprecated
    public Long getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if (userId < 0) {
            throw new ConditionException("非法用户！");
        }
        return userId;
    }


    public Long getCurrentUseUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader(LoginAspect.X_ACCESS_TOKEN);
        return (Long) SingletonMapCache.getInstance().get(token);
    }
}
