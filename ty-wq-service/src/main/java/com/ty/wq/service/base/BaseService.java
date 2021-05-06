package com.ty.wq.service.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.dao.BaseDao;
import com.ty.wq.pojo.po.BasePo;
import com.ty.wq.pojo.vo.BaseSearchVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author Administrator
 */
public interface BaseService<E extends BasePo,D extends BaseDao<E>, SV extends BaseSearchVo> {

    /**
     * 新增一条数据
     * @param entity
     * @return
     */
    int insert(E entity);

    /**
     * 批量插入数据
     * @param entityList
     * @return
     */
    int inserts(Collection<E> entityList);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Serializable id);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    int deleteBatchIds(Collection<? extends Serializable> ids);

    /**
     * 根据一个实体删除
     * @param entity
     * @return
     */
    int delete(E entity);

    /**
     * 更新一条数据
     * @param entity
     * @return
     */
    int updateById(E entity);

    /**
     * 获取一条记录
     * @param queryWrapper
     * @return
     */
    E findOne(Wrapper<E> queryWrapper);

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    E findById(Serializable id);

    /**
     * 根据id集合查询多条数据
     * @param idList
     * @return
     */
    List<E> findBatchIds(Collection<? extends Serializable> idList);

    /**
     * 根据条件查询多条记录
     * @param queryWrapper
     * @return
     */
    List<E> findList(Wrapper<E> queryWrapper);

    /**
     * 查询所有记录
     * @return
     */
    List<E> findAll();

    /**
     * 根据条件查询总条数
     * @param queryWrapper
     * @return
     */
    int findCount(Wrapper<E> queryWrapper);

    /**
     * 查询分页数据
     * 自定义页号与页面数据量
     * 自定义查询条件
     * @param page
     * @param queryWrapper
     * @param <P>
     * @return
     */
    <P extends IPage<E>> P findPage(P page, Wrapper<E> queryWrapper);

    /**
     * 查询分页数据
     * 自定义页号与页面数据量
     * 自动封装好查询条件
     * @param page
     * @param sv
     * @param <P>
     * @return
     */
    <P extends IPage<E>> P findPage(P page, SV sv);

    /**
     * 查询分页数据
     * 自动封装好页号与页面数据量
     * 自动封装好查询条件
     * @param sv
     * @return
     */
    Page<E> findPage(SV sv);

}
