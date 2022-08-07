package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Topic;
import com.storehouse.service.TopicService;
import com.storehouse.service.impl.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * id返回文章数据
 */
@WebServlet(name = "TopicToId", value = "/TopicToId")
public class TopicToId extends HttpServlet {
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
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");

        // 创建输出对象
        PrintWriter out = response.getWriter();
        // 创建业务逻辑层对象
        TopicService ts = new TopicServiceImpl();
        // 获取topicId
        String topicId = request.getParameter("topicId");
        //判断是否为空
        if (topicId == null || "".equals(topicId)) {
            // 调用业务逻辑层方法
            out.print("请求参数错误");
        } else {
            int id = Integer.parseInt(topicId);
            //创建实体类对象
            Topic topic = ts.getTopicByTopicId(id);
            out.print(JSON.toJSONString(topic));
        }
    }
}
