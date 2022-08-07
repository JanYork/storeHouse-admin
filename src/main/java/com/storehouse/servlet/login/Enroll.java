package com.storehouse.servlet.login;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.storehouse.entity.UserInfo;
import com.storehouse.service.UserInfoService;
import com.storehouse.service.impl.UserInfoServiceImpl;
import com.storehouse.utils.DateUtils;
import com.storehouse.utils.JWTUtil;
import com.storehouse.utils.Md5Utils;
import com.storehouse.utils.RedisUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册接口
 *
 * @author Jay
 */
@WebServlet(name = "Enroll", urlPatterns = "/Enroll")
public class Enroll extends HttpServlet {
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
        // 输出流对象
        PrintWriter out = response.getWriter();

        // 获取传过来的昵称
        String account = request.getParameter("account");
        // 获取传过来的邮箱
        String email = request.getParameter("email");
        // 获取传过来的nanoid
        String nanoId = request.getParameter("nanoId");
        // 获取传过来的验证码
        String code = request.getParameter("code");
        // 获取传过来的密码
        String password = Md5Utils.md5To16Lower(request.getParameter("password"), nanoId);
        String avatar;

        RedisUtils redisUtils = RedisUtils.initPool();
        String codeStr = redisUtils.get(email);
        if (codeStr == null) {
            out.print(JSON.toJSONString(new HashMap<String, Object>() {{
                put("code", "500");
                put("msg", "验证码已过期");
            }}));
            return;
        }
        //创建Map集合
        Map<String, Object> map = new HashMap<>();
        if (codeStr.equals(code)) {
            // 创建业务逻辑类对象
            UserInfoService userService = new UserInfoServiceImpl();
            // 创建实体类对象
            UserInfo userInfo = new UserInfo();
            userInfo.setAccount(account);
            userInfo.setPassword(password);
            userInfo.setTelephoneNumber(null);
            //判断邮箱是否是QQ邮箱
            if (email.contains("qq.com") && email.substring(0, email.indexOf("@")).matches("\\d+")) {
                //获取QQ号
                String qq = email.substring(0, email.indexOf("@"));
                avatar = "http://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=640";
            } else {
                avatar = "https://a.ideaopen.cn/JanYork/pHWbLhkb.png";
            }
            userInfo.setAvatarAddress(avatar);
            userInfo.setEamil(email);
            userInfo.setIntroduce("用户");
            userInfo.setUrl(null);
            userInfo.setTopicCount(0);
            userInfo.setCommentCount(0);
            userInfo.setNanoId(nanoId);
            userInfo.setUserState(0);
            //获取现在的时间
            String nowTime = DateUtil.now();
            userInfo.setCreateTime(DateUtils.stringToDate(nowTime));
            // 调用业务逻辑类的方法
            int result = userService.addUsertInfo(userInfo);
            // 判断是否注册成功
            if (result > 0) {
                UserInfoService userInfoService = new UserInfoServiceImpl();
                //根据nanoid查询uid
                String uid = String.valueOf(userInfoService.findUidByNanoId(nanoId).getUid());
                String tokenStr = JWTUtil.createJWT(uid, nanoId, 1000 * 60 * 60 * 24 * 7);
                map.put("code", 200);
                map.put("msg", "ok");
                map.put("token", tokenStr);
                map.put("uid", uid);
                map.put("nanoId", nanoId);
                map.put("avatar", avatar);
                map.put("uname", userInfo.getAccount());
            } else {
                map.put("code", 403);
                map.put("msg", "拒绝访问");
            }
        } else {
            map.put("code", 403);
            map.put("msg", "验证码错误");
        }
        out.print(JSON.toJSONString(map));
    }
}
