/*

package com.hbsi.shopping.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()// 对请求授权
                .antMatchers(//资源文件路径
                        "/static/**", "/webjars/**", "/META-INF/resources/", "/druid/index.html", "/swagger-ui.html", "/druid/**",
                        //错误页面
                        "/error/404", "/error/500", "/error/error",
                        //演示页面
                        "/demo/test", "/demo/index", "/demo/", "/demo/pay", "/demo/order", "/demo/order-submit", "/demo/cart",
                        //基本页面
                        "/login", "/shopping-mall/login", "/", "/register", "/shopping-mall/register", "/shopping-mall", "/shopping-mall/home",
                        //不登录就可以访问的接口
                        "/shopping-mall/product", "/shopping-mall/search",
                        "/productInfo/**","/cartProduct/**","/images/**",
                        "/user/**","/address/**", "/cart/**","/comment/**",
                        "/loginOut","/shopping-mall/**",
                        "/aliPay/**"
                ).permitAll()
                .anyRequest() // 任何请求
                .authenticated()//; // 都需要身份认证
                .and()
                .csrf().disable()// 禁用跨站攻击
                .formLogin()
                .loginProcessingUrl("/user/login")
                .loginPage("/shopping-mall/login")
                .failureForwardUrl("/shopping-mall/login")
                .successForwardUrl("/shopping-mall/home")
                .and()
                .logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .sessionManagement()
                .maximumSessions(10)
                .expiredUrl("/shopping-mall/login");
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder(4);
    }


}

*/
