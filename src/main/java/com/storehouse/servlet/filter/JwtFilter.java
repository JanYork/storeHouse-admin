package com.storehouse.servlet.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.storehouse.entity.JwtResult;
import com.storehouse.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT接口过滤器
 * 登录、注册等操作不过滤  拦截servlet下的所有接口
 *
 * @author JanYork
 */
@WebFilter(filterName = "JwtFilter", urlPatterns = "/servlet/*")
public class JwtFilter implements Filter {

    /**
     * 日志记录器Log4j
     */
    private static final Logger LOG = Logger.getLogger(JwtFilter.class);

    @Override
    public void init(FilterConfig config) {
        /* 初始化时调用 */
    }

    @Override
    public void destroy() {
        /* 销毁时调用 */
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //输出日志
        LOG.info("进入了JwtFilter过滤器");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // jws设置在Header的Authorization里面
        String tokenStr = request.getHeader("Authorization");
        // 错误说明
        String msg = "系统异常";
        // 成功标识
        boolean flag = false;
        // 错误码
        int rtnValue;
        // 存放结果的实体类 有属性：success、errNum、message
        JwtResult jr;
        // 用户未登录
        if (tokenStr == null || tokenStr.equals("")) {
            LOG.error("用户未登录或为找到JWT字段资源");
            rtnValue = 1004;
            msg = "未登录";
            jr = new JwtResult(rtnValue, flag, msg);
            out.print(JSON.toJSONString(jr));
            out.flush();
            out.close();
            return;
        }
        // 解析jws
        JSONObject pojo = JWTUtil.validateJWT(tokenStr);
        LOG.info("解析jws：" + pojo);
        if (pojo.getBooleanValue("Success")) {
            //获取Claims对象
            Claims claims = (Claims) pojo.get("Claims");
            //获取JWT的jti，即JWT唯一身份标识，一般用于一次性验证Token(暂时不使用)
            String account = claims.getId();
            // 用于转发下一个过滤器或者servlet
            chain.doFilter(request, response);
        } else {
            rtnValue = pojo.getIntValue("ErrCode");
            switch (rtnValue) {
                case 1005:
                    msg = "签名过期";
                    LOG.error("错误编号："+rtnValue+"消息："+msg);
                    break;
                case 1004:
                    msg = "未登录";
                    LOG.error("错误编号："+rtnValue+"消息："+msg);
                    break;
                default:
                    break;
            }
            jr = new JwtResult(rtnValue, flag, msg);
            out.print(JSON.toJSONString(jr));
            out.flush();
            out.close();
        }
    }
}
