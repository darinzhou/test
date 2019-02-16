package com.comcast.algorithms;

/**
 * Created by zzhou200 on 8/16/15.
 */
public class RegexTest {

    public static boolean isNumber(String s) {
        return s.matches("^[+-]?\\d+\\.?\\d+$");
    }

    public static boolean isIpAddress(String s) {
        return s.matches("^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})$");
    }

    public static boolean isEmailAddress(String s) {
        return s.matches("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }
}
