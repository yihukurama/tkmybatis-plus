package com.yihukurama.tkmybatisplus.app.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 说明： 信息工具类
 * @author dengshuai
 * @date Created in 17:27 2019/2/28
 * @modified by autor in 17:27 2019/2/28
 */
public class InfoUtil {



    public final static String UNKNOW = "unknown";
    public final static String HEADER_X = "x-forwarded-for";
    public final static String HEADER_PCI = "Proxy-Client-IP";
    public final static String HEADER_WPCI = "WL-Proxy-Client-IP";
    public final static String LOCALHOST_127 = "127.0.0.1";
    public final static int IP_LEN = 15;
    public final static String DOT = ",";
    public final static int IP_MAX_LEN = 100;
    /**
     * 功能描述:获取IP地址
     *
     * @param request
     * @return
     * @Author:Jieyq
     * @Date:2016年10月14日 下午3:21:16
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader(HEADER_X);
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOW.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HEADER_PCI);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOW.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HEADER_WPCI);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOW.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_127.equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > IP_LEN) {
            // = 15
            if (ipAddress.indexOf(DOT) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(DOT));
            }
        }
        //地址过长截取前100位
        if (ipAddress != null && ipAddress.length() > IP_MAX_LEN) {
            ipAddress = ipAddress.substring(0, IP_MAX_LEN);
        }
        return ipAddress;
    }
}
