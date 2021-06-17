package com.ty.wq;

import com.ty.wq.controller.base.BaseController;
import com.ty.wq.dao.BaseDao;
import com.ty.wq.pojo.po.BasePo;
import com.ty.wq.pojo.vo.BaseReqVo;
import com.ty.wq.pojo.vo.BaseRespVo;
import com.ty.wq.pojo.vo.BaseSearchVo;
import com.ty.wq.service.base.BaseService;
import com.ty.wq.service.base.impl.BaseServiceImpl;
import com.ty.wq.utils.CommonUtils;
import com.ty.wq.utils.DateUtils;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * @version 1.0
 * @program: ty-paid
 * @description: 自动生成代码
 * @date 2020/12/2 11:18
 */
public class MainGeneratorManager {

    private static final String lineSeparator = System.getProperty("line.separator");

    private static final String columnName = "COLUMN_NAME";
    private static final String columnDefault = "COLUMN_DEFAULT";
    private static final String isNullable = "IS_NULLABLE";
    private static final String columnType = "COLUMN_TYPE";
    private static final String dataType = "DATA_TYPE";
    private static final String columnKey = "COLUMN_KEY";
    private static final String extra = "EXTRA";
    private static final String columnComment = "COLUMN_COMMENT";

    public static void main(String[] args) throws Exception {
        System.out.println("===================================================================================");
        System.out.println("=======================    代码生成器 v1.0.0    ====================");
        System.out.println("===================================================================================");
        System.out.println();

        // 读取配置文件信息
        final String path = MainGeneratorManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        Properties properties = new Properties();
        final File configFile = new File(path.substring(0, path.lastIndexOf("/")).concat("/config.ini"));
        if (configFile.exists()) {
            properties.load(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8));
        }

        Props props = new Props();
        // 通过反射赋值
        Field[] declaredFields = Props.class.getDeclaredFields();
        for (Field field : declaredFields){
            field.setAccessible(true);
            field.set(props,properties.get(field.getName()));
        }
        //SystemController.out.println(JSONObject.toJSONString(props));

        if ("db".equalsIgnoreCase(props.getGenerateType())){
            // 通过DB生成
            generatedByDb(props);
        }else {
            // 通过POJO生成
            generatedByPojo(props);
        }
    }

    /**
     * 通过数据库生成
     * @param props
     * @throws IOException
     */
    private static void generatedByDb(Props props) throws IOException {
        System.out.println("=======================    generatedByDb    ====================");
        // 1、获取数据库各个表名
        List<Object> tableNames = getTableNames(props);
        //SystemController.out.println(Arrays.toString(tableNames.toArray()));
        // 2、获取各个表的列信息
        for (Object tableName : tableNames){
            String pojoPath = generatedPojo(props, tableName.toString());
            String replace = pojoPath.replaceAll("\\/", "\\.").replace("\\", "\\.");
            props.setPojoPath(replace.substring(0,replace.length() - 5));
            generatedByPojo(props, tableName.toString());
        }
    }

    /**
     * 通过实体类Po生成
     * @param props
     * @throws IOException
     */
    private static void generatedByPojo(Props props) throws IOException {
        System.out.println("=======================    generatedByPojo    ====================");
        generatedDao(props);
        generateService(props);
        generateServiceImpl(props);
        generateController(props);

        voProps(props);

        //generateValidator(props);
        //generateFactory(props);
        generateReqVo(props);
        generateRespVo(props);
        generateSearchVo(props);
    }

    /**
     * 通过实体类Po生成
     * @param props
     * @param tableName
     * @throws IOException
     */
    private static void generatedByPojo(Props props, String tableName) throws IOException {
        System.out.println("=======================    generatedByPojo    ====================");
        generatedDao(props);
        generateService(props);
        generateServiceImpl(props);
        generateController(props);

        //提取原路径
        String voPath = props.getSavingPathVo();

        voProps(props);

        generateReqVo(props, tableName);
        generateRespVo(props, tableName);
        generateSearchVo(props, tableName);
        //generateValidator(props);
        //generateFactory(props);
        //将原路径赋值回去，否则会出错
        props.setSavingPathVo(voPath);
    }

    /**
     * //给vo添加一个pojo的名称文件
     * @param props
     * @return
     */
    private static Props voProps (Props props){
        String pojoName = props.getPojoPath().substring(props.getPojoPath().lastIndexOf(".") + 1);
        props.setSavingPathVo(props.getSavingPathVo() + "/" + CommonUtils.toFirstLowerCase(tableNameToPojoName(pojoNameToTableName(pojoName))));
        return props;
    }

    /**
     * 生成实体类
     * @param props
     * @param tableName
     * @return
     * @throws IOException
     */
    private static String generatedPojo(Props props, String tableName) throws IOException {
        //获取表的列信息
        /*List<Object> columnInfos = getColumnInfo(props, tableName);
        String pojoName = tableNameToPojoName(tableName);
        String packageName = props.getSavingPathPoJo().split("/src/main/java/")[1].replaceAll("\\/","\\.");
        String basePackageName = BasePo.class.getPackage().getName();*/
        return generateWithField(props,props.getSavingPathPoJo(),tableName,"Po", BasePo.class);
    }

    /**
     * 生成Dao
     * @param props
     * @return
     * @throws IOException
     */
    private static String generatedDao(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathDao(),"Dao", BaseDao.class);
    }

    /**
     * 生成Service
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateService(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathService(),"Service", BaseService.class);
    }

    /**
     * 生成Service实现类
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateServiceImpl(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathServiceImpl(),"ServiceImpl", BaseServiceImpl.class);
    }

    /**
     * 生成Controller
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateController(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathController(),"Controller", BaseController.class);
    }

    /**
     * 生成Validator
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateValidator(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathValidator(),"Validator", null);
    }

    /**
     * 生成 RequestVo
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateReqVo(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathVo(),"ReqVo", BaseReqVo.class);
    }

    /**
     * 生成 RequestVo
     * @param props
     * @param tableName
     * @return
     * @throws IOException
     */
    private static String generateReqVo(Props props, String tableName) throws IOException {
        return generateWithVoField(props,props.getSavingPathVo(),tableName,"ReqVo", BaseReqVo.class);
    }

    /**
     * 生成 ResponseVo
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateRespVo(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathVo(),"RespVo", BaseRespVo.class);
    }

    /**
     * 生成 ResponseVo
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateRespVo(Props props, String tableName) throws IOException {
        return generateWithVoField(props,props.getSavingPathVo(),tableName,"RespVo", BaseRespVo.class);
    }


    /**
     * 生成 SearchVo
     * @param props
     * @param tableName
     * @return
     * @throws IOException
     */
    private static String generateSearchVo(Props props, String tableName) throws IOException {
        return generateWithVoField(props,props.getSavingPathVo(),tableName,"SearchVo", BaseSearchVo.class);
    }

    /**
     * 生成 SearchVo
     * @param props
     * @return
     * @throws IOException
     */
    private static String generateSearchVo(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathVo(),"SearchVo", BaseSearchVo.class);
    }

    /**
     * 生成Factory
     * @param props
     * @return
     * @throws IOException
     */
    /*private static String generateFactory(Props props) throws IOException {
        return generateWithNoField(props,props.getSavingPathFactory(),"Factory", BaseFactory.class);
    }*/

    /**
     * 获取表名
     * @param props
     * @return
     */
    private static List<Object> getTableNames(Props props){
        String sql = "SELECT * FROM `TABLES` WHERE TABLE_SCHEMA = ?";
        List<Object> tableNames = get(props, sql, props.getDatabase());
        return tableNames;
    }

    /**
     * 获取数据库表的字段信息
     * @param props
     * @param tableName
     * @return
     */
    private static List<Object> getColumnInfo(Props props, Object tableName) {
        String sql = "SELECT * FROM `COLUMNS` WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        List<Object> objects = get(props, sql, props.getDatabase(), tableName);
        return objects;
    }

    /**
     * 查询数据库
     * @param props
     * @param sql
     * @param paras
     * @return
     */
    private static List<Object> get(Props props,String sql,Object... paras){
        Connection con = null;		//连接
        PreparedStatement ps = null;	//使用预编译语句
        ResultSet rs = null;	//获取的结果集
        try {
            Class.forName(props.getDriver());
            con = DriverManager.getConnection(props.getUrl(), props.getUsername(), props.getPassword());
            ps = con.prepareStatement(sql);
            if (paras != null && paras.length > 0){
                for (int i = 0; i < paras.length; i++){
                    ps.setObject(i + 1,paras[i]);
                }
            }
            rs = ps.executeQuery();
            List<Object> objects = handleResultSet(rs, sql);
            return objects;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps .close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理查询结果
     * @param rs
     * @param sql
     * @return
     */
    private static List<Object> handleResultSet(ResultSet rs, String sql) {
        List<Object> result = new ArrayList<>();
        try {
            while (rs.next()){
                if (sql.indexOf("FROM `TABLES`") != -1){
                    result.add(rs.getString("TABLE_NAME"));
                }else {
                    Map<String,Object> map = new HashMap<>();
                    String colName = rs.getString(columnName);
                    map.put(columnName,colName);
                    Object object = rs.getObject(columnDefault);
                    if (object != null){
                        map.put(columnDefault,object);
                    }
                    String isNull = rs.getString(isNullable);
                    map.put(isNullable,"YES".equalsIgnoreCase(isNull));
                    String colType = rs.getString(columnType);
                    map.put(columnType,colType);
                    String colKey = rs.getString(columnKey);
                    if (StringUtils.isNotBlank(colKey)){
                        map.put(columnKey,colKey);
                    }
                    String ext = rs.getString(extra);
                    if (StringUtils.isNotBlank(ext)){
                        map.put(extra,ext);
                    }
                    String comment = rs.getString(columnComment);
                    if (StringUtils.isNotBlank(comment)){
                        map.put(columnComment,comment);
                    }
                    map.put(dataType,rs.getString(dataType));
                    result.add(map);
                }
            }
        }catch (Exception e){

        }
        return result;
    }

    /**
     * 数据库表名转换成实体类类名
     * @param tableName
     * @return
     */
    private static String tableNameToPojoName(String tableName){
        String[] split = tableName.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(CommonUtils.toFirstUpperCase(s));
        }
        return sb.toString();
    }

    /**
     * 实体类类名转换成数据库表名
     * @param pojoName
     * @return
     */
    private static String pojoNameToTableName(String pojoName){
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(pojoName)){
            // 去掉Po
            if ("Po".equals(pojoName.substring(pojoName.length() -2))) {
                pojoName = pojoName.substring(0, pojoName.length()-2);
            }
            // 将第一个字符处理成小写
            sb.append(pojoName.substring(0,1).toLowerCase());
            // 循环处理其他字符
            for(int i = 1; i < pojoName.length(); i++){
                String tem = pojoName.substring(i,i+1);
                // 在大写字母前添加下划线
                if (tem.equals(tem.toUpperCase()) && !Character.isDigit(tem.charAt(0))){
                    sb.append("_");
                }
                // 其他字符直接转成小写
                sb.append(tem.toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 生成文件含字段
     * @param props
     * @param savePath
     * @param tableName
     * @param suffix
     * @param baseClass
     * @return
     * @throws IOException
     */
    private static String generateWithField(Props props, String savePath, String tableName, String suffix, Class baseClass) throws IOException {
        String pojoName = tableNameToPojoName(tableName);
        File file = copyTemplateFile(pojoName,suffix);
        if (file == null) {
            return null;
        }
        StringBuilder stringBuilder = loadFile(file);
        StringBuilder fieldBuilder = new StringBuilder();
        buildField(props,tableName,fieldBuilder);
        return writeFiled(props,savePath,tableName,file,suffix,stringBuilder,fieldBuilder,baseClass);
    }

    /**
     *
     * @param props
     * @param savePath
     * @param tableName
     * @param suffix
     * @param baseClass
     * @return
     * @throws IOException
     */
    private static String generateWithVoField(Props props, String savePath, String tableName, String suffix, Class baseClass) throws IOException {
        String pojoName = tableNameToPojoName(tableName);
        File file = copyTemplateFile(pojoName,suffix);
        if (file == null) {
            return null;
        }
        StringBuilder stringBuilder = loadFile(file);
        StringBuilder fieldBuilder = new StringBuilder();
        buildVoField(props,tableName,fieldBuilder);
        return writeFiled(props,savePath,tableName,file,suffix,stringBuilder,fieldBuilder,baseClass);
    }

    /**
     * 生成文件不含字段
     * @param props
     * @param savePath
     * @param suffix
     * @param baseClass
     * @return
     * @throws IOException
     */
    private static String  generateWithNoField(Props props, String savePath, String suffix, Class baseClass) throws IOException {
        //String pojoName = tableNameToPojoName(tableName);
        String pojoName = props.getPojoPath().substring(props.getPojoPath().lastIndexOf(".") + 1);
        File file = copyTemplateFile(pojoName,suffix);
        if (file == null) {
            return null;
        }
        StringBuilder stringBuilder = loadFile(file);
        StringBuilder fieldBuilder = new StringBuilder();
        return writeFiled(props,savePath,pojoNameToTableName(pojoName),file,suffix,stringBuilder,fieldBuilder,baseClass);
    }

    /**
     * 复制临时文件
     * @param pojoName
     * @param suffix
     * @return
     * @throws IOException
     */
    private static File copyTemplateFile(String pojoName, String suffix) throws IOException {
        InputStream inputStream = ClassLoader.class.getResourceAsStream("/generator-template/".concat(suffix).concat(".java"));
        Path path = Files.createTempFile(pojoName.concat(suffix), ".java");
        System.out.println("tempFilePath:" + path.toString());
        File file = path.toFile();
        System.out.println("tempFile:" + file.getAbsoluteFile().getName());
        file.deleteOnExit();
        if (Objects.isNull(inputStream)){
            System.out.println("生成".concat(suffix).concat(" 失败 ： 模板文件不存在"));
            return null;
        }
        Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);
        return file;
    }

    /**
     * 加载模板文件
     * @param file
     * @return
     */
    private static StringBuilder loadFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        String lineStr;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            while ((lineStr = reader.readLine()) != null) {
                stringBuilder.append(lineStr).append(lineSeparator);
            }
        } catch (Exception e) {
            System.err.println(" 载入文件失败: ".concat(e.getMessage()));
        }
        return stringBuilder;
    }

    /**
     * 生成字段
     * @param props
     * @param tableName
     * @param fieldBuilder
     */
    private static void buildField(Props props, String tableName, StringBuilder fieldBuilder) {
        List<String> baseFiledNames = new ArrayList<>();
        Field[] declaredFields = BasePo.class.getDeclaredFields();
        Arrays.asList(declaredFields).forEach(field -> {
            String name = field.getName();
            baseFiledNames.add(name);
        });
        List<Object> columnInfos = getColumnInfo(props, tableName);
        AtomicInteger i = new AtomicInteger(0);
        columnInfos.forEach(columnInfo -> {
            if (columnInfo instanceof Map){
                HashMap<String, Object> map = (HashMap<String, Object>) columnInfo;
                if (baseFiledNames.contains(getFiledName(map.get(columnName).toString()))){
                    return;
                }
                String colName = map.get(columnName).toString();
                boolean isNull = Boolean.valueOf(map.get(isNullable).toString());
                String colKey = map.get(columnKey) == null ? null : map.get(columnKey).toString();
                System.out.println("colKey: " + colKey);
                String colType = map.get(columnType).toString();
                System.out.println("colType: " + colType);
                String fieldType = map.get(dataType).toString();
                String colDefault = map.get(columnDefault) == null ? null : map.get(columnDefault).toString();
                String colComment = map.get(columnComment) == null ? null : map.get(columnComment).toString();
                // 生成@Column注解
                StringBuilder columnStringBuilder = new StringBuilder();
                if (i.get() == 0){
                    i.incrementAndGet();
                }else {
                    columnStringBuilder.append("\t");
                }

                columnStringBuilder.append("@Column(");
                columnStringBuilder.append("name = \"").append(colName).append("\"");
                String length = "";
                if (colType.contains("(")) {
                    length = colType.substring(colType.indexOf("(") + 1, colType.indexOf(")"));
                    colType = colType.substring(0, colType.indexOf("("));
                }
                columnStringBuilder.append((", type = MySqlTypeConstant.".concat(colType.toUpperCase())));
                if (!"".equals(length)) {
                    columnStringBuilder.append(", length = ".concat(length));
                }
                if (!StringUtils.isEmpty(colDefault)){
                    columnStringBuilder.append(", defaultValue = \"").append(colDefault).append("\"");
                }
                if (!StringUtils.isEmpty(colComment)){
                    columnStringBuilder.append(", comment = \"").append(colComment).append("\"");
                }
                columnStringBuilder.append(")");
                columnStringBuilder.append(lineSeparator);
                if (!isNull) {
                    fieldBuilder.append("\t@IsNotNull").append(lineSeparator);
                }
                if ("UNI".equalsIgnoreCase(colKey)){
                    fieldBuilder.append("\t@Unique").append(lineSeparator);
                }
                fieldBuilder.append(columnStringBuilder.toString());
                // 生成字段
                fieldBuilder.append("\tprivate ");
                fieldBuilder.append(converterType(fieldType));
                fieldBuilder.append(getFiledName(colName));
                fieldBuilder.append(";");
                fieldBuilder.append(lineSeparator);
                fieldBuilder.append(lineSeparator);
            }
        });
    }

    private static void buildVoField(Props props, String tableName, StringBuilder fieldBuilder) {
        List<String> baseFiledNames = new ArrayList<>();
        Field[] declaredFields = BasePo.class.getDeclaredFields();
        Arrays.asList(declaredFields).forEach(field -> {
            String name = field.getName();
            baseFiledNames.add(name);
        });
        List<Object> columnInfos = getColumnInfo(props, tableName);
        AtomicInteger i = new AtomicInteger(0);
        columnInfos.forEach(columnInfo -> {
            if (columnInfo instanceof Map){
                HashMap<String, Object> map = (HashMap<String, Object>) columnInfo;
                if (baseFiledNames.contains(getFiledName(map.get(columnName).toString()))){
                    return;
                }
                String colName = map.get(columnName).toString();
                String fieldType = map.get(dataType).toString();
                // 生成字段
                fieldBuilder.append("\tprivate ");
                fieldBuilder.append(converterType(fieldType));
                fieldBuilder.append(getFiledName(colName));
                fieldBuilder.append(";");
                fieldBuilder.append(lineSeparator);
                fieldBuilder.append(lineSeparator);
            }
        });
    }

    /**
     * 数据库类型转换成java类型
     * @param fieldType
     * @return
     */
    private static String converterType(String fieldType) {
        String ret = "";
        switch (fieldType){
            case "char":
            case "varchar":
            case "tinytext":
            case "text":
            case "mediumtext":
            case "longtext":{
                ret = "String ";
                break;
            }
            case "bigint": {
                ret = "Long ";
                break;
            }
            case "tinyint":
            case "smallint":
            case "mediumint":
            case "int":
            case "integer":{
                ret = "Integer ";
                break;
            }
            case "timestamp":
            case "datetime": {
                ret = "Timestamp ";
                break;
            }
            case "tinyblob":
            case "blob":
            case "mediumblob":
            case "longblob":{
                ret = "byte[] ";
                break;
            }
            case "bit": {
                ret = "Boolean ";
                break;
            }
            case "float": {
                ret = "Float ";
                break;
            }
            case "double": {
                ret = "Double ";
                break;
            }
            case "decimal": {
                ret = "BigDecimal ";
                break;
            }
        }
        return ret;
    }

    /**
     * 数据库字段名转换成java类字段名
     * @param colName
     * @return
     */
    private static String getFiledName(String colName) {
        String[] split = colName.split("_");
        if (split.length == 1) {
            return colName;
        }
        String filedName = split[0];
        for (int i = 1; i < split.length; i++){
            filedName += CommonUtils.toFirstUpperCase(split[i]);
        }
        return filedName;
    }

    /**
     * 写文件
     * @param props
     * @param savingPath
     * @param tableName
     * @param file
     * @param suffix
     * @param stringBuilder
     * @param fieldBuilder
     * @param baseClass
     * @return
     * @throws IOException
     */
    private static String writeFiled(Props props, String savingPath, String tableName,File file,String suffix, StringBuilder stringBuilder, StringBuilder fieldBuilder,Class baseClass) throws IOException {
        String pojoName = tableNameToPojoName(tableName);
        System.out.println(tableName);
        String packageName = savingPath.split("/src/main/java/")[1].replaceAll("\\/","\\.");
        String basePackageName = "";
        if (baseClass != null) {
            basePackageName = baseClass.getPackage().getName();
        }
        String poPackageName = props.getSavingPathPoJo().split("/src/main/java/")[1].replaceAll("\\/","\\.");
        String daoPackageName = props.getSavingPathDao().split("/src/main/java/")[1].replaceAll("\\/","\\.");
        String servicePackageName = props.getSavingPathService().split("/src/main/java/")[1].replaceAll("\\/","\\.");
        String voPackageName = props.getSavingPathVo().split("/src/main/java/")[1].replaceAll("\\/","\\.");
        String factoryPackageName = props.getSavingPathFactory().split("/src/main/java/")[1].replaceAll("\\/","\\.");

        String filePath = savingPath.concat("\\").concat(pojoName).concat(suffix).concat(".java");
        File savePathFile = new File(filePath);
        if (savePathFile.exists()) {
            System.out.println("文件已存在: " + savePathFile);
            return filePath;
        }
        String content = stringBuilder.toString()
                .replace("${packageName}",packageName)
                .replace("${basePackageName}",basePackageName)
                .replace("${poPackageName}",poPackageName)
                .replace("${daoPackageName}",daoPackageName)
                .replace("${servicePackageName}",servicePackageName)
                .replace("${factoryPackageName}",factoryPackageName)
                .replace("${voPackageName}",voPackageName)
                .replace("${tableName}",tableName)
                .replace("${name}",pojoName)
                .replace("${path}", CommonUtils.toFirstLowerCase(pojoName))
                .replace("${time}", DateUtils.parseLongToDate(System.currentTimeMillis(),null))
                .replace("${fieldList}",fieldBuilder.toString());
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
            randomAccessFile.setLength(0);
            randomAccessFile.seek(0);
            randomAccessFile.write(content.getBytes(StandardCharsets.UTF_8));
        }
        if (!StringUtils.isEmpty(savingPath)){
            File file1 = new File(savingPath);
            if (!file1.exists() && !file1.mkdirs()){
                System.out.println(" 生成 ".concat(suffix).concat(" 失败 : 目录创建失败"));
                return null;
            }

            Files.copy(file.toPath(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(" 生成 ".concat(suffix).concat(" 成功 : ").concat(filePath));
            System.out.println(filePath);
            return filePath;
        }else {
            File savingFile = new File(pojoName.concat(suffix).concat(".java"));
            Files.copy(file.toPath(), Paths.get(savingFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
            System.out.println(
                    " 生成 ".concat(suffix).concat(" 成功 : ").concat(savingFile.getAbsolutePath()));
            System.out.println(savingFile.getAbsolutePath());
            return savingFile.getAbsolutePath();
        }
    }

    @Data
    static class Props{

        private String driver;

        private String url;

        private String username;

        private String password;

        private String database;

        private String generateType;

        private String pojoPath;

        private String savingPathPoJo;

        private String savingPathDao;

        private String savingPathService;

        private String savingPathServiceImpl;

        private String savingPathController;

        private String savingPathValidator;

        private String savingPathVo;

        private String savingPathFactory;
    }
}
