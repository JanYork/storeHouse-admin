package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Category;
import com.storehouse.service.CategoryService;
import com.storehouse.service.TagService;
import com.storehouse.service.impl.CategoryServiceImpl;
import com.storehouse.service.impl.TagServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 板块参数
 */
@WebServlet(name = "getCategoryList", value = "/getCategoryList")
public class getCategoryList extends HttpServlet {
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
        // 创建输出响应对象
        PrintWriter out = response.getWriter();
        // 创建业务逻辑层对象
        CategoryService cs = new CategoryServiceImpl();
        List<Category> listForCategory = cs.queryCategory();
        if (listForCategory != null) {
            out.print(JSON.toJSONString(listForCategory));
        } else {
            out.print("{\"code\":\"404\",\"msg\":\"请求错误\"}");
        }
    }
}
