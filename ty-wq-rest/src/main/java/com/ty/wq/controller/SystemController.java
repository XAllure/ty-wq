package com.ty.wq.controller;

import com.ty.wq.constant.Constants;
import com.ty.wq.enums.ResultEnum;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.LoginReqVo;
import com.ty.wq.pojo.vo.client.user.LoginRespVo;
import com.ty.wq.service.client.UserService;
import com.ty.wq.utils.CommonUtils;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.WsTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/system")
@Slf4j
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

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @PostMapping("/login")
    public Result login(LoginReqVo loginVo) {
        ReqVoUtils.validated(loginVo, BaseReqVo.Login.class);
        LoginRespVo loginRespVo = userService.login(loginVo);
        return Result.success(loginRespVo);
    }

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/logout/{token}")
    public Result logout(@Valid @NotBlank(message = "参数为空") @PathVariable String token) {
        if (!WsTokenUtils.validateToken(token)) {
            return Result.error(ResultEnum.ERROR_TOKEN);
        }
        Long userId = WsTokenUtils.getUserId(token);
        // 删除登录token
        WsTokenUtils.deleteToken(token);
        // 删除用户的服务器信息
        WsTokenUtils.delUserWs(userId);
        return Result.success();
    }

}
