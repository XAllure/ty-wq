package com.ty.wq.shiro;

import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
@Slf4j
public class ShiroAuthorizationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        CommonUtils.writeJson(Result.error(CodeEnum.NEED_LOGIN), res);
        log.info(Result.error(CodeEnum.NEED_LOGIN).toString());
        return false;
    }

}
