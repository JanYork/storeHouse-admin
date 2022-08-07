package com.storehouse.servlet.category;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Category;
import com.storehouse.service.CategoryService;
import com.storehouse.service.impl.CategoryServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 板块管理
 */
@WebServlet(name = "ServletCategory", urlPatterns = "/ServletCategory")
public class ServletCategory extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //创建输出对象
        PrintWriter out = response.getWriter();
        //获取act
        String act = request.getParameter("act");
        //创建实体对象
        Category category = new Category();
        //创建操作对象
        CategoryService cs = new CategoryServiceImpl();
        //创建map
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(act)) {
            if (request.getParameter("til").equals("") || request.getParameter("descr").equals("") || request.getParameter("lo").equals("")) {
                map.put("code", "403");
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            } else {
                String categoryTitle = request.getParameter("categoryTitle");
                String description = request.getParameter("description");
                String categoryLogo = request.getParameter("categoryLogo");
                int topicCount = Integer.parseInt(request.getParameter("topicCount"));
                int commentCount = Integer.parseInt(request.getParameter("commentCount"));
                category.setCategoryTitle(categoryTitle);
                category.setTopicCount(topicCount);
                category.setCommentCount(commentCount);
                category.setDescription(description);
                category.setCategoryLogo(categoryLogo);
                if (cs.addCategory(category) > 0) {
                    map.put("code", "200");
                    map.put("msg", "ok");
                    out.print(JSON.toJSONString(map));
                } else {
                    map.put("code", "403");
                    map.put("msg", "拒绝访问");
                    out.print(JSON.toJSONString(map));
                }
            }
        } else if ("select".equals(act)) {
            List<Category> list = cs.queryCategory();
            if (list != null) {
                map.put("code", "200");
                map.put("msg", "ok");
                out.print(JSON.toJSONString(list));
            } else {
                map.put("code", "404");
                map.put("msg", "请求错误");
            }
        } else if ("del".equals(act)) {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            if (categoryId != 0) {
                cs.delCategory(categoryId);
                map.put("code", "200");
                map.put("msg", "ok");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", "403");
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
        } else if ("getTrue".equals(act)) {
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            if (categoryId != 0) {
                String categoryTitle = request.getParameter("categoryTitle");
                String description = request.getParameter("description");
                String categoryLogo = request.getParameter("categoryLogo");
                int topicCount = Integer.parseInt(request.getParameter("topicCount"));
                int commentCount = Integer.parseInt(request.getParameter("commentCount"));
                category.setCategoryId(categoryId);
                category.setCategoryTitle(categoryTitle);
                category.setTopicCount(topicCount);
                category.setCommentCount(commentCount);
                category.setDescription(description);
                category.setCategoryLogo(categoryLogo);
                if (cs.updateCategory(category) > 0) {
                    map.put("code", "200");
                    map.put("msg", "ok");
                    out.print(JSON.toJSONString(map));
                } else {
                    map.put("code", "403");
                    map.put("msg", "拒绝访问");
                }
            }
        }
    }
}
