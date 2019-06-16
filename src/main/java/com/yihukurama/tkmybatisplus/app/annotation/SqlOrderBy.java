package com.yihukurama.tkmybatisplus.app.annotation;

import java.lang.annotation.*;
/**
 * 说明：sql order by 注解
 * @author yihukurma
 * @date Created in 下午 3:07 2019/6/16 0016
 *  modified by autor in 下午 3:07 2019/6/16 0016
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SqlOrderBy {

    String value();


}


