package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Category;
import com.storehouse.service.CategoryService;
import com.storehouse.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "getCategoryForId", value = "/getCategoryForId")
public class getCategoryForId extends HttpServlet {
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
        //List
        List<Category> list;
        // 创建业务逻辑层对象
        CategoryService cs = new CategoryServiceImpl();
        // 获取categoryId
        String categoryId = request.getParameter("categoryId");
        // 调用业务逻辑层方法
        list = cs.queryCategoryForId(categoryId);
        // 转换为json字符串
        out.print(JSON.toJSONString(list));
        out.flush();
    }
}
