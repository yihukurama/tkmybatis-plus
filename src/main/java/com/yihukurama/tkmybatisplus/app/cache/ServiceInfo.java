package com.yihukurama.tkmybatisplus.app.cache;

import lombok.Data;

/**
 * 说明： 服务接口信息类
 * @author yihukurma
 * @date Created in 下午 3:10 2019/6/16 0016
 *  modified by autor in 下午 3:10 2019/6/16 0016
 */
@Data
public class ServiceInfo {
    public ServiceInfo(){

    }
    /**
     * 业务类引用路径
     */
    String serviceName;
    /**
     * 业务方法名
     */
    String methName;

    /**
     * 业务编号
     */
    String code;
    public String getServiceName() {
        return serviceName;
    }

    public ServiceInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getMethName() {
        return methName;
    }

    public ServiceInfo setMethName(String methName) {
        this.methName = methName;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ServiceInfo setCode(String code) {
        this.code = code;
        return this;
    }
}
