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

    public interface Login{}
    public interface Add{}
    public interface Update{}
    public interface Self{}
    public interface Status{}
    public interface Reset{}
    public interface Chat{}
    public interface Apply{}
    public interface Info{}

}
