package com.ty.wq.controller;
import java.io.IOException;
import java.sql.Timestamp;

import com.google.zxing.WriterException;
import com.ty.wq.controller.base.BaseController;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.enums.StatusEnum;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.StatusReqVo;
import com.ty.wq.service.manager.AdminService;
import com.ty.wq.shiro.ShiroUtils;
import com.ty.wq.utils.*;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.pojo.vo.manager.admin.AdminReqVo;
import com.ty.wq.pojo.vo.manager.admin.AdminRespVo;
import com.ty.wq.pojo.vo.manager.admin.AdminSearchVo;
import com.ty.wq.dao.manager.AdminDao;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    @ApiOperation(value = "生成谷歌验证码二维码")
    public void getCode(@NonNull @PathVariable String key, HttpServletResponse response) throws IOException, WriterException {
        GoogleAuthenticatorUtils.createQrCode(key, response);
    }

    /**
     * 添加
     * @param reqVo
     * @return
     */
    @Override
    @PostMapping("/insert")
    public Result add(@RequestBody AdminReqVo reqVo) {
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

}
