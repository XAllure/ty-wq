package ${packageName};

import ${poPackageName}.${name};
import ${voPackageName}.${name}ReqVo;
import ${voPackageName}.${name}RespVo;
import ${basePackageName}.BaseFactory;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date ${time}
 */
@Component
public class ${name}Factory extends BaseFactory<${name}, ${name}ReqVo, ${name}RespVo> {
    @Override
    protected void doOtherForRespVo(${name}RespVo vo, ${name} po) {

    }

    @Override
    protected void doOtherForPo(${name}ReqVo reqVo, ${name} po) {

    }
}
