import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSONObject;
import com.storehouse.entity.UserInfo;
import com.storehouse.service.UserInfoService;
import com.storehouse.service.impl.UserInfoServiceImpl;
import com.storehouse.utils.*;
import io.jsonwebtoken.Claims;
import io.lettuce.core.RedisURI;
import org.apache.commons.io.FileUtils;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Test {
    public static void main(String[] args) {

        //将(2022-07-31T16:00:00.000Z)变成(2022-07-31 16:00:00)
        String dateTime = "2022-07-31 12:00:00";
        //将字符串转换成日期格式
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);



        //使用System.currentTimeMillis()设置时间30秒
//        long nowMillis = 60 * 1000;
//        //获取token
//        String token = JWTUtil.createJWT("mdceF7BhHW5HjuaYbmSjD", "1",nowMillis);
//        System.out.println(token);

//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJtZGNlRjdCaEhXNUhqdWFZYm1TakQiLCJpYXQiOjE2NTg5OTQ5NTEsInN1YiI6IjEiLCJleHAiOjE2NTg5OTUwMTEsImlzcyI6InN0b3JlaG91c2UifQ.ngUEOr3T-O9MAcgDsnB4nZJy36GbS1kCQEbLl_iXUvU";
        //验证token
//        JSONObject json =  JWTUtil.validateJWT(token);
        //输出json中的内容
//        System.out.println(json.get("Code"));

        //解析token
//        Claims claims = JWTUtil.parseJWT(token);

        //遍历对象的所有参数
//        for (String key : claims.keySet()) {
//            System.out.println(key + ":" + claims.get(key));
//        }


        //测试短信验证码
//        SmsMessagesApi.goMessages("16670887668","janyork");
//        System.out.println(DateUtils.getCurrentDate());
        //读取图片io
//        byte[] bytes = ImgToBytes.getUrlImg("https://a.ideaopen.cn/JanYork/c8VI6Q4A.png");
        //上传图片到腾讯云
//        TencentCosUtil.uploadByteForCloud(bytes, "CCC.png");

//        System.out.println(ImgToBytes.getFileNameByTime("https://a.ideaopen.cn/JanYork/c8VI6Q4A.png"));
//        System.out.println(TencentCosUtil.isExistFile("est.png"));

        //测试md5加密
//        String md5 = Md5Utils.md5To16Lower("123456789");
//        System.out.println(md5);
//        String md52 = Md5Utils.md5To16Lower("123456");
//        System.out.println(md52.equals(md5));

        //测试百度Ai图片内容审核
//        BaiduAiUtils.imgAudit();
//        System.out.println(BaiduAiUtils.textAudit("+q26363"));
//        Redis.properties

//        RedisUtils redisUtils = RedisUtils.initPool();
////        //创建key
//        String key = "test";
//        //创建value
//        String value = "111111111";
//        //设置key-value
//        redisUtils.set(key, value);
        //写入键设置过期时间3s
//        System.out.println(redisUtils.expire("test", 10, TimeUnit.SECONDS));
//        //设置10秒后
//        try {
//            sleep(10000);
//            //查询test
//            System.out.println(redisUtils.get("test"));
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        String a = "111111111";
//        String redis = redisUtils.get("test");
//        System.out.println(redis);
//        System.out.println(a.equals(redis));

//        System.out.println(redisUtils.get("Test"));
        //接受邮件的邮箱账号

//        test();
    }

//    public static void test() {
//
//        RedisUtils redisUtils = RedisUtils.initPool();
//        //创建key
//        RedisUtils.set("test", "test");
//    }
}
