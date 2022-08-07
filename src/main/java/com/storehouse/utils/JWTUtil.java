package com.storehouse.utils;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * JWT通用加密解密认证工具类
 * @author JanYork
 * @date 2022年7月21日08点49分
 * @Description JWT工具类
 */
public class JWTUtil {
    /**
     * JWT密文加密秘钥
     */
    public static String SECRET = "qwerasdfdxzvdfajjjjjeiojznvxyyyyfaowikkkkdl";

    /**
     * 生成Jwt的方法
     *
     * @param nanoId    用户唯一标识nanoId
     * @param uId   用户ID
     * @param ttlMillis 过期时间
     * @return Token String 凭证
     */
    public static String createJWT(String nanoId, String uId, long ttlMillis) {
        // 签名方法 HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成Jwt的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成秘钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // JwtBuilder设置JWT所存储的信息
        JwtBuilder builder = Jwts.builder().setId(nanoId).setIssuedAt(now).setSubject(uId).signWith(signatureAlgorithm, signingKey);

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //设置iss
        builder.setIssuer("storehouse");

        // 构建JWT并将其序列化为紧凑的URL安全字符串
        return builder.compact();
    }

    /**
     * 解析Jwt字符串
     *
     * @param jwt Jwt字符串
     * @return Claims 解析后的对象
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET)).parseClaimsJws(jwt).getBody();
    }

    /**
     * 验证JWT
     *
     * @param jwtStr jwt字符串
     * @return JOSNObject 解析结果
     * Success 成功标识
     * true：成功
     * false：失败
     * Claim 声明对象
     * ErrCode 错误码
     * 1005：过期
     * 1004：未登录
     */
    public static JSONObject validateJWT(String jwtStr) {
        JSONObject pojo = new JSONObject();
        Claims claims;
        try {
            claims = parseJWT(jwtStr);
            pojo.put("Success", true);
            pojo.put("Claims", claims);
            //添加成功参数
            pojo.put("Code", 200);
            pojo.put("Msg", "验证成功");
        } catch (ExpiredJwtException e) {
            pojo.put("Success", false);
            pojo.put("Code", 1005);
            pojo.put("Error", e.getMessage());
            pojo.put("Msg", "登录已过期，请重新登录");
        } catch (Exception e) {
            pojo.put("Success", false);
            pojo.put("Code", 1004);
            pojo.put("Error", e.getMessage());
            pojo.put("Msg", "验证异常");
        }
        return pojo;
    }
}
