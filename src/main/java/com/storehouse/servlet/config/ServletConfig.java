package com.storehouse.servlet.config;

import com.alibaba.fastjson.JSON;
import com.storehouse.entity.Config;
import com.storehouse.service.ConfigService;
import com.storehouse.service.impl.ConfigServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置管理
 */
@WebServlet(name = "SercletConfig",urlPatterns = "/ServletConfig")
public class ServletConfig extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String act = request.getParameter("act");
        Config config = new Config();
        ConfigService cs = new ConfigServiceImpl();
        Map<String, Object> map = new HashMap<>();
        if ("add".equals(act)) {
            if (request.getParameter("eam").equals("") || request.getParameter("getp").equals("")) {
                map.put("code", "403");
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            } else {
                String smtpEamil = request.getParameter("smtpEamil");
                String smtpPwd = request.getParameter("smtpPwd");
                String baduAppKey = request.getParameter("baduAppKey");
                String baiduSecretKey = request.getParameter("baiduSecretKey");
                String geetestId = request.getParameter("geetestId");
                String geetestKey = request.getParameter("geetestKey");
                config.setSmtpEamil(smtpEamil);
                config.setSmtpPwd(smtpPwd);
                config.setBaduAppKey(baduAppKey);
                config.setBaiduSecretKey(baiduSecretKey);
                config.setGeetestId(geetestId);
                config.setGeetestKey(geetestKey);
                if (cs.addConfig(config) > 0) {
                    map.put("code", "200");
                    map.put("msg", "ok");
                    out.print(JSON.toJSONString(map));
                } else {
                    map.put("code", "403");
                    map.put("msg", "拒绝访问");
                    out.print(JSON.toJSONString(map));
                }
            }
        } else if ("select".equals(act)) {
            List<Config> list = cs.queryConfig();
            if (list != null) {
                map.put("code", "200");
                map.put("msg", "ok");
                out.print(JSON.toJSONString(list));
            } else {
                map.put("code", "404");
                map.put("msg", "请求错误");
            }
        } else if ("dele".equals(act)) {
            int cd = Integer.parseInt(request.getParameter("cd"));
            if (cd != 0) {
                cs.delConfig(cd);
                map.put("code", "200");
                map.put("msg", "ok");
                out.print(JSON.toJSONString(map));
            } else {
                map.put("code", "403");
                map.put("msg", "拒绝访问");
                out.print(JSON.toJSONString(map));
            }
        } else if ("getTrue".equals(act)) {
            int cid = Integer.parseInt(request.getParameter("cid"));
            if (cid != 0) {
                String smtpEamil = request.getParameter("smtpEamil");
                String smtpPwd = request.getParameter("smtpPwd");
                String baduAppKey = request.getParameter("baduAppKey");
                String baiduSecretKey = request.getParameter("baiduSecretKey");
                String geetestId = request.getParameter("geetestId");
                String geetestKey = request.getParameter("geetestKey");
                config.setCid(cid);
                config.setSmtpEamil(smtpEamil);
                config.setSmtpPwd(smtpPwd);
                config.setBaduAppKey(baduAppKey);
                config.setBaiduSecretKey(baiduSecretKey);
                config.setGeetestId(geetestId);
                config.setGeetestKey(geetestKey);
                if (cs.updateConfig(config) > 0) {
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
