package com.yihukurama.tkmybatisplus.app.component;

import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 说明： 保存spring上下文，方便在非spring管理的类中获取容器中的bean
 * @author yihukurma
 * @date Created in 下午 3:12 2019/6/16 0016
 *  modified by autor in 下午 3:12 2019/6/16 0016
 */
@Configuration
public class SpringBeanTools implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static Object getBean(Class classname) {
        try {
            Object _restTemplate = applicationContext.getBean(classname);
            return _restTemplate;
        } catch (Exception e) {
            LogUtil.ErrorLog(SpringBeanTools.class, "使用springbeantools获取托管对象失败:" + classname);
            return "";
        }
    }

    /**
     * 说明： 通过bean别名获取bean，bean别名写在对应的configure对象里
     * @author: ouyaokun
     * @date: Created in 17:24 2018/5/29
     * @modified: by autor in 17:24 2018/5/29
     *
     */
    public static Object getBeanByName(String beanName) {
        try {
            Object _restTemplate = applicationContext.getBean(beanName);
            return _restTemplate;
        } catch (Exception e) {
            LogUtil.ErrorLog(SpringBeanTools.class, "使用springbeantools获取托管对象失败:" + beanName);
            return "";
        }
    }

    public static void setApplicationContext1(ApplicationContext context) {
        applicationContext = context;
    }
}
