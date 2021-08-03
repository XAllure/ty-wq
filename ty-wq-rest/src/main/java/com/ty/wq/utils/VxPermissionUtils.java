package com.ty.wq.utils;

import com.ty.wq.constant.Constants;
import com.ty.wq.pojo.po.client.DepartmentPermission;
import com.ty.wq.pojo.po.client.Permission;
import com.ty.wq.pojo.po.client.UserDepartment;
import com.ty.wq.service.client.DepartmentPermissionService;
import com.ty.wq.service.client.PermissionService;
import com.ty.wq.service.client.UserDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 权限工具
 * @author Administrator
 */
@Component
@Slf4j
public class VxPermissionUtils {

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private DepartmentPermissionService departmentPermissionService;

    @Autowired
    private PermissionService permissionService;

    public static VxPermissionUtils utils;

    @PostConstruct
    public void init() {
        utils = this;
        utils.userDepartmentService = this.userDepartmentService;
        utils.departmentPermissionService = this.departmentPermissionService;
        utils.permissionService = this.permissionService;
    }

    /**
     * 将用户的权限保存到redis
     * @param userId
     */
    public static void savePermission(Long userId) {
        // 获取用户的部门列表
        List<UserDepartment> userDepartments = utils.userDepartmentService.findByUserId(userId);
        if (userDepartments != null && !userDepartments.isEmpty()) {
            List<Long> departmentIds = new ArrayList<>();
            for (UserDepartment userDepartment : userDepartments) {
                departmentIds.add(userDepartment.getDepartmentId());
            }
            // 获取部门权限列表
            List<DepartmentPermission> departmentPermissions = utils.departmentPermissionService.findByDepartmentIds(departmentIds);
            if (departmentPermissions != null && departmentPermissions.size() > 0) {
                List<Long> permissionIds = new ArrayList<>();
                for (DepartmentPermission departmentPermission : departmentPermissions) {
                    permissionIds.add(departmentPermission.getPermissionId());
                }
                // 去掉重复的权限ID
                HashSet<Long> hs = new HashSet<>(permissionIds);
                permissionIds.clear();
                permissionIds.addAll(hs);
                List<Permission> permissions = utils.permissionService.findByIds(permissionIds);
                if (permissions != null && !permissions.isEmpty()) {
                    List<String> perms = new ArrayList<>();
                    for (Permission permission : permissions) {
                        perms.add(permission.getPermission());
                    }
                    setPermissions(userId, perms);
                    log.info("该用户的权限为：{}", getPermissions(userId));
                }
            }
        }
    }

    /**
     * 删除用户权限
     * @param userId
     */
    public static void delPermission(Long userId) {
        RedisUtils.delete(Constants.USER_PERMISSION_KEY + userId);
    }

    /**
     * 验证是否有权限
     * @param perms
     * @return
     */
    public static boolean isPermitted(List<String> perms) {
        return getPermissions(AccessUtils.userId()).containsAll(perms);
    }

    /**
     * 验证是否有权限
     * @param perms
     * @return
     */
    public static boolean notPermitted(List<String> perms) {
        return !isPermitted(perms);
    }

    /**
     * 保存权限
     * @param userId
     * @param perms
     */
    private static void setPermissions(Long userId, List<String> perms) {
        RedisUtils.set(Constants.USER_PERMISSION_KEY + userId, perms);
    }

    /**
     * 获取权限
     * @param userId
     * @return
     */
    private static ArrayList<?> getPermissions(Long userId) {
        Object permissions = RedisUtils.get(Constants.USER_PERMISSION_KEY + userId);
        return (ArrayList<?>) permissions;
    }

}
