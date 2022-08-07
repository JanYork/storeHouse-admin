package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Thumbs;
import com.storehouse.service.ThumbsService;
import com.storehouse.service.impl.ThumbsServiceImpl;
import com.storehouse.utils.TencentCosUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;

@WebServlet(name = "thumbsPush", value = "/thumbsPush")
public class thumbsPush extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        response.setCharacterEncoding("utf-8");

        response.setContentType("text/html;charset=utf-8");

        HashMap<String, String> map = new HashMap<>();
        //创建输出对象
        Writer out = response.getWriter();

        //获取uid
        String uid = request.getParameter("uid");
        //获取articleId
        String articleId = request.getParameter("articleId");

        //创建对象
        Thumbs thumbs = new Thumbs();
        thumbs.setThumbsUid(Integer.parseInt(uid));
        thumbs.setThumbsTopicld(Integer.parseInt(articleId));

        //创建Service对象
        ThumbsService thumbsService = new ThumbsServiceImpl();

        //查询是否已经点赞
        int count = thumbsService.ThumbsByUidAndArticleId(Integer.parseInt(uid), Integer.parseInt(articleId));
        if(count > 0) {
            //调用删除方法
            int result = thumbsService.delThumbs(count);
            if(result > 0) {
                map.put("code", "200");
                map.put("msg", "取消点赞");
            } else {
                map.put("code", "400");
                map.put("msg", "取消点赞失败");
            }
        }else {
            //判断
            if (thumbsService.addThumbs(thumbs) > 0) {
                map.put("code", "200");
                map.put("msg", "点赞成功");
            } else {
                map.put("code", "400");
                map.put("msg", "点赞失败");
            }
        }
        //输出
        out.write(JSON.toJSONString(map));
    }
}
