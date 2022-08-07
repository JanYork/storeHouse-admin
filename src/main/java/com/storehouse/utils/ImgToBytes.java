package com.storehouse.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.apache.commons.io.FileUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 将图片转换为Byte数组工具类
 * @author JanYork
 */
public class ImgToBytes {

    /**
     * 将图片URL转换为字节数组
     * @param url 图片外链
     * @return 图片字节数组
     */
    public static byte[] getUrlImg(String url){
        byte[] bytes = null;
        //获取url链接中的图片，并将其转化为字节数组
        try {
            BufferedImage image = ImageIO.read(new URL(url));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //将image转化为字节数组,不指定图片类型，默认为png
            ImageIO.write(image, "png", baos);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }
    /**
     * 将本地图片转换为字节数组
     * @param path 图片本地路径
     * @return 图片字节数组
     */
    public static byte[] getLocalImg(String path){
        File file = new File(path);
        byte[] bytes;
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * 获取文件格式
     */
    public static String getFileType(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    /**
     * 修改文件名根据年月日+随机3字母
     */
    public static String getFileNameByTime(String path) {
        String fileType = getFileType(path);
        //获取当前时间，使用SimpleDateFormat格式化YYYYMM
        String date = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
        //随机生成大写字母3个字符
        String random1 = RandomUtil.randomStringUpper(3);
        //随机生成小写字母3个字符
        String random2 = RandomUtil.randomString(2);
        String random = random1 + random2;
        return date + random + "." + fileType;
    }
}
