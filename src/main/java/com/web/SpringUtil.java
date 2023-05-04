package com.web;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringUtil {

    private static ConfigurableApplicationContext appContext;

    public static void setAppContext(ConfigurableApplicationContext appContext) {
        SpringUtil.appContext = appContext;
    }

    public static ConfigurableApplicationContext getAppContext() {
        return appContext;
    }

}
