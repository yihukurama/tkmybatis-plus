package com.yihukurama.tkmybatisplus.framework.service.domainservice;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihukurama.tkmybatisplus.app.annotation.SqlCriteriaFactory;
import com.yihukurama.tkmybatisplus.app.constant.Constant;
import com.yihukurama.tkmybatisplus.app.constant.MagicCode;
import com.yihukurama.tkmybatisplus.app.exception.TipsException;
import com.yihukurama.tkmybatisplus.app.utils.EmptyUtil;
import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.MapperFactory;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.BaseEntity;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.UserEntity;
import com.yihukurama.tkmybatisplus.framework.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 说明： 领域服务的基础增删查改
 *
 * @author: dengshuai
 * @date: Created in 11:37 2018/4/2
 * modified: by autor in 11:37 2018/4/2
 */
public class CrudService<T extends BaseEntity> {

    @Autowired
    MapperFactory mapperFactory;

    @Autowired
    SqlCriteriaFactory sqlCriteriaFactory;


    /**
     * 说明： 依据条件加载一条数据
     *
     * @author dengshuai
     * @date Created in 15:29 2018/4/10
     * @modified by autor in 15:29 2018/4/10
     */
    public T create(T t) throws TipsException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());

        String createrId = (String) getValueByField(t, MagicCode.CREATERIDFIELD);
        autoSetCommonField(t, createrId);
        //创建时id为空时才生成uuid
        if(EmptyUtil.isEmpty(t.getId())){
            t.setId(UUID.randomUUID().toString().replace("-",""));
        }
        int row = mapper.insertSelective(t);
        if (row == 1) {
            return mapper.selectByPrimaryKey(t);
        }
        return null;
    }

    /**
     * 说明： 根据条件增加一批数据
     * @author: ouyaokun
     * @date: Created in 14:46 2018/5/2
     * @modified: by autor in 14:46 2018/5/2
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int creates(List<T> list) throws TipsException {
        int sum = 0;
        for(T t : list) {
            Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());

            String createrId = (String) getValueByField(t, MagicCode.CREATERIDFIELD);
            autoSetCommonField(t, createrId);

            int row = mapper.insertSelective(t);
            if (row == 1) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * 说明： 依据条件加载一条数据
     *
     * @author dengshuai
     * @date Created in 15:29 2018/4/10
     * @modified by autor in 15:29 2018/4/10
     */
    public T loadOneByCondition(T t) throws TipsException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        changeDelStatusIfNull(t);
        return mapper.selectOne(t);
    }

    /**
     * 说明： 加载单条数据
     *
     * @author: dengshuai
     * @date: Created in 11:38 2018/4/2
     * @modified: by autor in 11:38 2018/4/2
     */
    public T load(T t) throws TipsException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        return mapper.selectByPrimaryKey(t);
    }

    /**
     * 说明： 根据主键进行更新
     *
     * @author: dengshuai
     * @date: Created in 15:43 2018/4/9
     * @modified: by autor in 15:43 2018/4/9
     */
    public T update(T t) throws TipsException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        T loadObject = mapper.selectByPrimaryKey(t.getId());
        if(loadObject == null){
            throw new TipsException("系统中无此数据");
        }
        t.setCreateDate(loadObject.getCreateDate());
        String operatorId = (String) getValueByField(t, MagicCode.OPERATORIDFIELD);
        autoSetCommonField(t, operatorId);

        int updateRow = mapper.updateByPrimaryKeySelective(t);
        if (updateRow == 1) {
            t = mapper.selectByPrimaryKey(t);
            return t;
        }

        return null;
    }

    private Object getValueByField(T t, String field){
        if(EmptyUtil.isEmpty(field)){
            return null;
        }

        String fieldFragments = field.substring(0, 1).toUpperCase() + field.substring(1);

        Method getMethod = null;
        try {
            getMethod = t.getClass().getMethod("get" + fieldFragments);
        }catch (NoSuchMethodException e){

        }

        if(getMethod == null){
            return null;
        }

        try {
            return getMethod.invoke(t);
        }catch (Exception e){
            LogUtil.debugLog(this, e.getMessage());
        }

        return null;
    }

    private void changeValueByFieldIfNull(T t, String field, Object defaultValue, Class type){
        if(EmptyUtil.isEmpty(field)){
            return;
        }

        String fieldFragments = field.substring(0, 1).toUpperCase() + field.substring(1);

        Method setMethod = null;
        try{
            setMethod = t.getClass().getMethod("set" + fieldFragments, type);
        }catch (NoSuchMethodException e) {

        }
        if(setMethod == null) {
            return;
        }

        Method getMethod = null;
        try {
            getMethod = t.getClass().getMethod("get" + fieldFragments);
        }catch (NoSuchMethodException e){

        }
        try {
            if(getMethod != null && getMethod.invoke(t) != null){
                return;
            }


            setMethod.invoke(t, defaultValue);
        }catch (Exception e){
            LogUtil.debugLog(this, e.getMessage());
        }

    }

    /**
     * 说明： 加载列表数据不分页
     *
     * @author: dengshuai
     * @date: Created in 11:38 2018/4/2
     * @modified: by autor in 11:38 2018/4/2
     */
    public List<T> list(T t) throws TipsException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        changeDelStatusIfNull(t);
        return mapper.select(t);

    }

    private void changeDelStatusIfNull(Object t){
        Method setMethod = null;
        try{
            setMethod = t.getClass().getMethod(MagicCode.SETDELSTATUS, Boolean.class);
        }catch (NoSuchMethodException e) {
            LogUtil.debugLog(this,"无法自动setIsDelete,不需要根据is_delete查询");
        }
        if(setMethod == null) {
            return;
        }

        Method getMethod = null;
        try {
            getMethod = t.getClass().getMethod(MagicCode.GETDELSTATUS);
        }catch (NoSuchMethodException e){
            LogUtil.debugLog(this,"无法获得getIsDelete,不需要根据is_delete查询");
        }

        try {
            if (getMethod != null && getMethod.invoke(t) != null) {
                return;
            }


            setMethod.invoke(t, Constant.DEL_STATUS_0);
        }catch (Exception e){
            LogUtil.debugLog(this, e.getMessage());
        }
    }

    private void autoSetCommonField(T t, String userId){
        changeValueByFieldIfNull(t, MagicCode.OPERATEDATEFIELD, new Date(), Date.class);
        if(EmptyUtil.isEmpty(userId)){
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);


        if(EmptyUtil.isEmpty(t.getId())){
            changeValueByFieldIfNull(t, MagicCode.CREATERIDFIELD, userId, String.class);
        }

        changeValueByFieldIfNull(t, MagicCode.OPERATORIDFIELD, userId, String.class);
    }

    /**
     * 说明： 加载列表数据前端调用分页
     *
     * @author: dengshuai
     * @date: Created in 11:38 2018/4/2
     * @modified: by autor in 11:38 2018/4/2
     */
    public Result list(T t, Integer page, Integer limit) throws TipsException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        List<T> tList;
        Result result;

        //是Entity
        if(t.getClass().getSimpleName().contains(MagicCode.ENTITY)){
            throw new TipsException("该list接口仅允许领域对象使用");
        }
        LogUtil.debugLog(this, "查询条件是：" + JSON.toJSONString(t));
        //给查询赋值
        LogUtil.debugLog(this,"当前查询业务类是"+t.getClass().getName());
        Condition condition = new Condition(t.getClass().getSuperclass());
        Example.Criteria criteria = condition.createCriteria();
        changeDelStatusIfNull(t);
        try {
            criteria = sqlCriteriaFactory.generaCriteria(criteria,t);
        } catch (NoSuchMethodException e) {
            throw new TipsException("sqlCriteriaFactory异常");
        }
        boolean hasIndexOrder = false;

        //排序
        Method[] methods = t.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();
            LogUtil.debugLog(this,methodName);
            if(methodName.equals(MagicCode.SETINDEXORDER)){
                hasIndexOrder = true;
            }
        }

        //排序sql不为空
        if(!EmptyUtil.isEmpty(t.getSortSql())){
            condition.setOrderByClause(t.getSortSql());
        }else{
            //对indexOrder排序
            if(hasIndexOrder){
                condition.setOrderByClause(MagicCode.INDEXORDER+"," + MagicCode.CREATEDATE+" desc");
            }else{
                condition.setOrderByClause(MagicCode.CREATEDATE+" desc");
            }
        }

        if (page != null && limit != null) {
            PageHelper.startPage(page, limit);
            tList = mapper.selectByExample(condition);
            PageInfo<T> pageInfo = new PageInfo<T>(tList);
            result = Result.successed(tList);
            result.setTotal(pageInfo.getTotal());

        } else {
            tList = mapper.selectByExample(condition);
            result = Result.successed(tList);

        }
        return result;
    };

    /**
     * 说明： 根据主键删除单条数据
     *
     * @author dengshuai
     * @date Created in 15:31 2018/4/10
     * @modified by autor in 15:31 2018/4/10
     */
    public int remove(T t) throws TipsException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        int row = 0;
        Method method = null;

        try {
            method = t.getClass().getMethod(MagicCode.SETDELSTATUS, Boolean.class);
        } catch (NoSuchMethodException | SecurityException e) {
            row = mapper.deleteByPrimaryKey(t);
            return row;
        }


        try {
            T t1 = (T)t.getClass().newInstance();
            t1.setId(t.getId());

            method.invoke(t1, Constant.DEL_STATUS_1);

            String operatorId = (String) getValueByField(t, MagicCode.OPERATORIDFIELD);
            autoSetCommonField(t1, operatorId);
            Object o = mapper.updateByPrimaryKeySelective(t1);
            if (o != null) {
                row = 1;
            }
        } catch (InstantiationException|IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            row = 0;
            e.printStackTrace();
        }

        return row;
    }

    private <T> T newTclass(Class<T> clazz) throws InstantiationException, IllegalAccessException{
        T a=clazz.newInstance();
        return a;

    }
    /**
     * 说明： 根据主键批量删除多条数据
     *
     * @author dengshuai
     * @date Created in 15:31 2018/4/10
     * @modified by autor in 15:31 2018/4/10
     */
    @Transactional(rollbackFor = Exception.class)
    public int removes(T t) throws TipsException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        int row = 0;
        List<String> ids = t.getIds();

        boolean hasDelStatus = false;
        Method[] methods = t.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();
            if (methodName.equals(MagicCode.SETDELSTATUS)) {
                hasDelStatus = true;
                break;
            }
        }

        String operatorId = (String) getValueByField(t, MagicCode.OPERATORIDFIELD);
        if (hasDelStatus) {
            Method method = t.getClass().getMethod(MagicCode.SETDELSTATUS, Boolean.class);

            T t1 = null;
            try {
                t1 = (T)t.getClass().newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
                LogUtil.debugLog(this,"无法生成t的实例");
            }
            t1.setId(t.getId());

            method.invoke(t1, Constant.DEL_STATUS_1);
            for (String id : ids) {
                t1.setId(id);
                autoSetCommonField(t1, operatorId);
                Object o = mapper.updateByPrimaryKeySelective(t1);
                if (o != null) {
                    row++;
                }
            }
        } else {
            for (String id : ids) {
                t.setId(id);
                int deleteRow = mapper.deleteByPrimaryKey(t);
                row += deleteRow;
            }
        }

        return row;
    }

}
