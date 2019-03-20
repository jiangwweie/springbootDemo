/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jiangwei.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author JackBauer
 */

public class MathUtils {

    /**
     * 截取整数的后几位
     *
     * @param number 要处理的整数
     * @param digit 截取的位数
     * @param fillZero 位数不足是否在前面补0
     * @return 返回处理后的字符串
     */
    public static String intPrecision(int number, int digit, boolean fillZero) {
        StringBuilder sb = new StringBuilder();
        sb.append(number);
        int length = sb.length();
        if (length > digit) {
            return sb.substring(length - digit, length);
        } else if (length < digit && fillZero) {
            for (int i = 0; i < digit - length; i++) {
                sb.insert(0, '0');
            }
            return sb.toString();
        }
        return sb.toString();
    }

    private static Random random = new Random();

    /**
     * 获取在min和max之间的随机整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int genRandomInt(int min, int max) {
        if (random == null) {
            random = new Random();
        }
        int randomInt = random.nextInt(max - min + 1) + min;
        return randomInt;
    }

    /**
     * 32位MD5加密
     *
     * @param plainText
     * @return
     */
    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuilder sb = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            //32位加密  
            return sb.toString();
            // 16位的加密  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    /**
     * 按给定位数进行四舍五入
     *
     * @param origNum
     * @param bitCount
     * @return
     */
    public static double numFormatDigit(double origNum, int bitCount) {
        BigDecimal b = new BigDecimal(origNum);
        return b.setScale(bitCount, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard 身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(String idCard) {
        int iAge = 0;
        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyy-MM-dd)
     */
    public static String getBirthByIdCard(String idCard) {
        return idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday 身份编号
     * @return 生日(yyyy-MM-dd)
     */
    public static int getAgeByBirth(String birthday) {
        if (birthday == null || birthday.length() == 0) {
            return -1;
        }
        int year;
        try {
            year = Integer.parseInt(birthday.substring(0, 4));
        } catch (NumberFormatException | StringIndexOutOfBoundsException ex) {
            return -1;
        }
        SimpleDateFormat dateType = new SimpleDateFormat("yyyy");
        int now = Integer.parseInt(dateType.format(new Date()));
        int age = now - year;
        return age;
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(M-男，F-女，N-未知)
     */
    public static String getSexByIdCard(String idCard) {
        String sex = "未知";
        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sex = "1";//男
        } else {
            sex = "2";//女
        }
        return sex;
    }

    /**
     *
     * @return 生成随机UUID
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    /**
     *
     * @return base64加密
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes));
    }

    /**
     *
     * @return base64解密
     */
    public static String decode(String result) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(result), "UTF-8");
    }

}
