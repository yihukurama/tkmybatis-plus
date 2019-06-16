package com.yihukurama.tkmybatisplus.app.cache;


import java.util.Map;
import java.util.TreeMap;

/**
 * 说明： 系统缓存类
 * @author yihukurma
 * @date Created in 下午 3:09 2019/6/16 0016
 *  modified by autor in 下午 3:09 2019/6/16 0016
 */
public class AppCache {

    /**
     * 接口映射根据接口下标进行映射
     */
    public static Map<String,ServiceInfo> serviceInfoMap = new TreeMap();

}
