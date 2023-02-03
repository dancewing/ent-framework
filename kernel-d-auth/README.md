# 权限模块

## 系统需要认证的情况下使用

## auth包含认证和鉴权

认证(authentication)和鉴权(authorization)的区别：

- 认证校验你能否登录系统，认证的过程是校验token的过程
- 鉴权校验你有系统的哪些权限，鉴权的过程是校验角色是否包含某些接口的权限

# 例子
你要登陆论坛，输入用户名张三，密码1234，密码正确，证明你张三确实是张三，这就是 authentication；再一check用户张三是个版主，所以有权限加精删别人帖，这就是 authorization。

# 场景
- 认证和鉴权可以独立开，比如admin既提供认证也提供鉴权功能
- gateway只提供鉴权功能
- 其他业务模块只提供鉴权功能，认证由专门的服务提供了

## 模块介绍
- auth-api API核心包
- auth-session 登录会话管理, TOKEN对应的session 信息由此模块提供
- authentication-sdk 认证模块，通常集成到admin中
- authentication-filter 鉴权模块，因为网关提供统一鉴权功能，此模块可以引入
- authentication-sdk 鉴权用的permission service