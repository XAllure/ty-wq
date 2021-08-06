package com.ty.wq.config;

import com.ty.wq.shiro.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置自定义Realm
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm userRealm = new ShiroRealm();
        //配置使用哈希密码匹配
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    @Bean
    public CacheManager shiroCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 配置security并设置userReaml，避免xxxx required a bean named 'authorizer' that could not be found.的报错
     * @return
     */
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(shiroCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 自定义sessionManager
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        return sessionManager;
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new MemorySessionDAO();
    }

    /**
     * 配置url过滤器
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        /*
            anon：   无需认证即可访问
            authc：  必须认证才能访问
            user：   必须拥有 记住我 才能访问
            perms：  拥有对某个资源的权限才能访问
            roles：   拥有某个角色权限才能访问
        */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //添加shiro的内置过滤器
        //配置不会被拦截的链接，顺序判断，
        //anon:所有url都可以匿名访问,无需认证
        filterChainDefinitionMap.put("/system/login", "anon");
        /*filterChainDefinitionMap.put("/system/preview", "anon");*/
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/admin/qrCode/**", "anon");
        //authc:所有url必须通过认证才能访问，
        filterChainDefinitionMap.put("/system/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //自定义过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //检测是否登录等
        filterMap.put("authc", new ShiroAuthorizationFilter());
        //配置自定义登出 覆盖 logout 之前默认的LogoutFilter
        filterMap.put("logout", new ShiroLogoutFilter());
        shiroFilter.setFilters(filterMap);
        return shiroFilter;
    }


    /**
     * 设置用于匹配密码的CredentialsMatcher
     * @return
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        matcher.setHashAlgorithmName(ShiroUtils.HASH_ALGORITHM_NAME);
        // 散列的次数，比如散列三次，相当于md5(md5(md5("")));
        matcher.setHashIterations(ShiroUtils.HASH_ITERATIONS);
        return matcher;
    }

}
