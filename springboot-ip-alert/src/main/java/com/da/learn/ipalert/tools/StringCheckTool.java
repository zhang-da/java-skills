package com.da.learn.ipalert.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCheckTool {
    private static final String IP_PATTERN = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";


    public static boolean checkIp(String ip) {
        if (ip == null) {
            return false;
        }
        Pattern r = Pattern.compile(IP_PATTERN);
        Matcher m = r.matcher(ip);
        return m.matches();
    }
}
