package com.storehouse.servlet.comment;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Comment;
import com.storehouse.service.CommentService;
import com.storehouse.service.impl.CommentServiceImpl;
import com.storehouse.utils.DateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论管理
 */
@WebServlet(name = "ServletComment", value = "/ServletComment")
public class ServletComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // 创建输出响应对象
        PrintWriter out = response.getWriter();
        //获取act
        String act = request.getParameter("act");
        // 创建业务逻辑层对象
        Map<String,Object> map = new HashMap<>();
        Comment comment = new Comment();
        CommentService cs = new CommentServiceImpl();
        if("add".equals(act)){
            if(request.getParameter("con").equals("") || request.getParameter("uids").equals("") || request.getParameter("to").equals("")){
                map.put("code","403");
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }else{
                String content = request.getParameter("content");
                int commentUid = Integer.parseInt(request.getParameter("commentUid"));
                int commentTopicId = Integer.parseInt(request.getParameter("commentTopicId"));
                String commentIp =request.getParameter("commentIp");
                String commentEquipment =request.getParameter("commentEquipment");
                comment.setContent(content);
                comment.setCommentTopicId(commentTopicId);
                comment.setCommentUid(commentUid);
                comment.setCommentIp(commentIp);
                comment.setCommentEquipment(commentEquipment);
                comment.setCommentTime(DateUtils.getCurrentDate());
                    if(cs.addComment(comment)>0){
                        map.put("code","200");
                        map.put("msg","ok");
                        out.print(JSON.toJSONString(map));
                    }else{
                        map.put("code","403");
                        map.put("msg", "拒绝访问");
                        out.print(JSON.toJSONString(map));
                    }
            }
        }else if("select".equals(act)){
            List<Comment> list = cs.queryComment();
            if(list != null){
                map.put("code","200");
                map.put("msg","ok");
                out.print(JSON.toJSONString(list));
            }else{
                map.put("code", "404");
                map.put("msg", "请求错误");
            }
        }else if("del".equals(act)){
            int commentId = Integer.parseInt(request.getParameter("commentId"));
            if(commentId != 0){
                cs.delComment(commentId);
                map.put("code","200");
                map.put("msg","ok");
                out.print(JSON.toJSONString(map));
            }else{
                map.put("code","403");
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
        }else if ("getTrue".equals(act)) {
            int commentId = Integer.parseInt(request.getParameter("commentId"));
            if (commentId != 0) {
                String content = request.getParameter("content");
                int commentUid = Integer.parseInt(request.getParameter("commentUid"));
                int commentTopicId = Integer.parseInt(request.getParameter("commentTopicId"));
                String commentIp =request.getParameter("commentIp");
                String commentEquipment =request.getParameter("commentEquipment");
                comment.setContent(content);
                comment.setCommentTopicId(commentTopicId);
                comment.setCommentUid(commentUid);
                comment.setCommentIp(commentIp);
                comment.setCommentEquipment(commentEquipment);
                comment.setCommentId(commentId);
                comment.setCommentTime(DateUtils.getCurrentDate());
                if (cs.updateComment(comment)>0) {
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
