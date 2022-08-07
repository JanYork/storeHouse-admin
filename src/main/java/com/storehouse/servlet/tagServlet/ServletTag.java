package com.storehouse.servlet.tagServlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Tag;
import com.storehouse.service.TagService;
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
 * 标签管理
 */
@WebServlet(name = "ServletTag", value = "/ServletTag")
public class ServletTag extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("UTF-8");
        //设置响应编码
        response.setContentType("text/html;charset=UTF-8");
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        //创建输出响应对象
        PrintWriter out = response.getWriter();

        //创建实现类对象
        TagService tagService = new TagServiceImpl();
        //获取数据
        String act = request.getParameter("act");
        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<>();
        //执行添加操作
        if ("add".equals(act)) {
            int count = Integer.parseInt(request.getParameter("count"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            //创建实体类对象
            Tag tag = new Tag();
            //进行赋值
            tag.setTagName(name);
            tag.setTopicCount(count);
            tag.setTagCategoryId(categoryId);
            //调用添加方法
            int result = tagService.addTag(tag);
            //进行判断
            if (result > 0) {
                map.put("code", 200);
                map.put("msg", "ok");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", 403);
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
            //根据标签的编号来删除信息
        } else if ("del".equals(act)) {
            String str = request.getParameter("tagId");
            int tagId = 0;
            if (str != null && str.length() != 0) {
                tagId = Integer.parseInt(str);
            }
            //创建实体类对象
            Tag tag = new Tag();
            //赋值
            tag.setTagId(tagId);
            //调用删除方法，把tagId带进去
            int result = tagService.delTagByTagId(tag.getTagId());
            //进行判断
            if (result > 0) {
                map.put("code", 200);
                map.put("msg", "ok");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", 403);
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
            //根据编号修改主题
        } else if ("getTagByTagId".equals(act)) {
            //获取数据
            String str = request.getParameter("tagId");
            int tagId = 0;
            if (str != null && str.length() != 0) {
                tagId = Integer.parseInt(str);
            }
            Tag tag = tagService.getTagByTagId(tagId);
            //封装成JSON格式
            String jsonTopic = JSON.toJSONString(tag);
            out.print(jsonTopic);
            //根据tagId修改标签信息
        } else if ("update".equals(act)) {
            //获取数据
            String str = request.getParameter("tagId");
            int count = Integer.parseInt(request.getParameter("count"));
            int categoryid = Integer.parseInt(request.getParameter("categoryid"));
            //对传过来的tagId做出一个判断
            int tagId = 0;
            if (str != null && str.length() != 0) {
                tagId = Integer.parseInt(str);
            }
            //创建实体类对象
            Tag tag = new Tag();
            //对值进行赋值
            tag.setTagId(tagId);
            tag.setTagName(name);
            tag.setTopicCount(count);
            tag.setTagCategoryId(categoryid);
            //调用标签的修改方法
            int result = tagService.updateTag(tag);
            //进行判断
            if (result > 0) {
                map.put("code", 200);
                map.put("msg", "ok");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", 403);
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
            //查询所有标签的信息
        } else if ("select".equals(act)) {
            //调用查询的方法
            List<Tag> list = tagService.getTagAll();
            //转换成JSON格式
            String json = JSON.toJSONString(list);
            out.print(json);
        } else {
            map.put("code", 403);
            map.put("msg", "拒绝访问");
            out.print(JSON.toJSONString(map));
        }
        out.flush();
        out.close();
    }
}
