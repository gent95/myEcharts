package com.zzhy.common.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO 通过此类的getBean会默认执行将Bean交给spring容器管理
 * @Author; majt
 * @Email: gentmjt@gmail.com
 * @Date: 2018/11/8 17:44
 * @Version: 1.0
 */

@Component
@EnableAutoConfiguration
public class ApplicationContextUtils implements ApplicationContextAware {
    public static ApplicationContext applicationContext=null;
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }
}
