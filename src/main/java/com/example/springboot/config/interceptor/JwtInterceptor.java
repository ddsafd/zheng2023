package com.example.springboot.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;


public class JwtInterceptor implements HandlerInterceptor {
//
//    @Autowired
//    private IUserService userService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String token = request.getHeader("token");
//        if (StrUtil.isBlank(token)) {
//            token = request.getParameter("token");
//        }
//        // 如果不是映射到方法直接通过
//        if(handler instanceof HandlerMethod) {
//            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
//            if (annotation == null) {//如果没有这个注解则放行
//                return true;
//            }
//        }
//        // 执行认证
//        if (StrUtil.isBlank(token)) {
//            throw new ServiceException(Constants.CODE_401, "无token，请重新登录");
//        }
//        // 获取 token 中的 user id
//        String userId;
//        try {
//            userId = JWT.decode(token).getAudience().get(0);
//        } catch (JWTDecodeException j) {
//            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
//        }
//        // 根据token中的userid查询数据库
//        User user = userService.getById(userId);
//        if (user == null) {
//            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
//        }
//        // 使用固定的、安全的密钥来进行JWT签名 (而不是用户的密码)
//        String jwtSecretKey = "YOUR_SECRET_KEY";  // 这应该存储在一个安全的地方，比如环境变量中
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtSecretKey)).build();
//        try {
//            jwtVerifier.verify(token); // 验证token
//        } catch (JWTVerificationException e) {
//            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
//        }
//        return true;
//    }
}
