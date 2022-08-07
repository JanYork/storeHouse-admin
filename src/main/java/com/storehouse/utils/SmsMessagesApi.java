package com.storehouse.utils;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 短信接口工具类
 *
 * @author JanYork
 */
public class SmsMessagesApi {
    private static String USERNAME;
    private static String PWD;
    private static String HTTPURL;
    private static String TIME;
    private static String MAGCONTENT;
    private static String CODE;
    private static final Logger LOG = Logger.getLogger(SmsMessagesApi.class);

    static {
        //读取配置文件SmsMessagesApi.properties
        Properties properties = new Properties();
        InputStream inputStream = SmsMessagesApi.class.getClassLoader().getResourceAsStream("SmsMessagesApi.properties");
        try {
            properties.load(inputStream);
            LOG.info("读取配置文件SmsMessagesApi.properties成功");
        } catch (IOException e) {
            LOG.error("读取配置文件SmsMessagesApi.properties异常", e);
            throw new RuntimeException(e);
        }
        // 获取设置短信宝注册的用户名
        USERNAME = properties.getProperty("USERNAME");
        // 获取设置短信宝注册的密码
        PWD = properties.getProperty("PWD");
        // 获取短信接口地址
        HTTPURL = properties.getProperty("HTTPURL");
        // 获取短信模板内容
        MAGCONTENT = properties.getProperty("MAGCONTENT");
        // 获取过期时间
        TIME = properties.getProperty("TIME");
        LOG.info("初始化短信配置全局成功");
    }
    /**
     * 发送短信验证码方法
     *
     * @param key Redis中的key(将使用手机号)
     * @param phone 手机号码
     * @return 发送结果
     */
    public static Boolean goMessages(String phone,String key) {
        CODE = getRandomCodeForPhone(key);
        // 获取短信模板内容
        MAGCONTENT = "【StoreHouse团队】亲爱的StoreHouse论坛博主，您的验证码是"+CODE+"。有效期为"+getTIME()+"分钟,请尽快验证。";
        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(USERNAME).append("&");
        httpArg.append("p=").append(md5(PWD)).append("&");
        httpArg.append("m=").append(phone).append("&");
        httpArg.append("c=").append(encodeUrlString(MAGCONTENT, "UTF-8"));

        String result = request(HTTPURL, httpArg.toString());
        if ("0".equals(result)) {
            LOG.info("短信发送成功" + "，手机号：" + phone);
            return true;
        } else {
            if ("30".equals(result)) {
                LOG.error("错误密码");
            } else if ("40".equals(result)) {
                LOG.error("账号不存在");
            } else if ("41".equals(result)) {
                LOG.error("余额不足");
            } else if ("43".equals(result)) {
                LOG.error("IP地址限制");
            } else if ("50".equals(result)) {
                LOG.error("内容含有敏感词");
            } else if ("51".equals(result)) {
                LOG.error("手机号码不正确");
            } else {
                LOG.error("发送失败");
            }
            return false;
        }
    }

    /**
     * 发送邮件验证码方法
     *
     * @param key Redis中的key(将使用邮箱账户)
     */
    public static Boolean goMessages(HtmlTemplateToSMTP htp, String key) {
        CODE = getRandomCodeForEmail(key);
        EmailSmtpUtils esu = new EmailSmtpUtils(htp.getEmailForCodeTp(CODE, key), key);
        LOG.info("邮件模板创建成功" + "，Key：" + key);
        new Thread(esu).start();
        LOG.info("邮件发送线程创建启动成功");
        return true;
    }



    /**
     * 发送请求
     *
     * @param httpUrl 接口地址
     * @param httpArg 拼接参数
     * @return 返回结果result
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = reader.readLine();
            if (strRead != null) {
                sbf.append(strRead);
                while ((strRead = reader.readLine()) != null) {
                    sbf.append("\n");
                    sbf.append(strRead);
                }
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer();
            for (byte value : b) {
                i = value;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String encodeUrlString(String str, String charset) {
        String strret;
        if (str == null) {
            return null;
        }
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }

    /**
     * 生成随机验证码
     * @param key Redis中的key(将使用手机号)
     * @return 验证码
     */
    public static String getRandomCodeForPhone(String key) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            sb.append(Math.round(Math.random() * 9));
        }
        //初始化Redis工具类哦
        RedisUtils redisUtils = RedisUtils.initPool();
        if (redisUtils.get(key) != null) {
            //清除原来的验证码
            redisUtils.delete(key);
        }
        //存入redis中
        redisUtils.set(key, sb.toString());
        //设置过期时间
        redisUtils.expire(key, 2, TimeUnit.MINUTES);
        LOG.info("生成验证码并写入Redis：" + sb.toString()+"，RedisKey："+key);
        //返回验证码
        return sb.toString();
    }

    /**
     * 生成随机验证码
     * @param key Redis中的key(将使用邮箱账户)
     * @return 验证码
     */
    public static String getRandomCodeForEmail(String key) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            sb.append(Math.round(Math.random() * 9));
        }
        //初始化Redis工具类哦
        RedisUtils redisUtils = RedisUtils.initPool();
        //存入redis中
        redisUtils.set(key, sb.toString());
        //设置过期时间
        redisUtils.expire(key, 2, TimeUnit.MINUTES);
        LOG.info("生成验证码并写入Redis：" + sb.toString()+"，RedisKey："+key);
        //返回验证码
        return sb.toString();
    }

    /**
     * 验证码有效期
     * @return 有效期
     */
    public static String getTIME() {
        return TIME;
    }
}