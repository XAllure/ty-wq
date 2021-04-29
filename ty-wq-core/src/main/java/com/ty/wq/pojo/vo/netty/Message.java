package com.ty.wq.pojo.vo.netty;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class Message {

    private String type;

    private String token;

    private Object data;

}
