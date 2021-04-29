package ${packageName};

import ${daoPackageName}.${name}Dao;
import ${voPackageName}.${path}.${name}SearchVo;
import ${poPackageName}.${name}Po;
import ${servicePackageName}.${name}Service;
import ${basePackageName}.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date ${time}
 */
@Service
public class ${name}ServiceImpl extends BaseServiceImpl<${name}Po, ${name}Dao, ${name}SearchVo> implements ${name}Service {
}
