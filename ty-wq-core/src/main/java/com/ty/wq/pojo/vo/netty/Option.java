package com.ty.wq.pojo.vo.netty;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Data
public class Option {

    Map<String, Object> option = new HashMap<>();

    public Option add(String key, Object value) {
        this.getOption().put(key, value);
        return this;
    }

    public static Option option() {
        return new Option();
    }

}
