package com.bigdata.eshop.util;

import java.util.List;
/*
* Util 类，用于判断集合是否为空
* */
public class ValidataUtil {
    public static boolean isValid(List list){
        if (list.isEmpty() || list == null){
            return false;
        }
        return true;
    }
}
