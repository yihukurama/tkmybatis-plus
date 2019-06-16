package com.yihukurama.tkmybatisplus.app.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明： 框架数据转化
 * @author yihukurma
 * @date Created in 下午 3:15 2019/6/16 0016
 *  modified by autor in 下午 3:15 2019/6/16 0016
 */
public class TransferUtils {


    /**
     * 功能描述:把数据实体类转化为业务实体类
     *
     * @param entity 数据实体对象
     * @param clazz  业务实体对象
     * @return 数据实体转化后的业务实体
     * @Author:dengshuai
     * @Date:2017年1月20日 下午2:09:15
     */
    public static <B, S extends B> S transferEntity2Domain(B entity, Class<S> clazz) {

        if (entity == null) {
            return null;
        }
        String jsonString = JSON.toJSONString(entity);
        S domain = JSON.parseObject(jsonString, clazz);

        return domain;

    }

    /**
     * 功能描述:把数据实体列表转化为业务实体列表
     *
     * @param entityList
     * @param clazz
     * @return 业务实体列表，不会为null
     * @Author:dengshuai
     * @Date:2017年2月4日 上午10:42:40
     */
    public static <B, S extends B> List<S> transferEntityList2DomainList(List<B> entityList, Class<S> clazz) {

        List<S> sList = new ArrayList<>();
        if (entityList == null) {
            return sList;
        }
        List<S> list = JSON.parseArray(JSON.toJSONString(entityList), clazz);
        if (list == null) {
            return sList;
        }
        return list;
    }


    /**
     * 功能描述:把业务实体列表转化为数据实体列表
     *
     * @param domainList
     * @param clazz
     * @return
     * @Author:dengshuai
     * @Date:2017年2月4日 上午10:48:00
     */
    public static <B, S extends B> List<B> transferDomainList2EntityList(List<S> domainList, Class<?> clazz) {

        List<B> bList = new ArrayList<>();
        if (domainList == null) {
            return bList;
        }
        for (B b : domainList) {
            String jsonString = JSON.toJSONString(b);
            B entity = (B) JSON.parseObject(jsonString, clazz);
            bList.add(entity);
        }


        return bList;

    }


}
