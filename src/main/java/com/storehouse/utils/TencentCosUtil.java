package com.storehouse.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Properties;

/**
 * 腾讯云COS工具类(云储存)
 * @author JanYork
 */
public class TencentCosUtil {

    /**
     * 创建Log4j日志对象
     */
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TencentCosUtil.class);

    static String bucketName;
    static COSCredentials cred;
    static ClientConfig clientConfig;
    static COSClient cosClient;
    static String regionName;

    static {
        //读取配置文件TencentCosUtil.properties
        Properties properties = new Properties();
        InputStream inputStream = TencentCosUtil.class.getClassLoader().getResourceAsStream("TencentCosUtil.properties");
        try {
            properties.load(inputStream);
            LOG.info("读取配置文件TencentCosUtil.properties成功");
        } catch (Exception e) {
            LOG.error("读取配置文件TencentCosUtil.properties异常", e);
        }
        // 腾讯云COS用户身份信息（secretId, secretKey）
        String secretId = properties.getProperty("secretId");
        String secretKey = properties.getProperty("secretKey");
        regionName = properties.getProperty("regionName");
        bucketName = properties.getProperty("bucketName");

        cred = new BasicCOSCredentials(secretId, secretKey);

        // 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224

        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(regionName);
        clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
        LOG.info("初始化腾讯云COS客户端成功");
    }

    /**
     * 图片URL上传到腾讯云COS
     * @param filepath 图片路径(URL)
     * @param fileName 图片名称
     */
    public static void uploadForUrl(String filepath, String fileName) {
       byte[] bytes =ImgToBytes.getUrlImg(filepath);
       uploadByteForCloud(bytes, fileName);
    }

    /**
     * 图片文件字节流上传到腾讯云COS
     * @param bytes 图片文件字节数组
     * @param fileName 图片名称
     */
    public static String uploadByteForCloud(byte[] bytes, String fileName) {
        int length = bytes.length;
        // 获取文件流
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 从输入流上传必须制定content length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
        objectMetadata.setContentLength(length);
        // 默认下载时根据cos路径key的后缀返回响应的contenttype, 上传时设置contenttype会覆盖默认值
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, byteArrayInputStream, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String eTag = putObjectResult.getETag();
        LOG.info("上传成功，eTag：" + putObjectResult);
        //返回图片路径
        String imgUrl = "https://" + bucketName + ".cos."+regionName+".myqcloud.com/" + fileName;
        return imgUrl;
    }

    /**
     * 删除COS上的文件
     * @param fileName 文件名称
     */
    public static void deleteFileFroCloud(String fileName) {
        cosClient.deleteObject(bucketName, fileName);
        LOG.info("删除文件成功");
        LOG.info("关闭腾讯云COS客户端成功");
    }
    /**
     * 查询COS上是否存在文件
     * @param fileName 文件名称
     */
    public static boolean isExistFile(String fileName) {
        return cosClient.doesObjectExist(bucketName, fileName);
    }
}
