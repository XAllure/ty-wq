package com.ty.wq.controller;

import com.ty.wq.constant.Constants;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.LoginReqVo;
import com.ty.wq.pojo.vo.client.user.LoginRespVo;
import com.ty.wq.service.client.UserService;
import com.ty.wq.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/system")
@Slf4j
@Api(tags = "系统相关")
public class SystemController {

    @Autowired
    private UserService userService;

    /*private List<String> badWordsList;

    @PostConstruct
    public synchronized void loadBadWordsList(){
        log.info("load badwords start");
        if (badWordsList == null){
            badWordsList = new ArrayList<>();
        }
        //BufferedReader reader = null;
        File file = new File(CommonUtils.getJarPath() + "/" + Constants.BADWORDS_FILE_NAME);
        System.out.println(file.getPath());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = null;
            List<ZcString> zcList = new ArrayList<>();
            while ((line = reader.readLine()) != null){
                String[] str = line.split("\\,");
                for (int i = 0; i < str.length; i++){
                    zcList.add(new ZcString(str[i]));
                }
            }
            this.badWordsList.clear();
            Collections.sort(zcList);
            zcList.forEach(zc -> {
                badWordsList.add(zc.toString());
            });
            badWordsList.remove(" ");
            badWordsList.remove("");
            log.info("load badwords end");
        }catch (IOException e){
            log.error("load badwords failed");
            e.printStackTrace();
        }
    }*/

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginReqVo loginVo) {
        ReqVoUtils.validated(loginVo, BaseReqVo.Login.class);
        LoginRespVo loginRespVo = userService.login(loginVo);
        // 存储用户的权限
        VxPermissionUtils.savePermission(loginRespVo.getUser().getId());
        return Result.success(loginRespVo);
    }

    @ApiOperation(value = "用户退出登录")
    @PostMapping("/logout")
    public Result logout() {
        String token = AccessUtils.userToken();
        if (!WsTokenUtils.hasToken(token)) {
            return Result.error(CodeEnum.ERROR_TOKEN);
        }
        Long userId = WsTokenUtils.getUserId(token);
        // 删除登录token
        WsTokenUtils.delToken(token);
        // 删除用户的服务器信息
        WsTokenUtils.delUserWs(userId);
        // 删除用户权限信息
        VxPermissionUtils.delPermission(userId);
        return Result.success();
    }

    @ApiOperation(value = "轮询回调客户端登录")
    @PostMapping("/sr/login")
    public Result srLogin(@RequestBody LoginReqVo vo) {
        ReqVoUtils.validated(vo, BaseReqVo.Login.class);
        LoginRespVo respVo = userService.srLogin(vo);
        return Result.success(respVo);
    }

    @ApiOperation(value = "轮询回调客户端退出登录")
    @PostMapping("/sr/logout")
    public Result srLogout() {
        String token = AccessUtils.userToken();
        String key = Constants.SR_LOGIN_KEY.concat(token);
        if (!RedisUtils.hasKey(key)) {
            return Result.error(CodeEnum.ERROR_TOKEN);
        }
        RedisUtils.delete(key);
        return Result.success();
    }

}
