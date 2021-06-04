package com.ty.wq.shiro;

import com.ty.wq.pojo.vo.Result;
import com.ty.wq.utils.CommonUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
public class ShiroLogoutFilter extends LogoutFilter {

    /**
     * 自定义登出,登出之后,清理当前用户部分缓存信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request,response);
        if (subject.getPrincipal() != null){
            clearCachedAuthorizationInfo(subject);
        }
        // 登出
        subject.logout();
        // 返回登出成功信息
        CommonUtils.writeJson(Result.success(),(HttpServletResponse) response);
        return false;
    }

    /**
     * 清空用户关联权限认证，待下次使用时重新加载
     * @param subject
     */
    public void clearCachedAuthorizationInfo(Subject subject) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(subject.getPrincipals(),getName());
        subject.runAs(principals);
        subject.releaseRunAs();
    }
}
