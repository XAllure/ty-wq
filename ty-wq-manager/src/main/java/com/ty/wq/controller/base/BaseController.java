package com.ty.wq.controller.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.dao.BaseDao;
import com.ty.wq.pojo.po.BasePo;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.BaseRespVo;
import com.ty.wq.pojo.vo.BaseSearchVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.utils.OrikaUtils;
import com.ty.wq.utils.ReqVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Administrator
 */
public class BaseController<Po extends BasePo, ReqVo extends BaseReqVo, RespVo extends BaseRespVo,
        SV extends BaseSearchVo, Dao extends BaseDao<Po>, Service extends BaseService<Po, Dao, SV>> {

    @Autowired
    protected Service service;

    private Class<Po> poClass;

    private Class<RespVo> respClass;

    public BaseController(){
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 2){
                poClass = (Class<Po>)actualTypeArguments[0];
                respClass = (Class<RespVo>)actualTypeArguments[2];
            }
        }
    }

    /**
     * 条件列表分页
     * @param sv
     * @return
     */
    @GetMapping("/list/condition")
    public Result listByCondition(SV sv){
        Page<RespVo> vPage = service.findPage(sv, respClass);
        return Result.success(vPage);
    }

    /**
     * 添加
     * @param reqVo
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody ReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Add.class);
        Po po = OrikaUtils.convert(reqVo, poClass);
        service.insert(po);
        return Result.success();
    }

    /**
     * 修改
     * @param reqVo
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody ReqVo reqVo) {
        ReqVoUtils.validated(reqVo, BaseReqVo.Update.class);
        Po po = service.findById(reqVo.getId());
        OrikaUtils.copy(reqVo, po);
        service.updateById(po);
        return Result.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public Result delete(@Valid @Min(value = 1,message = "id不能小于1") @PathVariable Long id){
        service.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody @Validated @NotEmpty(message = "id不能为空") List<Long> ids){
        service.deleteBatchIds(ids);
        return Result.success();
    }

}
