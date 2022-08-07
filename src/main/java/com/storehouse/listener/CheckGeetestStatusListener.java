package com.storehouse.listener;

import com.storehouse.geetestSdk.utils.PropertiesUtils;
import com.storehouse.task.CheckGeetestStatusTask;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.Timer;

@WebListener
public class CheckGeetestStatusListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    /**
     * 创建Log4j实例
     */
    private static final Logger LOG = Logger.getLogger(CheckGeetestStatusListener.class);

    public CheckGeetestStatusListener() {
    }

    /**
     * 监听状态
     *
     * @param servletContextEvent 事件
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Timer timer = new Timer();
        CheckGeetestStatusTask task = new CheckGeetestStatusTask();
        // 立即执行，每隔10秒执行一次task
        timer.schedule(task, 0, Integer.parseInt(PropertiesUtils.get("task.schedule.check.geetest.status.interval", "10000")));
        LOG.info("gtlog: 定时器开始执行: 监控极验云状态, 每隔10秒执行一次任务. ");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
