package com.ty.wq.utils;

import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * md5工具
 * @author Administrator
 */
public class Md5Utils {

    /**
     * md5和sha-1混合加密
     * @param data 要加密的内容
     * @return String md5和sha-1混合加密之后的密码
     */
    public static String md5AndSha(String data) {
        return sha(md5(data));
    }


    /**
     * md5加密
     * @param data 要加密的内容
     * @return String  md5加密之后的密码
     */
    public static String md5(String data) {
        return encrypt(data, "md5");
    }


    /**
     * sha-1加密
     * @param data  要加密的内容
     * @return  sha-1加密之后的密码
     */
    public static String sha(String data) {
        return encrypt(data, "sha-1");
    }


    /**
     * md5或者sha-1加密
     * @param data   要加密的内容
     * @param algorithmName  加密算法名称：md5或者sha-1，不区分大小写
     * @return String  md5或者sha-1加密之后的结果
     */
    private static String encrypt(String data, String algorithmName) {
        if (StringUtils.isBlank(data)) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if (StringUtils.isBlank(algorithmName)) {
            algorithmName = "md5";
        }
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] s = m.digest();
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * byte[]字节数组 转换成 十六进制字符串
     * @param arr 要转换的byte[]字节数组
     * @return  String 返回十六进制字符串
     */
    private static String hex(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : arr) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }


    /**
     * 返回加盐加密的数据
     * @param value 原数据
     * @param salt 盐
     * @return 返回加密后的数据
     */
    public static String encryptSalt(String value, String salt) {
        value = md5AndSha(value + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = value.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = value.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }



    /**
     * 验证加盐后是否和原密码一致
     * @param value 原密码
     *@return boolean true表示和原数据一致   false表示和原数据不一致
     */
    public static boolean validate(String value, String md5str) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5str.charAt(i + 2);
            cs2[i / 3] = md5str.charAt(i + 1);
        }
        String salt = new String(cs2);
        String encrypPassword = md5AndSha(value + salt);
        // 加密密码去掉最后8位数
        encrypPassword = encrypPassword.substring(0 , encrypPassword.length() - 8);
        return encrypPassword.equals(String.valueOf(cs1));
    }



    public static void main(String[] args) {
        // 原密码
        String password = "d82eda765d3cc788f9d7d9d29d68fb4d";
        // 获取加盐后的MD5值
        String password2 = Md5Utils.encryptSalt(password, "wRWi7X1W57DSqmLa");
        System.out.println("加盐后MD5：" + password2);
        System.out.println("是否是同一字符串:" + Md5Utils.validate(password, password2));
    }


}
