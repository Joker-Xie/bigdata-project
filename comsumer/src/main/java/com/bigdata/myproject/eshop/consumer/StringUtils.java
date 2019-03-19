package com.bigdata.myproject.eshop.consumer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

public class StringUtils {
    private static final String token = "\\|\\|\\|";
    public static String[] logSplit(String log){
        String[] arr = log.split(token);
        return arr;
    }
    public static String getHost(String[] arr){
        return arr[0].split(",")[0];
    }

    public static String getDate (String[] arr){
        String date = arr[2];
        return date.split(" ")[0];
    }
    //将时间为： 05/Mar/2019:09:40:01 转换成 yyyy/MM/dd/HH/mm
    public static String formatYyyymmddHHMMss(String[] arr){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss",Locale.US);
        String date = getDate(arr).trim();
        long dec = 0L;
        try {
            dec = sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localDf = new SimpleDateFormat("yyyy/MM/dd/HH/mm",Locale.US);
        return localDf.format(dec);
    }
}
