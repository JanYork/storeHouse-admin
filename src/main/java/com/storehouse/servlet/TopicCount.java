package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.service.TopicService;
import com.storehouse.service.impl.TopicServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计文章数量
 */
@WebServlet(name = "TopicCount", value = "/TopicCount")
public class TopicCount extends HttpServlet {
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
        // 创建Map集合
        Map<String, Object> map = new HashMap<>();
        // 获取板块id
        String categoryId = request.getParameter("categoryId");
        int count;
        if (categoryId == null || categoryId.equals("")) {
            count = ts.getTopicCount();
        } else {
            count = ts.getTopicCount(Integer.parseInt(categoryId));
        }
        // 判断是否有数据
        if (count > 0) {
            map.put("code", 200);
            map.put("msg", "ok");
            map.put("count", count);
        } else {
            map.put("code", 403);
            map.put("msg", "拒绝访问");
        }
        // 将map集合转换成json格式的字符串
        out.print(JSON.toJSONString(map));
        out.flush();
        out.close();
    }
}
