package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.UserInfo;
import com.storehouse.service.UserInfoService;
import com.storehouse.service.impl.UserInfoServiceImpl;
import com.storehouse.utils.RedisUtils;
import com.storehouse.utils.SmsMessagesApi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 修改手机号接口
 */
@WebServlet(name = "setPhone", value = "/setPhone")
public class setPhone extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HashMap<String,Object> map = new HashMap<>();
        UserInfoService userInfoService = new UserInfoServiceImpl();
        //获取uid
        String uid = request.getParameter("uid");
        //获取手机号
        String phone = request.getParameter("phone");
        //获取验证码
        String code = request.getParameter("code");

        if (uid == null || "".equals(uid)) {
            map.put("code", "403");
            map.put("msg", "uid参数错误");
        }else if (phone == null || "".equals(phone)) {
            map.put("code", "403");
            map.put("msg", "手机号获取错误");
        }else if (code == null || "".equals(code)) {
            map.put("code", "403");
            map.put("msg", "验证码获取错误");
        }else {
            //从Redis中获取验证码
            RedisUtils redisUtils = RedisUtils.initPool();
            String codeStr = redisUtils.get(phone);
            if (codeStr == null || "".equals(codeStr)) {
                map.put("code", "403");
                map.put("msg", "验证码已过期");
            }else if (!code.equals(codeStr)) {
                map.put("code", "403");
                map.put("msg", "验证码错误");
            }else {
                UserInfo userInfo = new UserInfo();
                userInfo.setUid(Integer.parseInt(uid));
                userInfo.setTelephoneNumber(phone);
                //修改手机号
                int result = userInfoService.updateUserTelephoneByUid(userInfo);
                if (result > 0) {
                    map.put("code", "200");
                    map.put("msg", "修改成功");
                }else {
                    map.put("code", "403");
                    map.put("msg", "修改失败");
                }
            }
        }
        out.print(JSON.toJSONString(map));
    }
}
