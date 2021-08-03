package com.ty.wq.controller.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.controller.BaseController;
import com.ty.wq.dao.client.UserDao;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.UserReqVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.pojo.vo.client.user.UserSearchVo;
import com.ty.wq.service.client.UserService;
import com.ty.wq.utils.GenerateUtils;
import com.ty.wq.utils.Md5Utils;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, UserReqVo, UserRespVo, UserSearchVo, UserDao, UserService> {

    UserController() {
        methodAll = false;
        methodAdd = false;
    }

    /**
     * 添加
     * @param reqVo
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody UserReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Add.class);
        User user = OrikaUtils.convert(reqVo, User.class);
        user.setSalt(GenerateUtils.generateString(20));
        user.setPassword(Md5Utils.encryptSalt(user.getPassword(), user.getSalt()));
        service.insert(user);
        return Result.success();
    }

    /**
     * 条件列表分页
     * @param sv
     * @return
     */
    @Override
    @GetMapping("/list/condition")
    public Result listByCondition(UserSearchVo sv){
        Page<UserRespVo> vPage = service.findPage(sv, UserRespVo.class);
        vPage.setRecords(service.toPageUsers(vPage.getRecords()));
        return Result.success(vPage);
    }

}
