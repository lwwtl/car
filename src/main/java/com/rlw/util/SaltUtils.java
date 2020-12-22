package com.rlw.util;

import java.util.Random;

public class SaltUtils {
    /**
     * 生成salt的静态方法
     * */
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //random.nextInt()的取值范围是前闭后开[m,n);
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String salt = getSalt(8);
        System.out.println(salt);
    }
}
