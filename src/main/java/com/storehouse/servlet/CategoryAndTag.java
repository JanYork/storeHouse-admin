package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Category;
import com.storehouse.entity.Tag;
import com.storehouse.service.CategoryService;
import com.storehouse.service.TagService;
import com.storehouse.service.impl.CategoryServiceImpl;
import com.storehouse.service.impl.TagServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签与板块参数
 */
@WebServlet(name = "CategoryAndTag", value = "/CategoryAndTag")
public class CategoryAndTag extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        // 设置响应编码
        response.setContentType("text/html;charset=UTF-8");
        // 创建输出响应对象
        PrintWriter out = response.getWriter();
        // 创建业务逻辑层对象
        CategoryService cs = new CategoryServiceImpl();
        TagService tagService = new TagServiceImpl();
        //创建map对象
        Map<String, Object> map = new HashMap<>();
        List<Category> listForCategory = cs.queryCategory();
        List<Tag> listForTag = tagService.getTagAll();
        //将listForCategory和listForTag放入map中
        if (listForCategory != null || listForTag != null) {
            map.put("code", "200");
            map.put("msg", "ok");
            map.put("Category", listForCategory);
            map.put("Tag", listForTag);
            out.print(JSON.toJSONString(map));
        } else {
            map.put("code", "404");
            map.put("msg", "请求错误");
        }
    }
}
