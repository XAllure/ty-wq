package com.ty.wq.config;

import com.ty.wq.constant.Constants;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.utils.CommonUtils;
import com.ty.wq.utils.WsTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class InterceptorConfig implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        // OPTIONS请求类型直接返回不处理
        if ("OPTIONS".equals(request.getMethod())){
            log.error("跨域请求");
            return false;
        }
        // token 信息校验
        String token = request.getHeader(Constants.WQ_TOKEN_HEADER);
        // token不存在或失效
        if (Objects.isNull(token) || !WsTokenUtils.hasToken(token)){
            //返回需要重新登录的信息
            CommonUtils.writeJson(Result.error(CodeEnum.NEED_LOGIN), response);
            return false;
        }
        log.info("请求token： {}",token);
        // 更新token时效
        WsTokenUtils.setExpire(token);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
