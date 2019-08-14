package com.yihukurama.tkmybatisplus.framework.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 说明： 请求体规范
 * @author yihukurma
 * @date Created in 下午 3:17 2019/6/16 0016
 *  modified by autor in 下午 3:17 2019/6/16 0016
 */
@Data
public class Request<T> {

    /**
     * 用户token;
     */
    private String token;

    private String code;

    private Integer page;

    private Integer limit;
    /**
     * 请求数据;
     */
    private T data;

}
