package com.yihukurama.tkmybatisplus.framework.service.businessservice;

import com.yihukurama.tkmybatisplus.framework.web.dto.Request;
import com.yihukurama.tkmybatisplus.framework.web.dto.Result;

/**
 * 说明： 基础业务接口
 * @author yihukurma
 * @date Created in 下午 3:16 2019/6/16 0016
 *  modified by autor in 下午 3:16 2019/6/16 0016
 */
public interface IBusinessService {

    /**
     * 执行业务
     * @param request
     * @return
     */
    Result doBusiness(Request request);
}
