package com.ty.wq.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 * @version 1.0
 * @description: 生成随机数工具类
 * @date 2020/9/11 14:01
 */
public class GenerateUtils {
    public static final String GENERATE_CODES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String GENERATE_SIMPLE_CODES = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";
    public static final String NUMBER_CODES = "0123456789";
    private static final Random RANDOM = new Random();

    /**
     * 使用系统默认字符源生成验证码
     *
     * @param size 验证码长度
     * @return
     */
    public static String generateString(int size) {
        return generateString(size, GENERATE_CODES);
    }

    /**
     * 生成简单的字符串，排除难辨认的0,1，i,I,l,L,o,O
     * @param size
     * @return
     */
    public static String generateSimpleString(int size) {
        return generateString(size, GENERATE_SIMPLE_CODES);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param size 验证码长度
     * @param sources 验证码字符源
     * @return
     */
    public static String generateString(int size, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = GENERATE_CODES;
        }
        int codesLen = sources.length();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(sources.charAt(RANDOM.nextInt(codesLen - 1)));
        }
        return sb.toString();
    }

    /**
     * 数字验证码
     * @param size 长度
     * @return 返回值
     */
    public static String generateNumber(int size) {
        return generateNumber(size, NUMBER_CODES);
    }
    /**
     * 数字验证码
     * @param size 验证码长度
     * @param sources 验证码字符源
     * @return 返回值
     */
    public static String generateNumber(int size, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = NUMBER_CODES;
        }
        int codesLen = sources.length();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(sources.charAt(RANDOM.nextInt(codesLen)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            list.add(generateString(8));
        }
        HashSet<String> has = new HashSet<>(list);
        System.out.println(list.size());
        System.out.println(has.size());
    }

}
