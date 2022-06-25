package com.newcore.laboratory.code.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * MyBatis代码生成主运行类
 * @author zhouchaowei
 * @date 2019年09月29日 Sunday 14:16
 */
public class Generator {

    /** 配置文件名称 */
    private static final String PROPERTIES_FILE_NAME = "application.properties";

    /**
     * 主执行方法
     * @param args 执行参数
     * @throws InterruptedException
     * @throws SQLException
     * @throws InvalidConfigurationException
     * @throws XMLParserException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, SQLException, InvalidConfigurationException, XMLParserException, IOException {
        execute();
    }

    /**
     * 1.加载配置文件并读取代码生成器开关
     * @return
     * @throws IOException
     */
    private static boolean switchStatus() throws IOException {
        /** 1.1. 将配置文件以流的形式加载 */
        InputStream inputStream = com.newcore.laboratory.code.generator.Generator.class.getResourceAsStream("/" + com.newcore.laboratory.code.generator.Generator.PROPERTIES_FILE_NAME);
        /** 1.2. 声明配置对象 */
        Properties properties = new Properties();
        /** 1.3. 将输入流以配置项的方式加载 */
        properties.load(inputStream);
        /** 1.4. 读取代码生成器开关 */
        String macSwitch = properties.getProperty("switch.mac");
        /** 1.5. 关闭输入流 */
        inputStream.close();
        return "ON".equalsIgnoreCase(macSwitch);
    }

    /**
     * 2.执行代码生成器
     * @throws IOException
     * @throws XMLParserException
     * @throws InvalidConfigurationException
     * @throws SQLException
     * @throws InterruptedException
     */
    private static void execute() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        /** 2.1. 定义输入流 */
        InputStream inputStream;
        if(switchStatus()){
            inputStream = com.newcore.laboratory.code.generator.Generator.class.getResourceAsStream("/generatorConfig-mac.xml");
        }else{
            inputStream = com.newcore.laboratory.code.generator.Generator.class.getResourceAsStream("/generatorConfig-windows.xml");
        }
        /** 2.2. 声明List集合,保存执行过程中的警告信息 */
        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(inputStream);
        inputStream.close();

        /** 2.3. 当生成的代码重复时，覆盖原代码 */
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        /** 2.4. 执行生成代码 */
        myBatisGenerator.generate(null);

        /** 2.5. 向控制台输出警告信息 */
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
