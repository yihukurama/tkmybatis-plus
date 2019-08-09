package com.yihukurama.tkmybatisplus.app.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 说明： 加解密工具
 * @author yihukurma
 * @date Created in 下午 3:14 2019/6/16 0016
 *  modified by autor in 下午 3:14 2019/6/16 0016
 */
public class EncrUtil {

    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 说明： base64加密
     * @author dengshuai
     * @date Created in 13:46 2019/1/17
     * @modified by autor in 13:46 2019/1/17
     */
    public static String encryptBASE64(String inputStr) {
        String value = "";
        try {
            byte[] key = inputStr.getBytes();
            value = encoder.encodeBuffer(key);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.ErrorLog(EncrUtil.class,"base64解密出错");
            return "";
        }
        return value;
    }

    /**
     * 说明：base64解密
     * @author dengshuai
     * @date Created in 13:44 2019/1/17
     * @modified by autor in 13:44 2019/1/17
     */
    public static String decryptBASE64(String outputStr) {
        String value = "";
        try {
            byte[] key = decoder.decodeBuffer(outputStr);
            value = new String(key);
        } catch (Exception e) {
            LogUtil.ErrorLog(EncrUtil.class,"base64解密出错");
            return "";
        }
        return value;
    }


    /**
     * 说明： utf8 Encode
     * @author dengshuai
     * @date Created in 15:33 2019/1/13
     * @modified by autor in 15:33 2019/1/13
     */
    public static String encodeUTF8(String source){
        String result = source;
        try {
            result = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    /**
     * 功能描述:对字符串脱敏处理 若字符串长度不满足begin和end则不脱敏返回
     *
     * @param str
     * @param begin
     * @param end
     * @return
     * @Author:dengshuai
     * @Date:2017年10月16日 上午10:44:38
     */
    public static String desenString(String str, int begin, int end) {
        if (EmptyUtil.isEmpty(str)) {
            return str;
        }
        StringBuilder strSB = new StringBuilder(str);
        int length = strSB.length();

        for (int i = 0; i < length; i++) {
            if (i >= begin && i < end) {
                strSB.replace(i, i + 1, "*");
            }
        }
        return strSB.toString();
    }

    private final static String[] STR_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return STR_DIGITS[iD1] + STR_DIGITS[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    /**
     * 功能描述:MD5生成长度为16位字符的随机串;
     *
     * @return 16位长度的随机串
     * @Author:liujun
     * @Date:2017年2月28日 下午4:15:46
     */
    public static String gen16BitMD5String() {
        String sourceString = UUID.randomUUID().toString().replaceAll("-", ""); // 原字符串;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(sourceString.getBytes());
            byte b[] = messageDigest.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public static String SHA1EncodeSHA1(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static final String SKEY = "tjhstrhdrthdgaku";
    private static final String IV_PARAMETER = "emtrhYPUIoyjdgki";


    // 加密  
    public static String AESEncrypt(String sSrc) {
        if (EmptyUtil.isEmpty(sSrc)) {
            return null;
        }
        String result = "";
        try {
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = SKEY.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            result = new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            result = sSrc;
        }
        // 此处使用BASE64做转码。  
        return result;

    }

    // 解密  
    public static String AESDecrypt(String sSrc) {
        if (EmptyUtil.isEmpty(sSrc)) {
            return null;
        }
        try {
            byte[] raw = SKEY.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密  
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return sSrc;
        }
    }

    // 加密
    public static String EncryptAES128(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        //"算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return encoder.encodeBuffer(encrypted);
    }

    // 解密
    public static String DecryptAES128(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            //先用base64解密
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }
}
