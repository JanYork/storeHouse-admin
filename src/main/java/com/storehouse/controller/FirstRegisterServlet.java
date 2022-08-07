package com.storehouse.controller;

import com.storehouse.geetestSdk.GeetestLib;
import com.storehouse.geetestSdk.entity.GeetestLibResult;
import com.storehouse.geetestSdk.enums.DigestmodEnum;
import com.storehouse.geetestSdk.utils.PropertiesUtils;
import com.storehouse.task.CheckGeetestStatusTask;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证初始化接口，GET请求
 */
@WebServlet(name = "FirstRegisterServlet", urlPatterns = "/register")
public class FirstRegisterServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(FirstRegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        必传参数
            digestmod 此版本sdk可支持md5、sha256、hmac-sha256，md5之外的算法需特殊配置的账号，联系极验客服
        自定义参数,可选择添加
            user_id 客户端用户的唯一标识，确定用户的唯一性；作用于提供进阶数据分析服务，可在register和validate接口传入，不传入也不影响验证服务的使用；若担心用户信息风险，可作预处理(如哈希处理)再提供到极验
            client_type 客户端类型，web：电脑上的浏览器；h5：手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生sdk植入app应用的方式；unknown：未知
            ip_address 客户端请求sdk服务器的ip地址
        */
        response.setContentType("application/json;charset=UTF-8");
        GeetestLib gtLib = new GeetestLib(PropertiesUtils.get("geetest.id"), PropertiesUtils.get("geetest.key"));
        String userId = "test";
        GeetestLibResult result = null;
        // 检测存入redis中的极验云状态标识
        if (CheckGeetestStatusTask.checkGeetestStatusRedisFlag()) {
            DigestmodEnum digestmodEnum = DigestmodEnum.MD5;
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("digestmod", digestmodEnum.getName());
            paramMap.put("user_id", userId);
            paramMap.put("client_type", "web");
            result = gtLib.register(digestmodEnum, paramMap);
            LOG.info("Redis中的极验云状态标识正常");
        } else {
            result = gtLib.localInit();
            LOG.info("Redis中的极验云状态标识未存入，开启本地初始化模式————>localInit()");
        }
        // 注意，不要更改返回的结构和值类型
        PrintWriter out = response.getWriter();
        out.println(result.getData());
    }
}
