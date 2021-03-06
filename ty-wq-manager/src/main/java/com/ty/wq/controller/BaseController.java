package com.ty.wq.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ty.wq.anno.RePermission;
import com.ty.wq.dao.BaseDao;
import com.ty.wq.enums.CodeEnum;
import com.ty.wq.pojo.po.BasePo;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.BaseRespVo;
import com.ty.wq.pojo.vo.BaseSearchVo;
import com.ty.wq.pojo.vo.Result;
import com.ty.wq.pojo.vo.manager.StatusReqVo;
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

    protected boolean methodAll = true;
    protected boolean methodList = true;
    protected boolean methodAdd = true;
    protected boolean methodUpdate = true;
    protected boolean methodStatus = true;
    protected boolean methodDelete = true;
    protected boolean methodDeleteBatch = true;

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
     * ????????????
     * @return
     */
    @GetMapping("/all")
    @RePermission(value = "list")
    public Result all(){
        if (!methodAll) {
            return Result.error(CodeEnum.METHOD_NOT_SUPPORT);
        }
        List<RespVo> respVos = OrikaUtils.converts(service.findAll(), respClass);
        return Result.success(respVos);
    }

    /**
     * ??????????????????
     * @param sv
     * @return
     */
    @GetMapping("/list/condition")
    @RePermission(value = "list")
    public Result listByCondition(SV sv){
        if (!methodList) {
            return Result.error(CodeEnum.METHOD_NOT_SUPPORT);
        }
        Page<RespVo> vPage = service.findPage(sv, respClass);
        return Result.success(vPage);
    }

    /**
     * ??????
     * @param reqVo
     * @return
     */
    @PostMapping("/add")
    @RePermission(value = "add")
    public Result add(@RequestBody ReqVo reqVo) {
        if (!methodAdd) {
            return Result.error(CodeEnum.METHOD_NOT_SUPPORT);
        }
        ReqVoUtils.validated(reqVo, BaseReqVo.Add.class);
        Po po = OrikaUtils.convert(reqVo, poClass);
        service.insert(po);
        return Result.success();
    }

    /**
     * ??????
     * @param reqVo
     * @return
     */
    @PostMapping("/update")
    @RePermission(value = "update")
    public Result update(@RequestBody ReqVo reqVo) {
        if (!methodUpdate) {
            return Result.error(CodeEnum.METHOD_NOT_SUPPORT);
        }
        ReqVoUtils.validated(reqVo, BaseReqVo.Update.class);
        Po po = service.findById(reqVo.getId());
        OrikaUtils.copy(reqVo, po);
        service.updateById(po);
        return Result.success();
    }

    /**
     * ??????????????????
     * @param vo
     * @return
     */
    @PostMapping("/status/update")
    @RePermission(value = "status:update")
    public Result updateStatus(@RequestBody StatusReqVo vo){
        if (!methodStatus) {
            return Result.error(CodeEnum.METHOD_NOT_SUPPORT);
        }
        ReqVoUtils.validated(vo, BaseReqVo.Status.class);
        Po po = service.findById(vo.getId());
        po.setStatus(vo.getStatus());
        service.updateById(po);
        return Result.success();
    }

    /**
     * ??????
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    @RePermission(value = "delete")
    public Result delete(@Valid @Min(value = 1, message = "id????????????1") @PathVariable Long id){
        if (!methodDelete) {
            return Result.error(CodeEnum.METHOD_NOT_SUPPORT);
        }
        service.deleteById(id);
        return Result.success();
    }

    /**
     * ????????????
     * @param ids
     * @return
     */
    @PostMapping("/deleteBatch")
    @RePermission(value = "deleteBatch")
    public Result deleteBatch(@RequestBody @Validated @NotEmpty(message = "???????????????????????????") List<Long> ids){
        if (!methodDeleteBatch) {
            return Result.error(CodeEnum.METHOD_NOT_SUPPORT);
        }
        service.deleteBatchIds(ids);
        return Result.success();
    }

}
