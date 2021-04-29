package com.ty.wq.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.anno.SearchCondition;
import com.ty.wq.dao.BaseDao;
import com.ty.wq.pojo.po.BasePo;
import com.ty.wq.pojo.vo.BaseSearchVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.utils.PageUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author xuxilan
 * @version 1.0
 * @program: meng-baby
 * @description:
 * @date 2020/11/25
 */
public class BaseServiceImpl<E extends BasePo,D extends BaseDao<E>, SV extends BaseSearchVo> implements BaseService<E,D, SV> {

    @Autowired
    protected D baseDao;

    @Override
    @Transactional(rollbackFor= Exception.class)
    public int insert(E entity) {
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        entity.setDeleted(0);
        entity.setVersion(0);
        return this.baseDao.insert(entity);
    }

    @Override
    public int insertBatchSomeColumn(Collection<E> entityList) {
        return this.baseDao.insertBatchSomeColumn(entityList);
    }

    @Override
    @Transactional(rollbackFor= Exception.class)
    public int deleteById(Serializable id) {
        return this.baseDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor= Exception.class)
    public int deleteBatchIds(Collection<? extends Serializable> ids) {
        return this.baseDao.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor= Exception.class)
    public int delete(E entity) {
        return this.deleteById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor= Exception.class)
    public int updateById(E entity) {
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return this.baseDao.updateById(entity);
    }

    @Override
    public E findOne(Wrapper<E> queryWrapper) {
        return this.baseDao.selectOne(queryWrapper);
    }

    @Override
    public E findById(Serializable id) {
        return this.baseDao.selectById(id);
    }

    @Override
    public List<E> findBatchIds(Collection<? extends Serializable> idList) {
        return this.baseDao.selectBatchIds(idList);
    }

    @Override
    public List<E> findList(Wrapper<E> queryWrapper) {
        return this.baseDao.selectList(queryWrapper);
    }

    @Override
    public List<E> findAll() {
        return this.baseDao.selectList(null);
    }

    @Override
    public int findCount(Wrapper<E> queryWrapper) {
        return this.baseDao.selectCount(queryWrapper);
    }

    @Override
    public <P extends IPage<E>> P findPage(P page, Wrapper<E> queryWrapper) {
        return this.baseDao.selectPage(page, queryWrapper);
    }

    @Override
    public <P extends IPage<E>> P findPage(P page, SV sv) {
        return this.baseDao.selectPage(page,buildWrapper(sv));
    }

    @Override
    public Page<E> findPage(SV sv) {
        return this.findPage(PageUtils.page(sv), sv);
    }

    @SneakyThrows
    protected QueryWrapper<E> buildWrapper(SV sv) {
        QueryWrapper<E> qw = new QueryWrapper<>();
        Field[] fields = sv.getClass().getDeclaredFields();
        for (Field field : fields) {
            SearchCondition sc = field.getDeclaredAnnotation(SearchCondition.class);
            if (sc == null) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(sv);
            if (Objects.isNull(value) || Objects.equals("", value)) {
                continue;
            }
            switch (sc.compare()) {
                case EQUAL: {
                    qw.eq(sc.filed(), value);
                    break;
                }
                case LIKE: {
                    qw.like(sc.filed(), value);
                    break;
                }
                case LE: {
                    qw.le(sc.filed(), value);
                    break;
                }
                case GE: {
                    qw.ge(sc.filed(), value);
                    break;
                }
                default: {
                    break;
                }
            }
        }
        qw.orderBy(true, PageUtils.order(sv.getOrder()), PageUtils.sort(sv.getSort()));
        return qw;
    }

}

