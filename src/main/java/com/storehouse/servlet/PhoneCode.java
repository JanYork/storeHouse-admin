package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.utils.SmsMessagesApi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 手机验证码接口
 * @author JanYork
 */
@WebServlet(name = "phoneCode", value = "/phoneCode")
public class PhoneCode extends HttpServlet {
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
        String receivePhone = request.getParameter("value");
        if (receivePhone == null || "".equals(receivePhone)) {
            map.put("code","403");
            map.put("msg","手机参数错误");
        }else {
            String regex = "^\\d{11}$";
            if (receivePhone.matches(regex)) {
                //发送短信
                Boolean codeMsg = SmsMessagesApi.goMessages(receivePhone,receivePhone);
                map.put("code","200");
                map.put("msg","ok");
                map.put("codeMsg",codeMsg);
            } else {
                map.put("code","403");
                map.put("msg","手机号格式错误");
            }
        }
        out.print(JSON.toJSONString(map));
    }
}
