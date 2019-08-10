package com.yihukurama.tkmybatisplus.app.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.BaseEntity;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 说明： sql查询工厂类
 * @author yihukurama
 * @date Created in 下午 3:05 2019/6/16 0016
 *  modified by autor in 下午 3:05 2019/6/16 0016
 */
@Component
public class SqlCriteriaFactory<T extends BaseEntity> {

    public Example.Criteria generaCriteria(Example.Criteria criteria, T t) throws NoSuchMethodException {

        //查询条件
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(t));
        Set<String> keys = jsonObject.keySet();
        for (String key : keys
        ) {
            String value = jsonObject.getString(key);
            String firstChar = String.valueOf(key.charAt(0));
            String getMethodName = key.replaceFirst(firstChar, firstChar.toUpperCase());
            LogUtil.debugLog(this, "get方法是=====>get" + getMethodName);
            Method getMethod = t.getClass().getMethod("get" + getMethodName);
            SqlWhere sqlWhere = getMethod.getAnnotation(SqlWhere.class);
            if (sqlWhere != null) {

                //该方法有sqllike注解，应该用like查询
                String sqlWhereValue = sqlWhere.value().getValue();
                key = sqlWhere.proprtityName();
                if(sqlWhereValue.equals(SqlWhere.SqlWhereValue.LIKE.getValue())){
                    value = "'%"+value+"%'";
                }else if(sqlWhereValue.equals(SqlWhere.SqlWhereValue.IN.getValue())){
                    value = value.replace("[","(");
                    value = value.replace("]",")");
                }else if(sqlWhereValue.equals(SqlWhere.SqlWhereValue.LTDATE.getValue())){
                    key = "DATE_FORMAT(" + sqlWhere.proprtityName() + ", '%Y-%m-%d')";
                    value = "DATE_FORMAT('"+value+"','%Y-%m-%d')";
                }else if(sqlWhereValue.equals(SqlWhere.SqlWhereValue.GTDATE.getValue())){
                    key = "DATE_FORMAT(" + sqlWhere.proprtityName() + ", '%Y-%m-%d')";
                    value = "DATE_FORMAT('"+value+"','%Y-%m-%d')";
                } else{
                    value = "'"+value+"'";
                }
                //针对createGT等范围查询需要得到对应的字段，如create
                LogUtil.debugLog(this,"条件查询的注解名是"+sqlWhere.value().name());
                LogUtil.debugLog(this,"条件查询的key名是"+ key);
                if(key.contains(sqlWhere.value().name())){
                    key = key.replace(sqlWhere.value().name(),"");
                }

                LogUtil.debugLog(this,"最后条件查询的key名是"+ key);
                criteria.andCondition(key + sqlWhereValue + value);

                continue;
            }

            try {
                t.getClass().getSuperclass().getDeclaredField(key);
                criteria.andEqualTo(key, value);
            } catch (NoSuchFieldException e) {
                LogUtil.debugLog(this,"创建查询条件时非Entity属性"+key);
            }





        }


        return criteria;
    }

}