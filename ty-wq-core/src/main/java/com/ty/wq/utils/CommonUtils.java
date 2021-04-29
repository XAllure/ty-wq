package com.ty.wq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ty.wq.pojo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author Administrator
 * @version 1.0
 * @program: image-captcha
 * @description: 通用工具类
 * @date 2020/6/10 16:51
 */
@Slf4j
public class CommonUtils {

    /**
     * 获取与jar同路径的地址
     *
     * @return
     */
    public static File getJarPath() {
        File basePath = new ApplicationHome().getDir();
        return basePath;
    }

    /**
     * 获取jar上一级目录的地址
     *
     * @return
     */
    public static File getJarParentPath() {
        return getJarPath().getParentFile();
    }

    /**
     * 获取图片的地址
     *
     * @return
     */
    public static String getImagePath() {
        String imagePath = getJarParentPath().getPath() + File.separator + "image";
        File file = new File(imagePath);
        if (!file.exists()){
            file.mkdir();
        }
        return imagePath + File.separator;
    }

    /**
     * 获取temp的地址
     *
     * @return
     */
    public static String getTemPath() {
        String imagePath = getJarParentPath().getPath() + File.separator + "tem";
        File file = new File(imagePath);
        if (!file.exists()){
            file.mkdir();
        }
        return imagePath + File.separator;
    }

    /**
     * 记录日记
     */
    public static void recordLog(JoinPoint joinPoint){
        HttpServletRequest request = getRequest();
        log.info("----------------------- 记录日志开始 -----------------------");
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("REAL IP : " + CommonUtils.getClientIp(request));
        log.info("浏览器 : " + request.getHeader("user-agent"));
        //log.info("AJAX : " + CommonUtils.isAjax(request));
        //log.info("parameter : " + JSONObject.toJSONString(joinPoint.getArgs()));
        if (!StringUtils.isEmpty(request.getContentType()) && request.getContentType().toLowerCase().contains("application/json")){
            if (joinPoint.getArgs() != null){
                Object[] args = joinPoint.getArgs();
                String param = "";
                if (args != null){
                    if (args.length == 1 && args[0] instanceof ExtendedServletRequestDataBinder){
                        Object target = ((ExtendedServletRequestDataBinder) args[0]).getTarget();
                        param = JSONObject.toJSONString(target);
                    }else {
                        param = Arrays.toString(args);
                    }
                }
                log.info("parameter : " + param);
            }
        }else {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String name = parameterNames.nextElement();
                log.info("name : {},value : {}",name,request.getParameter(name));
            }
        }
    }

    /**
     * 获取当前的request对象
     * @return
     */
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 获取客户端真实IP
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String[] headInfo = {"X-Forwarded-For","Proxy-Client-IP","WL-Proxy-Client-IP","X-Real-IP","HTTP_CLIENT_IP"};
        for (String header : headInfo) {
            String ip = request.getHeader(header);
            if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)){
                if (ip.indexOf(",") != -1){
                    String[] ips = ip.split(",");
                    for (String ipReal : ips){
                        if (!"unknown".equalsIgnoreCase(ipReal)){
                            ip = ipReal;
                            break;
                        }
                    }
                }
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 断是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        // 跨域请可能没有X-Requested-With
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return true;
        }
        String accept = request.getHeader("Accept");
        if (accept != null && accept.indexOf("text/html") == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否post请求
     * @param request
     * @return
     */
    public static boolean isPost(HttpServletRequest request) {
        return  "post".equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断是否是Windows系统
     * @return
     */
    public static boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().contains("window");
    }

    /**
     * 返回json
     * @param result
     * @param response
     * @throws IOException
     */
    public static void writeJson(Result result, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.close();
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String toFirstUpperCase(String str){
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String toFirstLowerCase(String str){
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.substring(0,1).toLowerCase() + str.substring(1);
    }


}
