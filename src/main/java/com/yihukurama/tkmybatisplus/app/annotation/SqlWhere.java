package com.yihukurama.tkmybatisplus.app.annotation;

import java.lang.annotation.*;
/**
 * 说明： sql where字句注解
 * @author yihukurma
 * @date Created in 下午 3:08 2019/6/16 0016
 *  modified by autor in 下午 3:08 2019/6/16 0016
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SqlWhere {

    SqlWhereValue value();
    String proprtityName();

    public static enum SqlWhereValue{
        //sql where字句常用
        GT(" > "),
        LT(" < "),
        GTE(" >= "),
        LTE(" <= "),
        LIKE(" like "),
        IN(" in "),
        LTDATE(" <= "),
        GTDATE(" >= ");

        private  String value;

        public String getValue() {
            return value;
        }

        SqlWhereValue(String value){
            this.value = value;
        }
    }
}


