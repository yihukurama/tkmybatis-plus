package com.yihukurama.tkmybatisplus.app.utils;

import java.util.Collection;

/**
 * 说明： 判空工具
 * @author yihukurma
 * @date Created in 下午 3:14 2019/6/16 0016
 *  modified by autor in 下午 3:14 2019/6/16 0016
 */
public class EmptyUtil {

    /**
     * 说明： 是否有成员变量是否为空，用于接口参数判断
     * @author dengshuai
     * @date Created in 10:35 2018/5/30
     * @modified by autor in 10:35 2018/5/30
     */
    public static boolean hasEmptyValue(Object o){





        return false;
    }
    /**
     * 功能描述:字符串判空
     *
     * @param str
     * @return
     * @Author:dengshuai
     * @Date:2017年10月16日 上午10:31:57
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:校验数组是否为空;
     *
     * @param array 数组对象
     * @return true, 数组为空;false,数组不为空
     * @Author:liujun
     * @Date:2016年12月18日 下午1:43:16
     */
    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length < 1) {
            return true;
        }
        return false;
    }

    /**
     * 功能描述:校验集合对象是否为空;
     *
     * @param collection Collection对象
     * @return true, Collection对象为空;false,Collection对象不为空
     * @Author:liujun
     * @Date:2016年12月18日 下午1:45:40
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        if (collection == null || collection.size() < 1) {
            return true;
        }
        return false;
    }

}
