package com.ty.wq.pojo.vo.client.wechatMessage;

import com.ty.wq.anno.SearchCondition;
import com.ty.wq.enums.CompareEnum;
import com.ty.wq.pojo.vo.BaseSearchVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date 2021-06-21 03:41:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WechatMessageSearchVo extends BaseSearchVo {

    private static final long serialVersionUID = -1L;

    @SearchCondition(compare = CompareEnum.GE, filed = "wechat_id")
    private String wechatId;

    @SearchCondition(compare = CompareEnum.GE, filed = "room_wx_id")
    private String roomWxId;

    @SearchCondition(compare = CompareEnum.GE, filed = "wx_id_from")
    private String wxIdFrom;

    @SearchCondition(compare = CompareEnum.GE, filed = "wx_id_to")
    private String wxIdTo;

}
