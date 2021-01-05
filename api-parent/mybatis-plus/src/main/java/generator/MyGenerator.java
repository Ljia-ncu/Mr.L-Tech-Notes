package generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MyGenerator
 * @Description
 * @Author Mr.L
 * @Date 2020/11/28 20:38
 * @Version 1.0
 */
public class MyGenerator {
    public static void main(String[] args) {
        new MyGenerator().generate();
    }

    /**
     * module 模块名称
     * group 最后生成group.module目录
     * tables 表名
     */
    private final String module = "mybatis";
    private final String group = "com.mrl";
    private final String[] tables = {"tx_test"};

    /**
     * abPath 输出的绝对路径
     */
    private final String author = "Mr.L";
    private final String abPath = "E:\\IDE\\api-parent\\mybatis-plus\\src\\main\\java";

    private final String url = "jdbc:mysql://localhost:3306/seata?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
    private final String username = "root";
    private final String password = "123456";

    private void generate() {
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(abPath);
        globalConfig.setAuthor(author);
        //生成结束后是否打开所在文件夹
        globalConfig.setOpen(false);
        //是否进行覆盖
        globalConfig.setFileOverride(false);
        //去除Service的I前缀
        globalConfig.setServiceName("%sService");
        globalConfig.setIdType(IdType.AUTO);
        globalConfig.setDateType(DateType.ONLY_DATE);
        globalConfig.setSwagger2(true);
        autoGenerator.setGlobalConfig(globalConfig);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dataSourceConfig);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setModuleName(module);
        packageConfig.setParent(group);
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setXml("mapper.mapping");
        packageConfig.setService("service");
        packageConfig.setController("controller");
        autoGenerator.setPackageInfo(packageConfig);

        StrategyConfig strategyConfig = new StrategyConfig();
        //设置要映射的表名
        strategyConfig.setInclude(tables);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        //如果表名含有下划线，则RequestMapping格式 module/user_info 变为 module/user-info;
        strategyConfig.setControllerMappingHyphenStyle(true);

        strategyConfig.setLogicDeleteFieldName("deleted");
        strategyConfig.setVersionFieldName("version");
        TableFill createDate = new TableFill("create_date", FieldFill.INSERT);
        TableFill updateDate = new TableFill("update_date", FieldFill.INSERT_UPDATE);
        List<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createDate);
        tableFills.add(updateDate);
        strategyConfig.setTableFillList(tableFills);
        autoGenerator.setStrategy(strategyConfig);

        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }
}
