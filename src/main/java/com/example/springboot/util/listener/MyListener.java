package com.example.springboot.util.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("web项目启动。。");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("web项目销毁。。");
    }
}
