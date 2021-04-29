package ${packageName};

import ${daoPackageName}.${name}Dao;
import ${poPackageName}.${name}Po;
import ${basePackageName}.BaseService;
import ${voPackageName}.${path}.${name}SearchVo;
import org.springframework.stereotype.Service;


/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date ${time}
 */
@Service
public interface ${name}Service extends BaseService<${name}Po, ${name}Dao, ${name}SearchVo> {
}
