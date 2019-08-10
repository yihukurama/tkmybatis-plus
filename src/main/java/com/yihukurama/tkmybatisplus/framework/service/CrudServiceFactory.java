package com.yihukurama.tkmybatisplus.framework.service;

import com.yihukurama.tkmybatisplus.app.component.SpringBeanTools;
import com.yihukurama.tkmybatisplus.app.exception.TipsException;
import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import com.yihukurama.tkmybatisplus.framework.service.domainservice.CrudService;
import org.springframework.stereotype.Component;

/**
 * 说明： 领域服务工厂类
 * @author yihukurma
 * @date Created in 下午 3:17 2019/6/16 0016
 *  modified by autor in 下午 3:17 2019/6/16 0016
 */
@Component
public class CrudServiceFactory {


    public final static String ENTITY_STRING = "Entity";

    /**
     * 说明： 创建领域服务
     * @author yihukurma
     * @date Created in 下午 3:17 2019/6/16 0016
     *  modified by autor in 下午 3:17 2019/6/16 0016
     */
    public CrudService createService(String domainName,String domainservicePath) throws TipsException {

        if (domainName.endsWith(ENTITY_STRING)) {
            domainName = domainName.replace("Entity", "");
        }
        String domainserviceName = domainName + "Service";
        LogUtil.debugLog(this, "想要获取的Service是:" + domainservicePath + domainserviceName);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(domainservicePath + domainserviceName);
            return (CrudService) SpringBeanTools.getBean(clazz);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new TipsException("无法获得有效的service");
    }
}
