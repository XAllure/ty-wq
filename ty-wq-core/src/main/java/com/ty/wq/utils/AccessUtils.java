package com.ty.wq.utils;
import com.ty.wq.constant.Constants;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author Administrator
 */
public class AccessUtils {

    /**
     * 通过token获取用户id
     * @return 返回用户id
     */
    public static Long userId(){
        String token = userToken();
        Object obj = RedisUtils.get(Constants.WQ_USER_LOGIN_KEY + token);
        if (obj == null) {
            // 测试用
            return 1L;
            // return null;
        }
        return Long.valueOf(obj.toString());
    }

    public static String userToken() {
        HttpServletRequest request = CommonUtils.getRequest();
        return request.getHeader(WsTokenUtils.WQ_TOKEN_HEADER);
    }

}
