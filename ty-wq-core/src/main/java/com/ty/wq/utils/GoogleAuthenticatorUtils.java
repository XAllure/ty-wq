package com.ty.wq.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Hashtable;

/**
 * @program: manager
 * @description: google二次验证工具类
 * @author:
 * @create: 2019-04-25 13:59
 */
@Component
public class GoogleAuthenticatorUtils {
    /**
     * 时间前后偏移量
     */
    private static int timeExcursion = 3;


    private static String active;

    @Value("${custom.googleVerifyCode.timeExcursion}")
    public void setTimeExcursion(String timeExcursion) {
        GoogleAuthenticatorUtils.timeExcursion = Integer.parseInt(timeExcursion);
    }

    @Value("${spring.profiles.active}")
    public void setActive(String active){
        GoogleAuthenticatorUtils.active = active;
    }

    /**
     * 随机生成一个密钥
     *
     * @return
     */
    public static String createSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secretKey = base32.encodeToString(bytes);
        return secretKey.toLowerCase();
    }

    /**
     * 根据密钥获取验证码
     *
     * @param secretKey 密钥
     * @param time
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static String getTOTP(String secretKey, long time)
            throws InvalidKeyException, NoSuchAlgorithmException {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey.toUpperCase());
        String hexKey = Hex.encodeHexString(bytes);
        String hexTime = Long.toHexString(time);
        return TOTPUtils.generateTOTP(hexKey, hexTime, "6");
    }

    /**
     * 生成 Google Authenticator 二维码所需信息，Google Authenticator
     * 约定的二维码信息格式:otpauth://totp/{issuer}:{account}?secret={secret}&issuer={issuer}，参数需要url 编码 +
     * 号需要替换成 %20
     *
     * @param secret  密钥 使用 createSecretKey 方法生成
     * @param account 用户账户 如: example@domain.com 138XXXXXXXX
     * @param issuer  服务名称 如:Google Github 印象笔记
     * @return
     */
    public static String createGoogleAuthQRCodeData(String secret, String account, String issuer) {
        String qrCodeData = "otpauth://totp/%s?secret=%s&issuer=%s";
        try {
            return String.format(
                    qrCodeData,
                    URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20"),
                    URLEncoder.encode(secret, "UTF-8").replace("+", "%20"),
                    URLEncoder.encode(issuer, "UTF-8").replace("+", "%20"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 校验方法
     *
     * @param secretKey 密钥
     * @param code      用户输入的 TOTP 验证码
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public static boolean verify(String secretKey, String code)
            throws InvalidKeyException, NoSuchAlgorithmException {
        if ("dev".equals(active)){
            if ("123456".equals(code)) return true;
            return false;
        }
        long time = System.currentTimeMillis() / 1000 / 30;
        for (int i = -timeExcursion; i <= timeExcursion; i++) {
            String totp = getTOTP(secretKey, time + i);
            if (code.equals(totp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将二维码图片输出到一个流中
     *
     * @param content 二维码内容
     * @param stream  输出流
     * @param width   宽
     * @param height  高
     * @throws WriterException
     * @throws IOException
     */
    public static void writeToStream(String content, OutputStream stream, int width, int height)
            throws WriterException, IOException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        String format = "png";
        BitMatrix bitMatrix =
                new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
    }

    /**
     * 生成二维码
     * @param secretKey
     * @param response
     * @throws IOException
     * @throws WriterException
     */
    public static void createQrCode(String secretKey, HttpServletResponse response) throws IOException, WriterException {
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("application/json; charset=utf-8");
        response.setContentType("image/jpeg");
        String qrCode = GoogleAuthenticatorUtils.createGoogleAuthQRCodeData(secretKey, "qdd@admin.com", "Google");
        ServletOutputStream stream = response.getOutputStream();
        GoogleAuthenticatorUtils.writeToStream(qrCode, stream, 90, 90);
    }

}
