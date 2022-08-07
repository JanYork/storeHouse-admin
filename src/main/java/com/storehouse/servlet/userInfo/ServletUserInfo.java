package com.storehouse.servlet.userInfo;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.UserInfo;
import com.storehouse.service.UserInfoService;
import com.storehouse.service.impl.UserInfoServiceImpl;
import com.storehouse.utils.Md5Utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户管理接口
 */
@WebServlet(name = "ServletUserInfo", urlPatterns = "/ServletUserInfo")
public class ServletUserInfo extends HttpServlet {
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
        //创建实现类对象
        UserInfoService userInfoService = new UserInfoServiceImpl();
        //创建Map集合
        Map<String, Object> map = new HashMap<>();
        //获取数据
        String act = request.getParameter("act");
        String account = request.getParameter("account");
        String pwd = request.getParameter("pwd");
        String number = request.getParameter("number");
        String address = request.getParameter("address");
        String eamil = request.getParameter("eamil");
        String introduce = request.getParameter("introduce");
        String url = request.getParameter("url");
        String nanoId = request.getParameter("nanoId");
        if ("add".equals(act)) {
            int count = Integer.parseInt(request.getParameter("count"));
            int comment = Integer.parseInt(request.getParameter("comment"));
            int state = Integer.parseInt(request.getParameter("state"));
            UserInfo userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setPassword(pwd);
            userInfo.setTelephoneNumber(number);
            userInfo.setAvatarAddress(address);
            userInfo.setEamil(eamil);
            userInfo.setIntroduce(introduce);
            userInfo.setUrl(url);
            userInfo.setTopicCount(count);
            userInfo.setCommentCount(comment);
            userInfo.setNanoId(nanoId);
            userInfo.setUserState(state);
            //创建时间对象
            Date date = new Date();
            //时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                userInfo.setCreateTime(sdf.parse(sdf.format(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int result = userInfoService.addUsertInfo(userInfo);
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
            String str = request.getParameter("uid");
            int uid = 0;
            if (str != null && str.length() != 0) {
                uid = Integer.parseInt(str);
            }
            //创建实体类对象
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(uid);
            int result = userInfoService.delUsertInfoByUid(userInfo.getUid());
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
        } else if ("getUserInfoByUid".equals(act)) {
            String str = request.getParameter("topicUid");
            int uid = 0;
            if (str != null && str.length() != 0) {
                uid = Integer.parseInt(str);
            }
            //创建实体类对象
            UserInfo userInfo = userInfoService.getUsertInfoByUid(uid);
            userInfo.setUid(uid);
            //封装成JSON格式
            String json = JSON.toJSONString(userInfo);
            out.print(json);
        } else if ("update".equals(act)) {
            int count = Integer.parseInt(request.getParameter("count"));
            int comment = Integer.parseInt(request.getParameter("comment"));
            int state = Integer.parseInt(request.getParameter("state"));
            int uid = Integer.parseInt(request.getParameter("uid"));
            UserInfo userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setPassword(pwd);
            userInfo.setTelephoneNumber(number);
            userInfo.setAvatarAddress(address);
            userInfo.setEamil(eamil);
            userInfo.setIntroduce(introduce);
            userInfo.setUrl(url);
            userInfo.setTopicCount(count);
            userInfo.setCommentCount(comment);
            userInfo.setNanoId(nanoId);
            userInfo.setUserState(state);
            userInfo.setUid(uid);
            //创建时间对象
            Date date = new Date();
            //时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                userInfo.setCreateTime(sdf.parse(sdf.format(date)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            int result = userInfoService.updateUsertInfo(userInfo);
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
            List<UserInfo> list = userInfoService.getUsertInfoAll();
            //封装成JSON格式
            String json = JSON.toJSONString(list);
            out.print(json);
        } else if ("avatar".equals(act)) {
            int uid = Integer.parseInt(request.getParameter("uid"));
            // 调用业务逻辑层的方法
            String userInfo = userInfoService.getUserHeadImgByUid(uid);
            // 判断 userInfo 是否为空
            if (userInfo != null) {
                map.put("code", 200);
                map.put("msg", "ok");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", 403);
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
        } else if ("getUidByTopicCount".equals(act)) {
            int uid = Integer.parseInt(request.getParameter("uid"));
            // 调用业务逻辑层的方法
            int count = userInfoService.getTopicCountByUid(uid);
            // 判断是否查询成功
            if (count > 0) {
                map.put("code", 200);
                map.put("msg", "ok");
                map.put("count", count);
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", 403);
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
        } else if ("getUidUpdateUserInfo".equals(act)) {
            int uid = Integer.parseInt(request.getParameter("uid"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String path = request.getParameter("path");
            String description = request.getParameter("description");

            UserInfo userInfo = new UserInfo();
            userInfo.setAccount(name);
            userInfo.setEamil(email);
            userInfo.setUrl(path);
            userInfo.setIntroduce(description);
            userInfo.setUid(uid);
            int result = userInfoService.updateUserInfoByUid(userInfo);
            // 判断是否查询成功
            if (result > 0) {
                map.put("code", 200);
                map.put("msg", "修改成功");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", 403);
                map.put("msg", "修改失败");
                out.print(JSON.toJSONString(map));
            }
        } else if("getUserInfoByAccount".equals(act)){
            //获取uid
            String uid = request.getParameter("uid");
            String imageUrl = request.getParameter("imageUrl");
            UserInfo userInfo = new UserInfo();
            userInfo.setUid(Integer.parseInt(uid));
            userInfo.setAvatarAddress(imageUrl);
            int result = userInfoService.updateUserHeadImgByUid(userInfo);
            if(result > 0){
                map.put("code", 200);
                map.put("msg", "上传成功");
                out.print(JSON.toJSONString(map));
            }else{
                map.put("code", 403);
                map.put("msg", "上传失败");
                out.print(JSON.toJSONString(map));
            }
        } else if ("getUidUpdatePwd".equals(act)) {
            int uid = Integer.parseInt(request.getParameter("uid"));
            UserInfo userInfo = new UserInfo();
            userInfo.setPassword(Md5Utils.md5To16Lower(request.getParameter("pwd"), nanoId));
            userInfo.setUid(uid);
            //调用业务逻辑层的方法
            int result = userInfoService.updateUserPasswordByUid(userInfo);
            // 判断是否查询成功
            if (result > 0) {
                map.put("code", 200);
                map.put("msg", "修改成功，请重新登陆");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", 403);
                map.put("msg", "修改失败");
                out.print(JSON.toJSONString(map));
            }
        }else {
            map.put("code", 403);
            map.put("msg", "参数缺失");
            out.print(JSON.toJSONString(map));
        }
    }
}
