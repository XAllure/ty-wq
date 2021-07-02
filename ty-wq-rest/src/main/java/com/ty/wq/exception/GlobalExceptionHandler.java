package com.ty.wq.exception;

import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 * 全局异常捕获
 * @author Administrator
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验异常处理器
     * @param req
     * @param e 参数验证异常
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result parameterExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e){
        recordExceptionLog(req,e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            if (!errors.isEmpty()){
                FieldError fieldError = (FieldError) errors.get(0);
                String message = fieldError.getDefaultMessage();
                message = StringUtils.isEmpty(message) ? CodeEnum.ERROR_PARAMETER.getMsg() : message;
                return Result.error(CodeEnum.ERROR_PARAMETER.getCode(), message);
            }
        }
        return Result.error(CodeEnum.ERROR_PARAMETER);
    }

    /**
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result parameterTypeExceptionHandler(HttpServletRequest req, MethodArgumentTypeMismatchException e){
        recordExceptionLog(req,e);
        return Result.error(CodeEnum.ERROR_PARAMETER.getCode(), "参数类型不匹配");
    }

    /**
     * 参数校验异常处理器
     * @param req
     * @param e 参数验证异常
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException e){
        recordExceptionLog(req,e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (constraintViolations != null && constraintViolations.size() > 0){
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                String message = constraintViolation.getMessageTemplate();
                message = StringUtils.isBlank(message) ? CodeEnum.ERROR_PARAMETER.getMsg() : message;
                return Result.error(CodeEnum.ERROR_PARAMETER.getCode(), message);
            }
        }
        return Result.error(CodeEnum.ERROR_PARAMETER);
    }

    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(HttpServletRequest req, BindException e){
        recordExceptionLog(req,e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            if (!errors.isEmpty()){
                FieldError fieldError = (FieldError) errors.get(0);
                String message = fieldError.getDefaultMessage();
                message = StringUtils.isBlank(message) ? CodeEnum.ERROR_PARAMETER.getMsg() : message;
                return Result.error(CodeEnum.ERROR_PARAMETER.getCode(), message);
            }
        }
        return Result.error(CodeEnum.ERROR_PARAMETER);
    }


    /**
     * 返回json格式
     * @param response
     * @param e
     * @return
     */
    private Result returnJsonExceptionHandler(HttpServletResponse response, Exception e) {
        Result result = Result.error();
        if (e instanceof WqException) {
            WqException imException = (WqException) e;
            result.setCode(imException.getCode());
            result.setMsg(imException.getMsg());
        }
        try {
            CommonUtils.writeJson(result, response);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.error(ioe.getMessage());
        }
        return null;
    }

    @ExceptionHandler(value = Exception.class)
    public Result errHandle(HttpServletRequest request, HttpServletResponse response, Exception e){
        // 记录错误日志
        recordExceptionLog(request,e);
        if (CommonUtils.isPost(request) || CommonUtils.isAjax(request)) {
            return returnJsonExceptionHandler(response, e);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return Result.error(CodeEnum.Request_Method_Not_Supported);
        } else {
            return Result.error(CodeEnum.ERROR_SERVER);
        }
    }

    /**
     * 记录错误日志
     *
     * @param request
     * @param e
     */
    private void recordExceptionLog(HttpServletRequest request, Exception e) {
        log.error("************************异常开始*******************************");
        log.error("错误原因：" + e.getMessage());
        log.error("请求地址：" + request.getRequestURL());
        StackTraceElement[] error = e.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            log.error(stackTraceElement.toString());
        }
        log.error("************************异常结束*******************************");
    }
}
