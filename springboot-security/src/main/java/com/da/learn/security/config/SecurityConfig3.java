package com.da.learn.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义编写实现类UserDetailsService(实际实用)
 */
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//@Configuration
public class SecurityConfig3 extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //用户注销配置
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();

        //配置没有权限自定义跳转页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin()        //自定义自己编写的登录页面
                .loginPage("/login.html")     //登录页面设置
                .loginProcessingUrl("/user/login")       //登录访问的路径 接口  由security框架实现该接口
//                .defaultSuccessUrl("/test/index").permitAll()          //登录成功后，跳转的路径
                .defaultSuccessUrl("/success.html").permitAll()          //登录成功后，跳转的路径
                .and().authorizeRequests()
                .antMatchers("/", "/test/hello", "/user/login").permitAll()     //设置哪些路径可以直接访问，不用认证
                //当前登录用户只有具有admins权限才可以访问路径test/index
//                .antMatchers("/test/index").hasAuthority("admins")
                //当登录用户有其中一个权限就可以访问
//                .antMatchers("/test/index").hasAnyAuthority("admins", "managers")
                //角色
//                .antMatchers("/test/index").hasRole("role1")
                .anyRequest().authenticated()
                .and().csrf().disable();        //关闭csrf防护

    }
}
