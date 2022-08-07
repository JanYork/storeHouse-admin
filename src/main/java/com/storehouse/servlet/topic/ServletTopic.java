package com.storehouse.servlet.topic;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Topic;
import com.storehouse.service.TopicService;
import com.storehouse.service.impl.TopicServiceImpl;
import com.storehouse.utils.DateUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话题管理
 */
@WebServlet(name = "ServletTopic", urlPatterns = "/ServletTopic")
public class ServletTopic extends HttpServlet {
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
        //创建输出响应对象
        PrintWriter out = response.getWriter();
        //创建业务逻辑层对象
        TopicService topicService = new TopicServiceImpl();
        //创建Map集合
        Map<String, Object> map = new HashMap<String, Object>();
        //获取数据
        String act = request.getParameter("act");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        if ("add".equals(act)) {
            //获取文本框上的数据
            int comment = Integer.parseInt(request.getParameter("comment"));
            int topicuId = Integer.parseInt(request.getParameter("topicuId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int tagId = Integer.parseInt(request.getParameter("tagId"));
            int browse = Integer.parseInt(request.getParameter("browse"));
            int thumbs = Integer.parseInt(request.getParameter("thumbs"));
            //创建实体类对象
            Topic topic = new Topic();
            //设置值
            topic.setTitle(title);
            topic.setTopicContent(content);
            topic.setCommentCount(comment);
            //创建时间对象
            Date date = new Date();
            //设置时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //把String转换成Data类型
                topic.setTopicTime(DateUtils.stringToTimestamp(sdf.format(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            topic.setTopicUid(topicuId);
            topic.setTopicCategoryId(categoryId);
            topic.setTopicTagId(tagId);
            topic.setBrowseCount(browse);
            topic.setThumbsUp(thumbs);
            //调用添加方法
            int result = topicService.addTopic(topic);
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
        } else if ("del".equals(act)) {
            String str = request.getParameter("topicId");
            int topicId = 0;
            if (str != null && str.length() != 0) {
                topicId = Integer.parseInt(str);
            }
            //创建实体类对象
            Topic topic = new Topic();
            topic.setTopicId(topicId);
            //调用删除方法
            int result = topicService.delTopicByTopicId(topic.getTopicId());
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
        } else if ("getTopicByTopicId".equals(act)) {
            String str = request.getParameter("topicId");
            int topicId = 0;
            if (str != null && str.length() != 0) {
                topicId = Integer.parseInt(str);
            }
            //创建实体类对象
            Topic topic = topicService.getTopicByTopicId(topicId);
            topic.setTopicId(topicId);
            //封装成JSON格式
            String jsonTopic = JSON.toJSONString(topic);
            out.print(jsonTopic);
        } else if ("update".equals(act)) {
            //获取文本框上的数据
            int topicId = Integer.parseInt(request.getParameter("topicId"));
            int comment = Integer.parseInt(request.getParameter("comment"));
            int topicuId = Integer.parseInt(request.getParameter("topicuId"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            int tagId = Integer.parseInt(request.getParameter("tagId"));
            int browse = Integer.parseInt(request.getParameter("browse"));
            int thumbs = Integer.parseInt(request.getParameter("thumbs"));
            //创建实体类对象
            Topic topic = new Topic();
            //设置值
            topic.setTitle(title);
            topic.setTopicContent(content);
            topic.setCommentCount(comment);
            topic.setTopicId(topicId);
            //创建时间对象
            Date date = new Date();
            //设置时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                //把String转换成Data类型
                topic.setTopicTime(DateUtils.stringToTimestamp(sdf.format(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            topic.setTopicUid(topicuId);
            topic.setTopicCategoryId(categoryId);
            topic.setTopicTagId(tagId);
            topic.setBrowseCount(browse);
            topic.setThumbsUp(thumbs);
            int result = topicService.updateTopic(topic);
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
        } else if ("list".equals(act)) {
            //调用查询所有信息的方法
            List<Topic> list = topicService.getTopicAll();
            String strJSON = JSON.toJSONString(list);
            out.print(strJSON);
        }
    }
}
