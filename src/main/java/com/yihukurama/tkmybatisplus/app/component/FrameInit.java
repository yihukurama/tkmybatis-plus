package com.yihukurama.tkmybatisplus.app.component;

import com.yihukurama.tkmybatisplus.app.constant.Constant;

/**
 * 说明： 框架初始化类
 * @author yihukurma
 * @date Created in 下午 3:12 2019/6/16 0016
 *  modified by autor in 下午 3:12 2019/6/16 0016
 */
public class FrameInit {


    /**
     * 说明： 对domainService和mapper和domain初始化
     * @author yihukurma
     * @date Created in 下午 3:11 2019/6/16 0016
     *  modified by autor in 下午 3:11 2019/6/16 0016
     */
    public static void init(String mapperPath){

        /**
         * admin mapper包路径
         */
        Constant.mapperPath = mapperPath;

    }
}
