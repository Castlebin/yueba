package com.xteam.ggq.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.xteam.ggq.model.bo.User;
import com.xteam.ggq.web.annotation.DoNotNeedLogin;
import com.xteam.ggq.web.controller.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Slf4j
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String JSON_UTF8 = "application/json;charset=UTF-8";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            Method method = ((HandlerMethod) handler).getMethod();
            Annotation doNotNeedLogin = method.getAnnotation(DoNotNeedLogin.class);
            // 如果标记了无需登录则不用检验
            if (doNotNeedLogin != null) {
                return true;
            }

            User user = (User) request.getSession().getAttribute("user");
            Assert.notNull(user, "用户未登录！");
        } catch (Exception e) {
            response.setHeader(HEADER_CONTENT_TYPE, JSON_UTF8);
            response.getWriter().write(JSON.toJSONString(ApiResponse.returnFail(-1, "用户未登录！")));
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
