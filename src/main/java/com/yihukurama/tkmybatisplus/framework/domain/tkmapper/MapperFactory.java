package com.yihukurama.tkmybatisplus.framework.domain.tkmapper;


import com.yihukurama.tkmybatisplus.app.component.SpringBeanTools;
import com.yihukurama.tkmybatisplus.app.constant.Constant;
import com.yihukurama.tkmybatisplus.app.exception.TipsException;
import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
/**
 * 说明： mapper工厂类
 * @author yihukurma
 * @date Created in 下午 3:15 2019/6/16 0016
 *  modified by autor in 下午 3:15 2019/6/16 0016
 */
@Component
public class MapperFactory {


    public final static String ENTITY_STRING = "Entity";

    /**
     * 说明： 创建mapper
     * @author yihukurma
     * @date Created in 下午 3:16 2019/6/16 0016
     *  modified by autor in 下午 3:16 2019/6/16 0016
     */
    public Mapper createMapper(String domainName) throws TipsException {

        if (domainName.endsWith(ENTITY_STRING)) {
            domainName = domainName.replace("Entity", "");
        }
        String mapperName = domainName + "Mapper";
        LogUtil.debugLog(this, "想要获取的Mapper是:" + Constant.mapperPath + mapperName);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(Constant.mapperPath + mapperName);
            return (Mapper) SpringBeanTools.getBean(clazz);


        } catch (ClassNotFoundException e) {

        }

        throw new TipsException("无法获取有效的mapper");
    }


}
