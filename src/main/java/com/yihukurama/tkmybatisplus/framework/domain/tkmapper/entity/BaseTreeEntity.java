package com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * 说明： 树形结构基类
 * @author yihukurma
 * @date Created in 下午 3:15 2019/6/16 0016
 *  modified by autor in 下午 3:15 2019/6/16 0016
 */
@Data
public abstract class BaseTreeEntity extends BaseEntity {

    public static final String ROOT = "root";

    @Transient
    private String title;
    @Transient
    private String value ;
    @Transient
    private String key;
    @Transient
    private String text;
    /**
     * 所有子节点
     */
    @Transient
    private List children;

    /**
     * 是否需要异步获取
     */
    @Transient
    private Boolean asyn;

    /**
     * 是否叶子节点
     */
    @Transient
    private Boolean leaf;
    /**
     * 是否根节点
     */
    @Transient
    private Boolean root;
    /**
     * 父级id
     */
    @Transient
    private String parentId;


    public void clearTreeCondition(){
        children = null;
        asyn = null;
        leaf = null;
    }
}
