package com.zz.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Z sir
 * @Description TODO
 * @date 2020/12/23 15:43
 */
public class DateUtils {

    //获取当前时间
    public static String yyyymmddHHmmss(){
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateStr = today.format(fmt);
        return dateStr;
    }
    // 根据具体转化为指定时间搓
    public static String ToDateTime(String time){
        String str = "2019-03-03";
        //指定转换格式
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //进行转换
        LocalDate date = LocalDate.parse(str, fmt);
        return str;
    }


}
