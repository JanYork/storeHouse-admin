package com.storehouse.servlet.notification;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Notification;
import com.storehouse.service.NotificationService;
import com.storehouse.service.impl.NotificationServiceImpl;

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
 * 通知管理
 */
@WebServlet(name = "ServletNotification", urlPatterns = "/ServletNotification")
public class ServletNotification extends HttpServlet {
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
        NotificationService notice = new NotificationServiceImpl();
        //创建Map集合
        Map<String, Object> map = new HashMap<>();
        //获取元素的数据
        String act = request.getParameter("act");
        String action = request.getParameter("action");
        if ("add".equals(act)) {
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            int fromUid = Integer.parseInt(request.getParameter("fromUid"));
            //创建实体类对象
            Notification notification = new Notification();
            //进行赋值
            notification.setAction(action);
            notification.setSubjectId(subjectId);
            notification.setUserId(userId);
            notification.setFromUid(fromUid);
            //创建时间对象
            Date date = new Date();
            //时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                notification.setReadAt(sdf.parse(sdf.format(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int result = notice.addNotification(notification);
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
            String str = request.getParameter("nid");
            int nid = 0;
            if (str != null && str.length() != 0) {
                nid = Integer.parseInt(str);
            }
            //创建实体类对象
            Notification notification = new Notification();
            notification.setNid(nid);
            int result = notice.delNotificationByNid(notification.getNid());
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
        } else if ("getNotificationByNid".equals(act)) {
            String str = request.getParameter("nid");
            int nid = 0;
            if (str != null && str.length() != 0) {
                nid = Integer.parseInt(str);
            }
            //调用按编号查询的方法
            Notification notification = notice.getNotificationByNid(nid);
            //封装成JSON格式
            String json = JSON.toJSONString(notification);
            out.print(json);
        } else if ("update".equals(act)) {
            int nid = Integer.parseInt(request.getParameter("nid"));
            int subjectId = Integer.parseInt(request.getParameter("subjectId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            int fromUid = Integer.parseInt(request.getParameter("fromUid"));
            //创建实体类对象
            Notification notification = new Notification();
            //传值
            notification.setNid(nid);
            notification.setSubjectId(subjectId);
            notification.setUserId(userId);
            notification.setFromUid(fromUid);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                notification.setReadAt(sdf.parse(sdf.format(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用修改方法
            int result = notice.updateNotification(notification);
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
            List<Notification> list = notice.getNotificationAll();
            String strJSON = JSON.toJSONString(list);
            out.print(strJSON);
        }
    }
}
