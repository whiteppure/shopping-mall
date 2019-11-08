/*
package com.hbsi.shopping.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbsi.shopping.user.entity.User;
import com.hbsi.shopping.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * Created by EalenXie on 2018/1/11.
 *//*

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception { //配置策略
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(//资源文件路径
                        "/static/**", "/webjars/**", "/META-INF/resources/","/druid/index.html","/swagger-ui.html", "/druid/**",
                        //页面路径
                        //错误页面
                        "/error/404","/error/500","/error/error",
                        //演示页面
                        "/demo/test","/demo/index","/demo/","/demo/pay","/demo/order","/demo/order-submit","/demo/cart",
                        //基本页面
                        "/login", "/shopping-mall/login", "/","/register","/shopping-mall/register","/shopping-mall","/shopping-mall/home",
                        //不登录就可以访问的接口
                        "/shopping-mall/product","/shopping-mall/search",
                        "/productInfo/**",
                        "/user/**",
                        "/images/**",
                        "/aliPay/pay"
                ).permitAll()
                .anyRequest().authenticated()
                .and().
                formLogin()
                .loginPage("/shopping-mall/login")
                .failureForwardUrl("/shopping-mall/login")
                .successForwardUrl("/shopping-mall/home")
                .permitAll()
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
                .permitAll()
                .invalidateHttpSession(true).
                deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler()).
                and()
                .sessionManagement()
                .maximumSessions(10)
                .expiredUrl("/shopping-mall/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //密码加密
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                try {
                    SecurityUser user = (SecurityUser) authentication.getPrincipal();
                    log.info("USER : " + user.getUsername() + " LOGOUT SUCCESS !  ");
                } catch (Exception e) {
                    log.info("LOGOUT EXCEPTION , e : " + e.getMessage());
                }
                httpServletResponse.sendRedirect("/shopping-mall/login");
            }
        };
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() { //登入处理
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                User userDetails = (User) authentication.getPrincipal();
                log.info("USER : " + userDetails.getUserName() + " LOGIN SUCCESS !  ");
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {    //用户登录实现
        return new UserDetailsService() {
            @Autowired
            private IUserService userService;

            @Override
            public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
                User user = userService.getOne(new QueryWrapper<User>().eq("userName", userName));
                if (user == null) throw new UsernameNotFoundException("Username " + userName + " not found");
                return new SecurityUser(user);
            }
        };
    }


    public User getUser() { //为了session从获取用户信息,可以配置如下
        User user = new User();
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) user = (User) auth.getPrincipal();
        return user;
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}*/
