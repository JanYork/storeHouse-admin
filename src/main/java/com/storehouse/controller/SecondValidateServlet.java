package com.storehouse.controller;

import com.storehouse.geetestSdk.GeetestLib;
import com.storehouse.geetestSdk.entity.GeetestLibResult;
import com.storehouse.geetestSdk.utils.PropertiesUtils;
import com.storehouse.task.CheckGeetestStatusTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 二次验证接口，POST请求
 */
@WebServlet(name = "SecondValidateServlet", urlPatterns = "/validate")
public class SecondValidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        GeetestLib gtLib = new GeetestLib(PropertiesUtils.get("geetest.id"), PropertiesUtils.get("geetest.key"));
        String challenge = request.getParameter(GeetestLib.GEETEST_CHALLENGE);
        String validate = request.getParameter(GeetestLib.GEETEST_VALIDATE);
        String seccode = request.getParameter(GeetestLib.GEETEST_SECCODE);
        PrintWriter out = response.getWriter();
        GeetestLibResult result = null;
        // 检测存入redis中的极验云状态标识
        if (CheckGeetestStatusTask.checkGeetestStatusRedisFlag()) {
            result = gtLib.successValidate(challenge, validate, seccode, null);
        } else {
            result = gtLib.failValidate(challenge, validate, seccode);
        }
        // 注意，不要更改返回的结构和值类型
        if (result.getStatus() == 1) {
            out.println(String.format("{\"result\":\"success\",\"version\":\"%s\"}", GeetestLib.VERSION));
        } else {
            out.println(String.format("{\"result\":\"fail\",\"version\":\"%s\",\"msg\":\"%s\"}", GeetestLib.VERSION, result.getMsg()));
        }
    }
}
