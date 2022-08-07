package com.storehouse.servlet.topicpage;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Topic;
import com.storehouse.service.TopicService;
import com.storehouse.service.impl.TopicServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 分页分板块查询文章
 */
@WebServlet(name = "TopicBrowsePages", value = "/TopicBrowsePages")
public class TopicBrowsePages extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("utf-8");
        // 设置响应编码
        response.setContentType("text/html;charset=utf-8");

        // 创建输出对象
        PrintWriter out = response.getWriter();
        // 创建业务逻辑层对象
        TopicService ts = new TopicServiceImpl();
        List<Topic> list;
        // 获取当前页码
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        // 获取每页显示的数量
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        //获取categoryId
        String categoryId = request.getParameter("categoryId");
        // 调用业务逻辑层方法,先判断是否为空
        if (categoryId == null || "".equals(categoryId)) {
            // 调用业务逻辑层方法
            list = ts.getTopicByBrowse(currentPage, pageSize);
        } else {
            list = ts.getTopicByBrowse(currentPage, pageSize, categoryId);
        }
        // 将pages对象转换为json格式的字符串
        String json = JSON.toJSONString(list);
        // 输出json格式的字符串
        out.print(json);
        out.flush();
        out.close();
    }
}
