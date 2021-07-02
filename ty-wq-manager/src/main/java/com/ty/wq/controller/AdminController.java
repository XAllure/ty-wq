package com.ty.wq.controller;
import java.io.IOException;

import com.google.zxing.WriterException;
import com.ty.wq.controller.base.BaseController;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.admin.PasswordReqVo;
import com.ty.wq.service.manager.AdminService;
import com.ty.wq.shiro.ShiroUtils;
import com.ty.wq.utils.*;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.pojo.vo.manager.admin.AdminReqVo;
import com.ty.wq.pojo.vo.manager.admin.AdminRespVo;
import com.ty.wq.pojo.vo.manager.admin.AdminSearchVo;
import com.ty.wq.dao.manager.AdminDao;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-02 09:41:11
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController<Admin, AdminReqVo, AdminRespVo, AdminSearchVo, AdminDao, AdminService> {

    AdminController() {
        methodAll = false;
        methodAdd = false;
    }

    /**
     * 生成谷歌验证码二维码
     * @param key
     * @param response
     * @throws IOException
     * @throws WriterException
     */
    @GetMapping("/qrCode/{key}")
    public void getCode(@NonNull @PathVariable String key, HttpServletResponse response) throws IOException, WriterException {
        GoogleAuthenticatorUtils.createQrCode(key, response);
    }

    /**
     * 添加
     * @param reqVo
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody AdminReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Add.class);
        Admin admin = OrikaUtils.convert(reqVo, Admin.class);
        admin.setSalt(GenerateUtils.generateString(20));
        admin.setSecretKey(GoogleAuthenticatorUtils.createSecretKey());
        admin.setPassword(ShiroUtils.md5(admin.getPassword(), admin.getSalt()));
        service.insert(admin);
        return Result.success();
    }

    /**
     * 重置管理员密码
     * @param id
     * @return
     */
    @PostMapping("/password/reset/{id}")
    public Result reset(@PathVariable @Validated(value = BaseReqVo.Reset.class) Long id) {
        Admin admin = service.findById(id);
        admin.setPassword(ShiroUtils.md5(DigestUtils.md5Hex(ShiroUtils.DEFAULT_PASSWORD), admin.getSalt()));
        service.updateById(admin);
        return Result.success();
    }

    /**
     * 获取个人信息
     * @return
     */
    @PostMapping("/info")
    public Result info() {
        AdminRespVo respVo = OrikaUtils.convert(service.findById(ShiroUtils.getAdminId()), AdminRespVo.class);
        return Result.success(respVo);
    }

    /**
     * 更新谷歌验证码秘钥
     * @return
     */
    @PostMapping("/qrCode/update")
    public Result updateQrCode() {
        return Result.success(GoogleAuthenticatorUtils.createSecretKey());
    }

    /**
     * 修改个人信息
     * @param adminReqVo
     * @return
     */
    @PostMapping("/info/update")
    public Result updateInfo(@RequestBody AdminReqVo adminReqVo) {
        ReqVoUtils.validated(adminReqVo, BaseReqVo.Self.class);
        Admin admin = service.findById(ShiroUtils.getAdminId());
        OrikaUtils.copy(adminReqVo, admin);
        service.updateById(admin);
        return Result.success();
    }

    /**
     * 修改密码
     * @param reqVo
     * @return
     */
    @SneakyThrows
    @PostMapping("/password/update")
    public Result updatePassword(@RequestBody PasswordReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Self.class);
        if (!reqVo.getPassword().equals(reqVo.getConfirmPassword())) {
            return Result.error(CodeEnum.PASSWORD_2_NOT_SAME);
        }
        Admin admin = service.findById(ShiroUtils.getAdminId());
        reqVo.setPassword(ShiroUtils.md5(reqVo.getPassword(), admin.getSalt()));
        if (admin.getPassword().equals(reqVo.getPassword())) {
            return Result.error(CodeEnum.PASSWORD_SAME);
        }
        if (!GoogleAuthenticatorUtils.verify(admin.getSecretKey(), reqVo.getCode())) {
            return Result.error(CodeEnum.ERROR_CODE);
        }
        admin.setPassword(reqVo.getPassword());
        service.updateById(admin);
        // SecurityUtils.getSubject().logout();
        return Result.success();
    }

}
