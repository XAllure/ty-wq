package com.ty.wq.shiro;

import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.utils.CommonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
public class ShiroPermissionsFilter extends PermissionsAuthorizationFilter {

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return this.isAccessAllowed(request, response,mappedValue) || this.onAccessDenied(request, response,mappedValue);
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = this.getSubject(request, response);
        String[] perms = (String[])mappedValue;
        boolean isPermitted = true;
        if(perms != null && perms.length > 0) {
            if(perms.length == 1) {
                if(!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else if(!subject.isPermittedAll(perms)) {
                isPermitted = false;
            }
        }
        return isPermitted;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        if (null == SecurityUtils.getSubject().getPrincipals()) {
            CommonUtils.writeJson(Result.error(CodeEnum.NEED_LOGIN), res);
        }else {
            CommonUtils.writeJson(Result.error(CodeEnum.NO_AUTHORITY), res);
        }
        return false;
    }
}
