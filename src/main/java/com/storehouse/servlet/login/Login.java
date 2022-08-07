package com.storehouse.servlet.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.storehouse.entity.UserInfo;
import com.storehouse.service.UserInfoService;
import com.storehouse.service.impl.UserInfoServiceImpl;
import com.storehouse.utils.JWTUtil;
import com.storehouse.utils.Md5Utils;
import com.storehouse.utils.RedisUtils;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import static com.storehouse.utils.SmsMessagesApi.goMessages;

/**
 * 用户登录接口
 * @author Jay
 */
@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //设置响应编码
        response.setContentType("text/html;charset=UTF-8");
        //输出流对象
        PrintWriter out = response.getWriter();
        HashMap<String, Object> map = new HashMap<>();
        //定义id与nanoId
        String uid;
        String nanoId;
        String avatar;
        //接收Token
        String token = request.getParameter("token");
        //获取act
        String act = request.getParameter("act");
        if (act == null) {
            if (token != null) {
                //验证token是否过期
                JSONObject tokenMsg = JWTUtil.validateJWT(token);
                //判断tokenMsg的code是否为200
                if (tokenMsg.get("Code").equals(200)) {
                    Claims claim = JWTUtil.parseJWT(token);
                    JSONObject jwtData = JSONObject.parseObject(JSON.toJSONString(claim));
                    map.put("token", token);
                    map.put("JWTdata", jwtData);
                    map.put("tokenMsg", tokenMsg);
                    map.put("code", "200");
                    map.put("msg", "验证成功");
                    map.put("Success", tokenMsg.get("Success"));
                } else if ("1005".equals(tokenMsg.get("Code"))) {
                    map.put("code", "1005");
                    map.put("msg", "Token验证过期，请重新登录");
                    map.put("Success", tokenMsg.get("Success"));
                } else {
                    map.put("code", "500");
                    map.put("msg", "Token验证错误");
                }
            }
        } else {
            switch (act) {
                case "phone": {
                    String loginStr = request.getParameter("value");
                    //正则判断是否是手机号
                    if (loginStr.matches("^1[3456789]\\d\\d{8}$")) {
                        UserInfoService userInfoService = new UserInfoServiceImpl();
                        UserInfo userInfo = userInfoService.getUsertInfoByTelephoneNumber(loginStr);
                        if (userInfo == null) {
                            map.put("code", "403");
                            map.put("msg", "该手机号未注册");
                        } else {
                            String code = request.getParameter("pwd");
                            //获取redis中的验证码
                            RedisUtils redisUtils = RedisUtils.initPool();
                            String codeStr = redisUtils.get(loginStr);
                            if (codeStr == null) {
                                map.put("code", "403");
                                map.put("msg", "验证码已过期，请重新获取");
                            } else {
                                if (codeStr.equals(code)) {
                                    // 发送短信验证码
                                    Boolean result = goMessages(loginStr, code);
                                    if (result) {
                                        uid = String.valueOf(userInfo.getUid());
                                        nanoId = userInfo.getNanoId();
                                        avatar = userInfo.getAvatarAddress();
                                        //判断是否为空
                                        if (avatar == null || "".equals(avatar)) {
                                            String email = userInfo.getEamil();
                                            //判断是不是QQ邮箱
                                            if (email.contains("qq.com")) {
                                                //将邮箱转换为QQ号码
                                                String qq = email.substring(0, email.indexOf("@"));
                                                avatar = "http://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=640";
                                            } else {
                                                avatar = "https://a.ideaopen.cn/JanYork/pHWbLhkb.png";
                                            }
                                        }
                                        // 生成token
                                        String tokenStr = JWTUtil.createJWT(uid, nanoId, 1000 * 60 * 60 * 24 * 7);
                                        map.put("token", tokenStr);
                                        map.put("uid", uid);
                                        map.put("nanoId", nanoId);
                                        map.put("avatar", avatar);
                                        map.put("uname", userInfo.getAccount());
                                        map.put("phone", userInfo.getTelephoneNumber());
                                        map.put("code", "200");
                                        map.put("msg", "登录成功");
                                    } else {
                                        map.put("code", "500");
                                        map.put("msg", "验证码发送失败");
                                    }
                                } else {
                                    map.put("code", "403");
                                    map.put("msg", "验证码错误");
                                }
                            }
                        }
                    } else {
                        map.put("code", "403");
                        map.put("msg", "请输入正确的手机号");
                    }
                    break;
                }
                case "email": {
                    String loginStr = request.getParameter("value");
                    //正则判断是否是邮箱
                    if (loginStr.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
                        UserInfoService userInfoService = new UserInfoServiceImpl();
                        UserInfo userInfo = userInfoService.getUsertInfoByEmail(loginStr);
                        if (userInfo == null) {
                            map.put("code", "403");
                            map.put("msg", "该邮箱未注册");
                        } else {
                            // 将id的类型转换为int类型
                            uid = String.valueOf(userInfo.getUid());
                            nanoId = userInfo.getNanoId();
                            avatar = userInfo.getAvatarAddress();
                            //判断是否为空
                            if (avatar == null || "".equals(avatar)) {
                                String email = userInfo.getEamil();
                                //判断是不是QQ邮箱
                                if (email.contains("qq.com")) {
                                    //将邮箱转换为QQ号码
                                    String qq = email.substring(0, email.indexOf("@"));
                                    avatar = "http://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=640";
                                } else {
                                    avatar = "https://a.ideaopen.cn/JanYork/pHWbLhkb.png";
                                }
                            }
                            String pwd = Md5Utils.md5To16Lower(request.getParameter("pwd"), nanoId);
                            if (userInfo.getPassword().equals(pwd)) {
                                String tokenStr = JWTUtil.createJWT(uid, nanoId, 1000 * 60 * 60 * 24 * 7);
                                map.put("token", tokenStr);
                                map.put("uid", uid);
                                map.put("nanoId", nanoId);
                                map.put("avatar", avatar);
                                map.put("uname", userInfo.getAccount());
                                map.put("phone", userInfo.getTelephoneNumber());
                                map.put("code", "200");
                                map.put("msg", "登录成功");
                            } else {
                                map.put("code", "403");
                                map.put("msg", "密码错误");
                            }
                        }
                    } else {
                        map.put("code", "403");
                        map.put("msg", "请输入正确的邮箱");
                    }
                    break;
                }
                case "username": {
                    String loginStr = request.getParameter("value");
                    // 使用正则判断昵称格式
                    if (loginStr.matches("^[a-zA-Z\\d_-]{4,18}$")) {
                        UserInfoService userInfoService = new UserInfoServiceImpl();
                        UserInfo userInfo = userInfoService.getUsertInfoByAccount(loginStr);
                        if (userInfo == null) {
                            map.put("code", "403");
                            map.put("msg", "该账户不存在");
                        } else {
                            // 将id的类型转换为int类型
                            uid = String.valueOf(userInfo.getUid());
                            nanoId = userInfo.getNanoId();
                            avatar = userInfo.getAvatarAddress();
                            //判断是否为空
                            if (avatar == null || "".equals(avatar)) {
                                String email = userInfo.getEamil();
                                //判断是不是QQ邮箱
                                if (email.contains("qq.com")) {
                                    //将邮箱转换为QQ号码
                                    String qq = email.substring(0, email.indexOf("@"));
                                    avatar = "http://q1.qlogo.cn/g?b=qq&nk=" + qq + "&s=640";
                                } else {
                                    avatar = "https://a.ideaopen.cn/JanYork/pHWbLhkb.png";
                                }
                            }
                            String pwd = Md5Utils.md5To16Lower(request.getParameter("pwd"), nanoId);
                            if (userInfo.getPassword().equals(pwd)) {
                                String tokenStr = JWTUtil.createJWT(uid, nanoId, 1000 * 60 * 60 * 24 * 7);
                                map.put("token", tokenStr);
                                map.put("uid", uid);
                                map.put("nanoId", nanoId);
                                map.put("avatar", avatar);
                                map.put("uname", userInfo.getAccount());
                                map.put("phone", userInfo.getTelephoneNumber());
                                map.put("code", 200);
                                map.put("msg", "登录成功");
                            } else {
                                map.put("code", "403");
                                map.put("msg", "密码错误");
                            }
                        }
                    } else {
                        map.put("code", "403");
                        map.put("msg", "请输入正确的昵称");
                    }
                    break;
                }
                default:
                    map.put("code", "403");
                    map.put("msg", "请按照正确的登录方式执行");
                    break;
            }
        }
        out.print(JSON.toJSONString(map));
    }
}
