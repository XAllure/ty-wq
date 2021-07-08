package com.ty.wq.pojo.vo.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route implements Serializable {

    private static final long serialVersionUID = 9015019551322787996L;

    private String id;

    private String ip;

    private Integer port;

}
