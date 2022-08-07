package com.storehouse.utils;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 通用时间工具类
 * @author JanYork
 */
public class DateUtils {
    /**
     * 将util.Date转换为sql.Date
     */
    public static java.sql.Date utilDateToSqlDate(Date date) {
        // 将util.Date转换为sql.Date
        return new java.sql.Date(date.getTime());
    }
    /**
     * 将sql.Date转换为util.Date
     */
    public static Date sqlDateToUtilDate(Date date) {
        return new Date(date.getTime());
    }
    /**
     * 获取当前时间
     */
    public static java.sql.Date getCurrentDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * 将(2022-07-31 12:00:00)格式的时间转换为Date对象
     */
    public static Date stringToDate(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 将(2022-07-31 12:00:00)格式的时间转换为Timestamp()对象
     */
    public static java.sql.Timestamp stringToTimestamp(String date) {
        //时间加上97768秒
        return new java.sql.Timestamp(stringToDate(date).getTime());
    }


    /**
     * 将getTimestamp()格式的时间转换为Date对象
     */
    public static java.sql.Date timestampToDate(java.sql.Timestamp timestamp) {
        return new java.sql.Date(timestamp.getTime());
    }

    /**
     * 将Date对象转换为getTimestamp()格式的时间
     */
    public static java.sql.Timestamp dateToTimestamp(Date date) {

        return new java.sql.Timestamp(date.getTime() + 100800 * 1000);
    }
}
