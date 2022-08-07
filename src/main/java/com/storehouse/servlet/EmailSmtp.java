package com.storehouse.servlet;

import com.alibaba.fastjson.JSON;
import com.storehouse.utils.HtmlTemplateToSMTP;
import com.storehouse.utils.SmsMessagesApi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件SMTP接口
 */
@WebServlet(name = "emailSmtp", value = "/emailSmtp")
public class EmailSmtp extends HttpServlet {
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
        String receiveMailAccount = request.getParameter("email");
        if (receiveMailAccount == null || receiveMailAccount.equals("")) {
            out.print(JSON.toJSONString("{\"code\":\"403\",\"msg\":\"邮箱参数错误\"}"));
        } else {
            //正则验证邮箱
            String regex = "^([a-z\\dA-Z]+[-|_.]?)+[a-z\\dA-Z]@([a-z\\dA-Z]+(-[a-z\\dA-Z]+)?\\.)+[a-zA-Z]{2,}$";
            if (receiveMailAccount.matches(regex)) {
                HtmlTemplateToSMTP htp = new HtmlTemplateToSMTP();
                SmsMessagesApi.goMessages(htp, receiveMailAccount);
                Map<String, Object> map = new HashMap<>();
                map.put("code", "200");
                map.put("msg", "ok");
                out.print(JSON.toJSONString(map));
            } else {
                out.print(JSON.toJSONString("{\"code\":\"403\",\"msg\":\"邮箱格式错误\"}"));
            }
        }
    }
}
