package com.ty.wq.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class BaseVo implements Serializable {
    private static final long serialVersionUID = -404217474408627321L;

    private String encryptData;

    private String encryptKey;
}
