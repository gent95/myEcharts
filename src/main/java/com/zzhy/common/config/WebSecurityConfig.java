package com.zzhy.common.config;

import com.alibaba.fastjson.JSONObject;
import com.zzhy.common.util.Constant;
import com.zzhy.common.util.R;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by majt on 2018-06-20.
 * 登录配置
 */
//@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {


    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Bean
    public HttpMessageConverter<String> reponseBodyConvert() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(reponseBodyConvert());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
        //排除拦截
        List<String> excludes = new ArrayList<>();
        excludes.add("/login");
        excludes.add("/login/**");
        excludes.add("/static/main/**");
        excludes.add("/static/css/**");
        excludes.add("/static/js/**");
        excludes.add("/static/images/**");
        excludes.add("/static/template/**");
//        addInterceptor.excludePathPatterns(excludes);

        //配置拦截
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1800);
            if (session.getAttribute(Constant.SESSION_KEY) != null) {
                return true;
            }

            // 判断ajax
            boolean isAjaxRequest = false;// 为true时，表示当前请求为ajax请求
            // 如果是ajax请求响应头会有，x-requested-with,ajax
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase(
                    "XMLHttpRequest")) {
                isAjaxRequest = true;
            }
            if (isAjaxRequest) {
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(JSONObject.toJSONString(R.error(110, "请登录")));
            } else {
                response.sendRedirect("/static/main/login.html");
            }
            return false;
        }
    }
}
