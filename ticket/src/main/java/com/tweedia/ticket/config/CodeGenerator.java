package com.tweedia.ticket.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

public class CodeGenerator {


    public static void main(String[] args) {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("Tweedia") // 作者
                .setOutputDir("E:\\download\\IDEA\\project\\ticket-master\\ticket\\src\\main\\java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setEnableCache(false) //是否开启二级缓存
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
                // IEmployeeService
                .setBaseResultMap(true)
//                .setKeyGenerator(new OracleKeyGenerator())
                .setOpen(false)
                .setBaseColumnList(true);

        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/ticket?serverTimezone=CST&useSSL=false")
                .setUsername("root")
//                .setTypeConvert(new OracleTypeConvert()) //oracle
                .setPassword("adsl39547");

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
                //.setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setColumnNaming(NamingStrategy.underline_to_camel)
//                .setTablePrefix("role_")
                .setInclude("announcement")  // 生成的表
//                .setExclude("role_user")
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
        ;

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.tweedia.ticket")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("domain")
                .setXml("mapper");

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();

        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);

        //6. 执行
        ag.execute();

//    @Autowired
//    private GoodsMapper goodsMapper;
//
//    public Object[][] getSearchUser(int type, String input) {
//        List<Goods> goodsList = null;
//        if (type == 1) {
//            goodsList = goodsMapper.selectList(new QueryWrapper<Goods>().eq("gid", input));
//        } else if (type == 0) {
//            goodsList = goodsMapper.selectList(null);
//        }
//        if (goodsList != null && goodsList.size() >= 0) {
//            Object[][] goodsMSG = new Object[goodsList.size()][7];
//            for (int i = 0; i < goodsList.size(); i++) {
//                goodsMSG[i][0] = i + 1;
//                goodsMSG[i][1] = goodsList.get(i).getGid();
//                goodsMSG[i][2] = goodsList.get(i).getGname();
//                goodsMSG[i][3] = goodsList.get(i).getGclass();
//                goodsMSG[i][4] = goodsList.get(i).getPrice();
//                goodsMSG[i][5] = goodsList.get(i).getStockqt();
//                goodsMSG[i][6] = goodsList.get(i).getGnotarr();
//            }
//            return goodsMSG;
//        }
//        return new Object[0][0];
//    }
    }
}