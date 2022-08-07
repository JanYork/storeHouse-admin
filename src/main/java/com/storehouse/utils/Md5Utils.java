package com.storehouse.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Md5加密工具类
 *
 * @author JanYork
 * @date 2022年7月21日22点12分
 */
public class Md5Utils {
    /**
     * @Title: md5Lower
     * @Description:不加盐值32位小写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5Lower(String plainText) {
        String md5 = null;
        if (null != plainText && !"".equals(plainText)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(plainText.getBytes(StandardCharsets.UTF_8));
                md5 = new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md5;
    }

    /**
     * @Title: md5Lower
     * @Description:加盐值32位小写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5Lower(String plainText, String saltValue) {
        String md5 = null;
        if (null != plainText && !"".equals(plainText) && null != saltValue && !"".equals(saltValue)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(plainText.getBytes(StandardCharsets.UTF_8));
                md.update(saltValue.getBytes(StandardCharsets.UTF_8));
                md5 = new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md5;
    }

    /**
     * @Title: md5To16Lower
     * @Description:不加盐值16位小写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5To16Lower(String plainText) {
        String md5 = md5Lower(plainText);
        return null == md5 ? md5 : md5.substring(8, 24);
    }

    /**
     * @Title: md5To16Lower
     * @Description:加盐值16位小写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5To16Lower(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null == md5 ? md5 : md5.substring(8, 24);
    }

    /**
     * @Title: md5To16Upper
     * @Description:不加盐值16位大写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5To16Upper(String plainText) {
        String md5 = md5To16Lower(plainText);
        return null == md5 ? md5 : md5.toUpperCase();
    }

    /**
     * @Title: md5To16Upper
     * @Description:加盐值16位大写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5To16Upper(String plainText, String saltValue) {
        String md5 = md5To16Lower(plainText, saltValue);
        return null == md5 ? md5 : md5.toUpperCase();
    }

    /**
     * @Title: md5Upper
     * @Description:不加盐值32位大写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5Upper(String plainText) {
        String md5 = md5Lower(plainText);
        return null == md5 ? md5 : md5.toUpperCase();
    }

    /**
     * @Title: md5Upper
     * @Description:加盐值32位大写
     * @author JanYork
     * @date 2022年7月21日22点26分
     */
    public static String md5Upper(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null == md5 ? md5 : md5.toUpperCase();
    }
}
