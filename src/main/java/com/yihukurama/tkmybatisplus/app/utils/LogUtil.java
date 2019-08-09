package com.yihukurama.tkmybatisplus.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 说明： 日志工具
 * @author yihukurma
 * @date Created in 下午 3:14 2019/6/16 0016
 *  modified by autor in 下午 3:14 2019/6/16 0016
 */
public class LogUtil {

    public static void debugLog(Object o, String msg) {
        Logger logger = LoggerFactory.getLogger(o.getClass());
        logger.debug("=============>"+msg);
    }

    public static void infoLog(Object o, String msg) {
        Logger logger = LoggerFactory.getLogger(o.getClass());
        logger.info("=============>"+msg);
    }

    public static void errorLog(Object o, String msg) {
        Logger logger = LoggerFactory.getLogger(o.getClass());
        logger.error("=============>"+msg);
    }



}
