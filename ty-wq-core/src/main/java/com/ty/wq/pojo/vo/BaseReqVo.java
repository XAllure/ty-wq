package com.ty.wq.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class BaseReqVo implements Serializable {

    private static final long serialVersionUID = -6758663212374473172L;

    @NotNull(message = "id不能为空", groups = {Update.class, Status.class})
    protected Long id;

    public interface Login{} // 登录
    public interface Get{} // 获取
    public interface Add{} // 添加
    public interface Update{} // 修改
    public interface Delete{} //删除
    public interface Self{} // 修改自身
    public interface Status{} // 状态
    public interface Reset{} // 重置
    public interface Chat{} // 聊天
    public interface At{} // 群@
    public interface Info{} // 信息
    public interface Top{} // 置顶
    public interface Disturb{} // 免打扰
    public interface Out{} // 踢群成员
    public interface Invite{} //邀请群成员
    public interface Search{} //邀请群成员

}
