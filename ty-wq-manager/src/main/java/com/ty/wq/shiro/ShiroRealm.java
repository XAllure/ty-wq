package com.ty.wq.shiro;

import com.ty.wq.enums.StatusEnum;
import com.ty.wq.exception.VerifyCodeException;
import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.pojo.po.manager.Authority;
import com.ty.wq.service.manager.AdminRoleService;
import com.ty.wq.service.manager.AdminService;
import com.ty.wq.service.manager.AuthorityService;
import com.ty.wq.service.manager.RoleAuthorityService;
import com.ty.wq.utils.GoogleAuthenticatorUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Administrator
 */
public class ShiroRealm extends AuthorizingRealm implements Serializable {

    private static final long serialVersionUID = 3199730348689886694L;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RoleAuthorityService roleAuthorityService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Object principal = principalCollection.getPrimaryPrincipal();
        Admin admin;
        if (principal instanceof Admin){
            //拿到user对象
            admin = (Admin) principal;
        }else {
            admin = adminService.findByUsername(principal.toString());
        }
        if ("admin".equals(admin.getUsername())) {
            List<Authority> authorities = authorityService.getAllNormal();
            for (Authority authority : authorities) {
                if (StringUtils.isNotBlank(authority.getPermission())) {
                    info.addStringPermission(authority.getPermission());
                }
            }
            return info;
        }
        List<Long> roleIds = adminRoleService.getRoleIdsByAdminId(admin.getId());
        List<Long> authIds = new ArrayList<>();
        for (Long roleId : roleIds) {
            authIds.addAll(roleAuthorityService.findAuthIdsByRoleId(roleId));
        }
        //去掉重复的权限id
        HashSet<Long> hs = new HashSet<>(authIds);
        //清空
        authIds.clear();
        //重新赋值
        authIds.addAll(hs);
        List<Authority> authorities = authorityService.findChildrenByIds(authIds);
        for (Authority authority : authorities) {
            info.addStringPermission(authority.getPermission());
        }
        return info;
    }

    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ShiroVerifyUser userToken = (ShiroVerifyUser)authenticationToken;
        Admin admin = adminService.findByUsername(userToken.getUsername());
        //账号认证
        if (admin == null){
            //抛出异常
            return null;
        }
        if (admin.getStatus().equals(StatusEnum.LOCKED.getCode())){
            throw new LockedAccountException();
        }
        // 判断谷歌验证码
        if (!GoogleAuthenticatorUtils.verify(admin.getSecretKey(), userToken.getCode())){
            throw new VerifyCodeException();
        }
        // 密码认证 shiro来做
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                admin,
                admin.getPassword(),
                ByteSource.Util.bytes(admin.getSalt()),
                getName()
        );
        SecurityUtils.getSubject().getSession().setAttribute(admin.getUsername(), admin);
        admin.setLoginTime(new Timestamp(System.currentTimeMillis()));
        adminService.updateById(admin);
        return authenticationInfo;
    }

    /**
     * 清除授权信息
     * @param principals
     */
    public void clearAuthorization(PrincipalCollection principals){
        clearCachedAuthorizationInfo(principals);
    }

}
