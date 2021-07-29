package com.ty.wq.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ty.wq.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description: 日记记录
 * @date 2020/4/22 16:48
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Pointcut("execution(public * com.ty.wq.controller..*.*(..)) && !@annotation(org.springframework.web.bind.annotation.InitBinder)")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = CommonUtils.getRequest();
        request.setAttribute("startTime",System.currentTimeMillis());
        CommonUtils.recordLog(joinPoint);
    }

    public void doAfter(JoinPoint joinPoint) throws Throwable {

    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        String jsonString = null;
        if (ret != null){
            jsonString = JSONObject.toJSONString(ret);
            if (!StringUtils.isEmpty(jsonString) && jsonString.length() > 200){
                jsonString = jsonString.substring(0,200);
            }
        }
        HttpServletRequest request = CommonUtils.getRequest();
        Long startTime = (Long) request.getAttribute("startTime");
        log.info("耗时 : {}ms",(System.currentTimeMillis() - startTime));
        log.info("请求返回 : {}" , jsonString);
        log.info("----------------------- 记录日志结束 -----------------------");
    }

    @AfterThrowing(value = "webLog()", throwing = "exception")
    public void smsApiException(JoinPoint joinPoint, Exception exception){
        HttpServletRequest request = CommonUtils.getRequest();
        String uri = request.getRequestURI();
        log.error("请求 ：" + uri + " 出现异常");
        log.info("----------------------- 记录日志结束 -----------------------");
    }
}
