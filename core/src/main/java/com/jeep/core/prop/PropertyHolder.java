package com.jeep.core.prop;

import com.jeep.core.log.WopLogger;
import java.util.Locale;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

/**
 * 功能描述：
 *
 * @日期：2013-12-31 10:32:28
 * @作者：wujng
 * @版本：1.0
 */
public class PropertyHolder {

    private static final WopLogger logger = new WopLogger(PropertyHolder.class);

    private static Properties props = new Properties();

    static {
        load();
    }

    public static Properties getProperties() {
        return props;
    }

    public static void load() {
        String systemConfig = "/com/jeep/config.properties";
        String localConfig = "/config.local.properties";
        String dbConfig = "/com/jeep/db.properties";
        String localDBConfig = "/db.local.properties";
        ClassPathResource cr = null;
        try {
            cr = new ClassPathResource(systemConfig);
            props.load(cr.getInputStream());
            logger.info("装入主配置文件:" + systemConfig);
            logger.info("Main profile is loaded: " + systemConfig, Locale.ENGLISH);
        } catch (Exception e) {
            logger.info("装入主配置文件" + systemConfig + "失败!", e);
            logger.info("Failed to load main profile " + systemConfig + "!", e, Locale.ENGLISH);
        }
        try {
            cr = new ClassPathResource(localConfig);
            props.load(cr.getInputStream());
            logger.info("装入自定义主配置文件：" + localConfig);
            logger.info("Custom main profile is loaded: " + localConfig, Locale.ENGLISH);
        } catch (Exception e) {
            logger.info("装入自定义主配置文件" + localConfig + "失败！", e);
            logger.info("Failed to load custom main profile " + localConfig + "！", e, Locale.ENGLISH);
        }
        try {
            cr = new ClassPathResource(dbConfig);
            props.load(cr.getInputStream());
            logger.info("装入数据库配置文件：" + dbConfig);
            logger.info("Database profile is loaded：" + dbConfig);
        } catch (Exception e) {
            logger.info("装入数据库配置文件" + dbConfig + "失败！", e);
            logger.info("Failed to load database profile " + dbConfig + "！", e, Locale.ENGLISH);
        }
        try {
            cr = new ClassPathResource(localDBConfig);
            props.load(cr.getInputStream());
            logger.info("装入自定义数据库配置文件：" + localDBConfig);
            logger.info("Custom database profile is loaded：" + localDBConfig, Locale.ENGLISH);
        } catch (Exception e) {
            logger.info("装入自定义数据库配置文件" + localDBConfig + "失败！", e);
            logger.info("Failed to load custom database profile " + localDBConfig + "！", e, Locale.ENGLISH);
        }

        String extendPropertyFiles = props.getProperty("extend.property.files");
        if (extendPropertyFiles != null && !"".equals(extendPropertyFiles.trim())) {
            String[] files = extendPropertyFiles.trim().split(",");
            for (String file : files) {
                try {
                    cr = new ClassPathResource(file);
                    props.load(cr.getInputStream());
                    logger.info("装入扩展配置文件：" + file);
                    logger.info("Extend profile is loaded：" + file, Locale.ENGLISH);
                } catch (Exception e) {
                    logger.info("装入扩展配置文件" + file + "失败！", e);
                    logger.info("Failed to load extend profile" + file + "失败！", e, Locale.ENGLISH);
                }
            }
        }
        logger.info("系统配置属性装载完毕");
        logger.info("System configuration properties finished loading", Locale.ENGLISH);
        logger.info("******************属性列表***************************");
        logger.info("******************Properties List********************", Locale.ENGLISH);
        for (String propertyName : props.stringPropertyNames()) {
            logger.info("  " + propertyName + " = " + props.getProperty(propertyName));
        }
        logger.info("***********************************************************");

        //指定日志输出语言
        WopLogger.setConfigLanguage(getLogLanguage());
    }

    /**
     * 日志语言
     *
     * @return
     */
    public static Locale getLogLanguage() {
        String language = getProperty("log.locale.language");
        return Locale.forLanguageTag(language);
    }

    public static String getProperty(String name) {
        String value = props.getProperty(name);
        return value;
    }

    public static void setProperty(String name, String value) {
        props.setProperty(name, value);
    }

    public static boolean getBooleanProperty(String name) {
        String value = props.getProperty(name);
        return "true".equals(value);
    }

    public static int getIntProperty(String name) {
        String value = props.getProperty(name);

        return Integer.parseInt(value);
    }
}
