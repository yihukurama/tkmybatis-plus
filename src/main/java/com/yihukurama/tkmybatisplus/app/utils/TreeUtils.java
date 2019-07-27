package com.yihukurama.tkmybatisplus.app.utils;

import com.yihukurama.tkmybatisplus.app.exception.TipsException;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.BaseTreeEntity;
import com.yihukurama.tkmybatisplus.framework.service.CrudServiceFactory;
import com.yihukurama.tkmybatisplus.framework.service.domainservice.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明： 树形结构工具
 * @author yihukurma
 * @date Created in 下午 3:15 2019/6/16 0016
 *  modified by autor in 下午 3:15 2019/6/16 0016
 */
@Component
public class TreeUtils {

    @Autowired
    CrudServiceFactory crudServiceFactory;

    public <T extends BaseTreeEntity> List<T> treeList(List parentEntityList, Class<T> clazz,String domainServicePath) throws TipsException {
        try {
            return treeList(parentEntityList, clazz, false,domainServicePath);
        }catch(Throwable e){
            throw new TipsException(e.getMessage(), e);
        }
    }

    /**
     * 说明：生成树型结构
     * @author: ouyaokun
     * @date: Created in 14:04 2018/5/4
     * @modified: by autor in 14:04 2018/5/4
     * @param parentEntityList 父节点list
     * @param clazz 需要转换的domain类
     * @param asyn 是否只搜索一级子节点
     * @return
     */
    public <T extends BaseTreeEntity> List<T> treeList(List parentEntityList, Class<T> clazz, Boolean asyn,String domainServicePath) throws TipsException{
        try {
            if (parentEntityList.isEmpty()) {
                return new ArrayList<>();
            }
            List<T> parentList = TransferUtils.transferEntityList2DomainList(parentEntityList, clazz);
            for (BaseTreeEntity parent : parentList) {
                if (parent == null) {
                    continue;
                }
                parent.setTitle(parent.getText());
                parent.setValue(parent.getId());
                parent.setKey(parent.getId());

                BaseTreeEntity condition = (BaseTreeEntity) clazz.newInstance();
                condition.setParentId(parent.getId());
                CrudService crudService = crudServiceFactory.createService(clazz.getSimpleName(),domainServicePath);
                List<BaseTreeEntity> children = (List) crudService.list(condition, null, null).getData();
                if (children.isEmpty()) {
                    parent.setLeaf(true);
                    continue;
                }
                parent.setLeaf(false);
                if (asyn) {
                    continue;
                }
                parent.setChildren(treeList(children, clazz,domainServicePath));

            }
            return parentList;
        }catch (Throwable e){
            throw new TipsException(e.getMessage(), e);
        }
    }

    public <T extends BaseTreeEntity> List<T> treeListByCondition(BaseTreeEntity condition, Class<T> clazz,String domainServicePath) throws TipsException {
        try {

            condition = TransferUtils.transferEntity2Domain(condition, clazz);
            CrudService crudService = crudServiceFactory.createService(clazz.getSimpleName(),domainServicePath);
            Boolean asyn = condition.getAsyn() == null ? false : condition.getAsyn();
            condition.clearTreeCondition();
            List parentEntity = (List) crudService.list(condition, null, null).getData();
            return treeList(parentEntity, clazz, asyn,domainServicePath);
        }catch (Throwable e){
            throw new TipsException(e.getMessage(), e);
        }
    }
}
