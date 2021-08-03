package com.ty.wq.controller.manager;

import com.ty.wq.pojo.po.manager.Admin;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.admin.LoginReqVo;
import com.ty.wq.pojo.vo.manager.admin.LoginRespVo;
import com.ty.wq.pojo.vo.manager.menu.MenuRespVo;
import com.ty.wq.service.manager.RoleMenuService;
import com.ty.wq.shiro.ShiroVerifyUser;
import com.ty.wq.utils.ReqVoUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private RoleMenuService roleMenuService;

    @Value("${custom.img.path}")
    private String imgPath;

    @PostMapping("/login")
    @ApiOperation(value = "管理员登录")
    public Result login(@RequestBody LoginReqVo loginReqVo){
        ReqVoUtils.validated(loginReqVo, BaseReqVo.Login.class);
        Subject subject = SecurityUtils.getSubject();
        //登录验证
        subject.login(new ShiroVerifyUser(loginReqVo));
        //生成sessionId
        String sessionId = String.valueOf(subject.getSession().getId());
        //成功返回 sessionId(token)
        Admin admin = (Admin) subject.getPrincipal();
        // 返回菜单
        List<MenuRespVo> menuRespVos = roleMenuService.getAdminRolesMenu(admin.getId());
        LoginRespVo loginResVo = new LoginRespVo(sessionId, admin.getAvatar(), admin.getUsername(), menuRespVos);
        return Result.success(loginResVo);
    }

    /**
     * 头像预览
     * @param multiReq
     * @return
     */
    @PostMapping("/preview")
    public Result previewImg(MultipartHttpServletRequest multiReq) throws IOException {
        MultipartFile avatar = multiReq.getFile("file");
        if(Objects.isNull(avatar)){
            return Result.error("请选择要上传的文件");
        }
        if(avatar.getSize() > 1024*1024*1024){
            return Result.error("文件大小不能超过10M！");
        }
        //获取文件后缀
        assert avatar.getContentType() != null;
        if(avatar.getContentType().contains("image/png, image/jpeg, image/gif")){
            return Result.error("请选择jpg,png格式的图片");
        }
        File savePathFile = new File(imgPath);
        if(!savePathFile.exists()){
            //若不存在该目录，则创建目录
            savePathFile.mkdirs();
        }
        String filename = System.currentTimeMillis() + avatar.getOriginalFilename();
        avatar.transferTo(new File(imgPath+filename));
        return Result.success(filename);
    }

}
