package ${packageName};

import ${basePackageName}.BaseRespVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-wq
 * @description:
 * @date ${time}
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ${name}RespVo extends BaseRespVo {

    private static final long serialVersionUID = -1L;

${fieldList}
}
