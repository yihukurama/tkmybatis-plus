package com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity;


import com.yihukurama.tkmybatisplus.app.annotation.SqlWhere;
import com.yihukurama.tkmybatisplus.app.constant.MagicCode;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * 说明： 数据实体基类
 * @author yihukurma
 * @date Created in 下午 3:15 2019/6/16 0016
 *  modified by autor in 下午 3:15 2019/6/16 0016
 */
@Data
public class BaseEntity {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;

    /**
     * 批量id处理字段
     */
    private List<String> ids;


    @SqlWhere(value = SqlWhere.SqlWhereValue.IN,proprtityName = MagicCode.ID)
    public List<String> getIds() {
        return ids;
    }


}
