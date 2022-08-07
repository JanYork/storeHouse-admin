package com.storehouse.servlet.thumbs;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Thumbs;
import com.storehouse.service.ThumbsService;
import com.storehouse.service.impl.ThumbsServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点赞管理
 */
@WebServlet(name = "ServletThumbs", urlPatterns = "/ServletThumbs")
public class ServletThumbs extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        ThumbsService ts = new ThumbsServiceImpl();
        Thumbs thumbs = new Thumbs();
        String act = request.getParameter("act");
        Map<String, Object> map = new HashMap<String, Object>();
        if ("add".equals(act)) {
            if (request.getParameter("pid").equals("") || request.getParameter("topicId").equals("")) {
                map.put("code", "403");
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            } else {
                int thumbsTopicld = Integer.parseInt(request.getParameter("thumbsTopicld"));
                int thumbsUid = Integer.parseInt(request.getParameter("thumbsUid"));
                thumbs.setThumbsTopicld(thumbsTopicld);
                thumbs.setThumbsUid(thumbsUid);
                int result = ts.addThumbs(thumbs);
                if (result > 0) {
                    map.put("code", "200");
                    map.put("msg", "ok");
                    out.print(JSON.toJSONString(map));
                } else {
                    map.put("code", "403");
                    map.put("msg", "拒绝访问");
                    out.print(JSON.toJSONString(map));
                }
            }
        }
        if ("query".equals(act)) {
            List<Thumbs> list = ts.queryThumbs();
            if (list != null) {
                map.put("code", "200");
                map.put("msg", "ok");
                out.print(JSON.toJSONString(list));
            } else {
                map.put("code", "404");
                map.put("msg", "请求错误");
            }
        } else if ("del".equals(act)) {
            int thumbsId = Integer.parseInt(request.getParameter("thumbsId"));
            if (thumbsId != 0) {
                int result = ts.delThumbs(thumbsId);
                if (result > 0) {
                    map.put("code", "200");
                    map.put("msg", "ok");
                    out.print(JSON.toJSONString(map));
                } else {
                    map.put("code", "403");
                    map.put("msg", "拒绝访问");
                }
            } else {
                out.print("404");
            }
        } else if ("getTrue".equals(act)) {
            int thumbsId = Integer.parseInt(request.getParameter("thumbsId"));
            if (thumbsId != 0) {
                int thumbsTopicld = Integer.parseInt(request.getParameter("thumbsTopicld"));
                int thumbsUid = Integer.parseInt(request.getParameter("thumbsUid"));
                thumbs.setThumbsId(thumbsId);
                thumbs.setThumbsTopicld(thumbsTopicld);
                thumbs.setThumbsUid(thumbsUid);
                if (ts.updateThumbs(thumbs) > 0) {
                    map.put("code", "200");
                    map.put("msg", "ok");
                    out.print(JSON.toJSONString(map));
                } else {
                    map.put("code", "403");
                    map.put("msg", "拒绝访问");
                }
            }
        }
        out.flush();
        out.close();
    }
}
