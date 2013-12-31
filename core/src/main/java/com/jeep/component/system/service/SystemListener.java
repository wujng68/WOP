package com.jeep.component.system.service;

import com.jeep.core.log.WopLogger;
import com.jeep.core.prop.PropertyHolder;
import java.util.Locale;

/**
 * 功能描述：
 *
 *    @日期：2013-12-30 17:14:05
 *    @作者：wujng
 *    @版本：1.0
 */
public class SystemListener {
    private static final WopLogger logger = new WopLogger(SystemListener.class);

    private static boolean running=false;
    
    private static String basePath;
    private static String contextPath;
    private static RuningTime runingTime=null;
    private static final  boolean memoryMonitor;
    private static final  boolean runingMonitor;
    private static MemoryMonitorThread memoryMonitorThread;
    
    static{
        memoryMonitor=PropertyHolder.getBooleanProperty("monitor.memory");        
        if(memoryMonitor){
            logger.info("启用内存监视日志");
            logger.info("Enable memory monitor log", Locale.ENGLISH);
        }else{
            logger.info("禁用内存监视日志");
            logger.info("Disable memory monitor log", Locale.ENGLISH);
        }
        runingMonitor=PropertyHolder.getBooleanProperty("monitor.runing");
        if(runingMonitor){
            logger.info("启用系统运行日志");
            logger.info("Enable system log", Locale.ENGLISH);
        }else{
            logger.info("禁用系统运行日志");
            logger.info("Disable system log", Locale.ENGLISH);
        }
    }
}
