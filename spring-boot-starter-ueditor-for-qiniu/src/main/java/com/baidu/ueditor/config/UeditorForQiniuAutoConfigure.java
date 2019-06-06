package com.baidu.ueditor.config;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.util.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author huxudong
 * 拦截ueditor上传资源请求
 */

@Configuration
@EnableConfigurationProperties(UeditorQiniuProperties.class)
@ComponentScan(basePackages = {"com.baidu"})
public class UeditorForQiniuAutoConfigure implements WebMvcConfigurer {

    @Autowired
    UeditorQiniuProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 七牛云参数赋值
        QiNiuUtil.properties = this.properties;
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                ServletOutputStream out = null;
                try {
                    out = response.getOutputStream();
                    out.print(new ActionEnter(request, properties.getConfig()).exec());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                // false直接返回response
                return false;
            }
        }).addPathPatterns(properties.getServerUrl());
    }
}
