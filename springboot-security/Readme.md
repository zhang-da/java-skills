## SpringSecurity本质是一个过滤器链
有很多过滤器
三个代表性过滤器
- FilterSecurityInterceptor: 方法级过滤器，位于过滤器最底部
- ExceptionTranslationFilter: 异常过滤器，处理在授权过程中出现的异常
- UsernamePasswordAuthenticationFilter: 对login的Post请求拦截，校验用户名密码

## 过滤器如何进行加载
springboot自动使用SpringSecurity配置过滤器
    - DelegatingFilterProxy
    - FilterChainProxy

## UserDetailsService  --重要接口之一
获取用户名密码，查数据库

- 创建类继承UsernamePasswordAuthenticationFilter，重写attemptAuthentication、successfulAuthentication、unsuccessfulAuthentication
- 实现UserDetailsService，编写查询数据库，返回User对象

## PasswordEncoder  --重要接口之二
数据加密接口，用于返回User对象里密码加密

## 认证授权
1. 认证
- 设置登录的用户名和密码
    - 通过配置文件
    - 通过配置类
    - 自定义编写实现类UserDetailsService(实际实用)
        - 创建配置类，设置哪个userDetailsService实现类
        - 编写接口的实现类，返回User对象
2. 基于角色的权限控制
- hasAuthority  当前主体具有指定权限，则返回true
- hasAnyAuthority 当前主体具有指定任一权限，则返回true
- hasRole    具有给定角色
- hasAnyRole   具有给定任一角色

## 注解
1. @Secured 用户具有某个角色，可以访问方法
- @EnableGlobalMethodSecurity(securedEnabled = true)
- Controller里方法上使用注解

2. @PreAuthorize 方法执行前校验
- @EnableGlobalMethodSecurity(prePostEnabled = true)

3. @PostAuthorize 方法执行后校验,没权限也执行方法，不返回  使用场景不多
- @EnableGlobalMethodSecurity(prePostEnabled = true)

4. @PostFilter 对返回数据过滤  使用场景不多

5. @PreFilter 对入参过滤  使用场景不多

## 用户注销
- 配置类中加配置

## 记住我 （自动登录）

## csrf
