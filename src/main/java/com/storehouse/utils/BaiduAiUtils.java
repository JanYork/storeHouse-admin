package com.storehouse.utils;
import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 百度Ai内容审核通用工具类
 * @author JanYork
 * @date 2022年7月22日09点29分
 */
public class BaiduAiUtils {
    private static final Logger LOG = Logger.getLogger(BaiduAiUtils.class);
    public static String APP_ID;
    public static String API_KEY;
    public static String SECRET_KEY;

    static{
        //读取配置文件BaiduAiUtils.properties
        Properties properties = new Properties();
        InputStream inputStream = BaiduAiUtils.class.getClassLoader().getResourceAsStream("BaiduAiUtils.properties");
        try {
            properties.load(inputStream);
            LOG.info("读取配置文件BaiduAiUtils.properties成功");
        } catch (IOException e) {
            LOG.error("读取配置文件BaiduAiUtils.properties异常", e);
            throw new RuntimeException(e);
        }
        // 获取设置短信宝注册的ID
        APP_ID = properties.getProperty("APP_ID");
        // 获取设置短信宝注册的KEY
        API_KEY = properties.getProperty("API_KEY");
        // 获取设置短信宝注册的SECRET_KEY
        SECRET_KEY = properties.getProperty("SECRET_KEY");
    }

    /**
     * 图片内容审核
     * @param url 图片url外链
     */
    public static String imgAudit(String url) {
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        JSONObject res = client.imageCensorUserDefined(url, EImgType.URL, null);
        return res.toString();
    }

    /**
     * 图片内容审核
     * @param bytes 图片byte数组
     */
    public static String imgAudit(byte[] bytes) {
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        // 参数为本地图片文件二进制数组
        JSONObject res = client.imageCensorUserDefined(bytes, null);
        return res.toString();
    }

    /**
     * 图片内容审核
     * @param localImgPath 图片本地路径
     */
    public static String localImgAudit(String localImgPath){
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        JSONObject res = client.imageCensorUserDefined(localImgPath, EImgType.FILE, null);
        return res.toString();
    }

    /**
     * 文本内容审核
     * @param text 文本内容
     * @return 审核结果JSON字符串
     */
    public static JSONObject textAudit(String text) {
        // 初始化一个AipContentCensor
        AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
        JSONObject res = client.textCensorUserDefined(text);
        return res;
    }
}
