package com.ty.wq.shiro;

import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.service.manager.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-qdd
 * @description:
 * @date 2020/12/17 18:46
 */
public class ShiroUtils {

    public static AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        ShiroUtils.adminService = adminService;
    }

    public final static String AUTHORIZATION = "Authorization";

    /**  加密算法 */
    public final static String HASH_ALGORITHM_NAME = "md5";
    /**  循环次数 */
    public final static int HASH_ITERATIONS = 3;

    public final static String DEFAULT_PASSWORD = "12345678";

    public static String md5(String password,String salt){
        return new Md5Hash(password, salt, HASH_ITERATIONS).toString();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Admin getAdmin() {
        return (Admin) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getAdminId() {
        return getAdmin().getId();
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 重新赋值权限
     * @param shiroRealm 自定义的realm
     * @param username  用户名
     */
    public static void reloadAuthorizing(ShiroRealm shiroRealm, String username){
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();
        Admin admin = adminService.findByUsername(username);
        SimplePrincipalCollection simplePrincipalCollection = new SimplePrincipalCollection(admin, realmName);
        subject.runAs(simplePrincipalCollection);
        shiroRealm.clearAuthorization(subject.getPrincipals());
        subject.releaseRunAs();
    }
}
