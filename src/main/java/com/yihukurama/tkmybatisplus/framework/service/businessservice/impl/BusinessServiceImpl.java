package com.yihukurama.tkmybatisplus.framework.service.businessservice.impl;


import com.yihukurama.tkmybatisplus.app.cache.AppCache;
import com.yihukurama.tkmybatisplus.app.cache.ServiceInfo;
import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import com.yihukurama.tkmybatisplus.framework.service.businessservice.IBusinessService;
import com.yihukurama.tkmybatisplus.framework.web.dto.Request;
import com.yihukurama.tkmybatisplus.framework.web.dto.Result;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 说明： 处理业务，调用服务
 * @author yihukurma
 * @date Created in 下午 3:16 2019/6/16 0016
 *  modified by autor in 下午 3:16 2019/6/16 0016
 */
public class BusinessServiceImpl implements IBusinessService {
    @Override
    public Result doBusiness(Request request) {
        String code = request.getCode();

        ServiceInfo servicePath = AppCache.serviceInfoMap.get(code);
        if(servicePath == null){
            return Result.failed("无法找到此接口映射，请确认已初始化此接口");
        }
        String methName = servicePath.getMethName();
        LogUtil.DebugLog(this,"业务方法名是："+methName);

        try {
            String className = this.getClass().getName();
            LogUtil.DebugLog(this,"当前类是："+className);
            Method callMethod = this.getClass().getMethod(methName,Request.class);


            return (Result) callMethod.invoke(this,request);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return Result.failed("无此业务方法"+methName);
        }



    }
}
