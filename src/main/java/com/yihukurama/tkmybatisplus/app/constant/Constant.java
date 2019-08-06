package com.yihukurama.tkmybatisplus.app.constant;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * 说明： 一些约定的常量
 * @author yihukurma
 * @date Created in 下午 3:12 2019/6/16 0016
 *  modified by autor in 下午 3:12 2019/6/16 0016
 */
public final class Constant {


    /**
     * 数据库中逻辑删除字段
     */
    public static String DEL_STATUS = "is_delete";
    /**
     * 逻辑删除
     */
    public static final Boolean DEL_STATUS_1 = TRUE;
    /**
     * 逻辑未删除
     */
    public static final Boolean DEL_STATUS_0 = FALSE;
    /**
     * //用于加密的密钥不同系统不同，避免撞库
     */
    public static String encryptKey = "encryptKey";
    /**
     * //token密钥
     */
    public static String JWTSECRET = "JWTSECRET";

    /**
     * admin mapper包路径
     */
    public static String mapperPath = "";

}
