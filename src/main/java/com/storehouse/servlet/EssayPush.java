package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Topic;
import com.storehouse.service.TopicService;
import com.storehouse.service.impl.TopicServiceImpl;
import com.storehouse.utils.BaiduAiUtils;
import com.storehouse.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文章推送写入数据库
 */
@WebServlet(name = "EssayPush", value = "/EssayPush")
public class EssayPush extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //返回错误，不允许使用get方式访问
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(new HashMap<String, String>(){{
            put("code", "403");
            put("msg", "请使用post方式访问");
        }}));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //设置响应编码
        response.setContentType("text/html;charset=UTF-8");
        //创建输出响应对象
        PrintWriter out = response.getWriter();
        //文章对象
        Topic topic = new Topic();
        //创建业务逻辑层对象
        TopicService topicService = new TopicServiceImpl();
        //创建参数打包对象
        Map<String, Object> map = new HashMap<>();

        //获取标题
        String title = request.getParameter("title");
        //获取内容
        String content = request.getParameter("content");
        //获取分类
        String cateValue = request.getParameter("cate_value");
        //获取标签
        String tagValue = request.getParameter("tag_value");
        //获取时间
        String dateTime = request.getParameter("dateTime");
        //获取作者uid
        String uid = request.getParameter("uid");
        if (title != null && content != null && cateValue != null && tagValue != null && dateTime != null && uid != null) {
            JSONObject baiduAi = BaiduAiUtils.textAudit(content);
            String aiMsg = baiduAi.getString("conclusion");
            if (aiMsg.equals("合规")) {
                //设置标题
                topic.setTitle(title);
                //设置内容
                topic.setTopicContent(content);
                //设置板块
                topic.setTopicCategoryId(Integer.parseInt(cateValue));
                //设置标签
                topic.setTopicTagId(Integer.parseInt(tagValue));
                //转换为Date类型
                Date date = DateUtils.stringToDate(dateTime);
                topic.setTopicTime(DateUtils.dateToTimestamp(date));
                //作者id
                topic.setTopicUid(Integer.parseInt(uid));
                //调用添加方法
                int result = topicService.addTopic(topic);
                //判断是否添加成功
                if (result > 0) {
                    //添加成功
                    map.put("code", "200");
                    map.put("msg", "添加成功");
                } else {
                    //添加失败
                    map.put("code", "400");
                    map.put("msg", "添加失败");
                }
            } else {
                map.put("code", "403");
                JSONArray data = baiduAi.getJSONArray("data");
                String msg = data.getJSONObject(0).getString("msg");
                if (msg != null) {
                    map.put("msg", msg);
                } else {
                    map.put("msg", "未知错误");
                }
            }
        } else {
            map.put("code", "404");
            map.put("msg", "参数不全");
        }
        out.print(JSON.toJSONString(map));
    }
}
