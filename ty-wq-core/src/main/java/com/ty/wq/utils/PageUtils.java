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

    /*public static Pageable initPage(BaseSearchVo baseSearchVo){
        Pageable pageable = null;

        // 当前页
        int pageNumber = baseSearchVo.getPageIndex() == null ? 1 : baseSearchVo.getPageIndex();

        // 页面数
        int pageSize = baseSearchVo.getPageSize() == null ? 10 : baseSearchVo.getPageSize();

        // 排序字段
        String sort = StringUtils.isEmpty(baseSearchVo.getSort()) ? "id" : baseSearchVo.getSort();

        // 排序 默认desc降序
        String order = baseSearchVo.getOrder();

        if (!StringUtils.isEmpty(sort)) {
            Sort.Direction d;
            if (StringUtils.isEmpty(order)) {
                d = Sort.Direction.DESC;
            } else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = Sort.by(new Sort.Order(d,sort));
            pageable = PageRequest.of(pageNumber - 1, pageSize, s);
        } else {
            pageable = PageRequest.of(pageNumber - 1, pageSize);
        }
        return pageable;
    }*/

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
            return "asc".equals(order.toUpperCase());
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
