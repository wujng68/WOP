package com.jeep.core.spring;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 功能描述：自定义spring核心监听器
 *
 *    @日期：2013-12-30 17:02:24
 *    @作者：wujng
 *    @版本：1.0
 */
public class WopContextLoaderListener extends ContextLoaderListener{

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event); 
    }

}
