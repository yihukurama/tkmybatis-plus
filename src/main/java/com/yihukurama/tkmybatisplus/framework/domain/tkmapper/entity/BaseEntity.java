package com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihukurama.tkmybatisplus.app.annotation.SqlWhere;
import com.yihukurama.tkmybatisplus.app.constant.MagicCode;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
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
    /**
     * 拼在单表查询后的sql
     * order by (xxx,xxx desc/asc)括号部分
     */
    @Transient
    protected String sortSql;

    /**
     *创建时间
     **/
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")@Column(name="create_date")
    private Date createDate;
    /**
     *  创建人id
     */
    @Column(name="creater_id")
    private String createrId;


    @SqlWhere(value = SqlWhere.SqlWhereValue.IN,proprtityName = MagicCode.ID)
    public List<String> getIds() {
        return ids;
    }


}
