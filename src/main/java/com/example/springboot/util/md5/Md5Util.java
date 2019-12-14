package com.example.springboot.util.md5;

import org.springframework.util.DigestUtils;

public class Md5Util {

    private static final String slat = "longhui";

    public static String getMD5(String str) {
        String base = str + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
