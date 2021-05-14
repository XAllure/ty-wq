package com.ty.wq.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.pojo.vo.BaseSearchVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Administrator
 * @version 1.0
 * @description: 页面请求参数工具类
 * @date 2020/9/11 10:16
 */
public class PageUtils {

    /**
     * 当前页
     * @param pageNum
     * @return
     */
    public static Integer pageNum(Integer pageNum) {
        pageNum = pageNum == null ? 1: pageNum;
        return pageNum;
    }

    /**
     * 页面数据量
     * @param pageSize
     * @return
     */
    public static Integer pageSize(Integer pageSize) {
        pageSize = pageSize == null ? 10: pageSize;
        return pageSize;
    }

    /**
     * 排序 默认desc降序
     * @param order
     * @return
     */
    public static boolean order(String order) {
        if (StringUtils.isNotBlank(order)) {
            return "asc".equals(order.toLowerCase());
        }
        return false;
    }

    /**
     * 排序字段
     * @param sort
     * @return
     */
    public static String sort(String sort) {
        sort = StringUtils.isBlank(sort) ? "id" : sort;
        return sort;
    }

    /**
     * 分页条件
     * @param bsVo
     * @param <E>
     * @return
     */
    public static <E> Page<E> page(BaseSearchVo bsVo) {
        return new Page<>(pageNum(bsVo.getPageNum()), pageSize(bsVo.getPageSize()));
    }
}
