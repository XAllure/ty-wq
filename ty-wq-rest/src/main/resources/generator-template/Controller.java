package ${packageName};


import ${basePackageName}.BaseController;
import ${servicePackageName}.${name}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ${poPackageName}.${name};
import ${voPackageName}.${path}.${name}ReqVo;
import ${voPackageName}.${path}.${name}RespVo;
import ${voPackageName}.${path}.${name}SearchVo;
import ${daoPackageName}.${name}Dao;
import ${factoryPackageName}.${name}Factory;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date ${time}
 */
@RestController
@RequestMapping("/${path}")
public class ${name}Controller extends BaseController<${name}, ${name}ReqVo, ${name}RespVo, ${name}SearchVo, ${name}Dao, ${name}Service> {
    /*@Autowired
    private ${name}Service service;*/
}
