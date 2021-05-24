package com.ty.wq.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.constant.MsgType;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.po.client.User;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.client.user.LoginReqVo;
import com.ty.wq.pojo.vo.client.user.LoginRespVo;
import com.ty.wq.pojo.vo.client.user.UserRespVo;
import com.ty.wq.pojo.vo.client.user.UserSearchVo;
import com.ty.wq.pojo.vo.netty.Message;
import com.ty.wq.service.client.UserService;
import com.ty.wq.utils.ChannelUtils;
import com.ty.wq.utils.MsgUtils;
import com.ty.wq.utils.ReqVoUtils;
import com.ty.wq.utils.WsTokenUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
    public Result login(@RequestBody LoginReqVo loginVo) {
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
        if (!WsTokenUtils.hasToken(token)) {
            return Result.error(CodeEnum.ERROR_TOKEN);
        }
        Long userId = WsTokenUtils.getUserId(token);
        // 删除登录token
        WsTokenUtils.delToken(token);
        // 删除用户的服务器信息
        WsTokenUtils.delUserWs(userId);
        return Result.success();
    }

    /*@PostMapping("/search")
    public Result search(UserSearchVo searchVo) {
        Page<UserRespVo> userRespVoPage = userService.findPage(searchVo, UserRespVo.class);
        System.out.println(userRespVoPage);
        User user = userService.findByUsername(searchVo.getUsername());
        Channel channel = ChannelUtils.userChannel(user.getId());
        MsgUtils.writeJson(channel, Message.success(MsgType.RECEIVED, userRespVoPage));
        return Result.success(userRespVoPage);
    }*/

}
