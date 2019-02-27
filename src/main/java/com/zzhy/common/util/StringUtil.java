package com.zzhy.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 字符串工具类
 * Created by majt on 2018-06-20.
 */
public class StringUtil {
    private static final String BY_OR = "\\|";
    private static final String BY_COMMA = ",";

    public static List<String> splitStringByOR(String string){
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(string)){
            String[] strings = string.split(BY_OR);
            for (String str : strings) {
               list.add(str);
            }
            return list;
        }
       return null;
    }

    public static List<String> splitStringByComma(String string){
        if (StringUtils.isNotBlank(string)){
            return Arrays.asList( string.split(BY_COMMA));
        }
        return null;
    }

}
