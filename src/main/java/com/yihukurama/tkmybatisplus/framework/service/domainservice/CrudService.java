package com.yihukurama.tkmybatisplus.framework.service.domainservice;

import com.alibaba.fastjson.JSON;
import com.yihukurama.tkmybatisplus.app.annotation.SqlCriteriaFactory;
import com.yihukurama.tkmybatisplus.app.annotation.SqlOrderBy;
import com.yihukurama.tkmybatisplus.app.constant.Constant;
import com.yihukurama.tkmybatisplus.app.constant.MagicCode;
import com.yihukurama.tkmybatisplus.app.exception.TipsException;
import com.yihukurama.tkmybatisplus.app.utils.EmptyUtil;
import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.MapperFactory;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.BaseEntity;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.admin.UserEntity;
import com.yihukurama.tkmybatisplus.framework.web.dto.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
 * 说明： 领域服务基础增删查改
 * @author yihukurma
 * @date Created in 下午 3:16 2019/6/16 0016
 *  modified by autor in 下午 3:16 2019/6/16 0016
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
        t.setId(UUID.randomUUID().toString().replace("-",""));
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
            LogUtil.ErrorLog(this, e.getMessage());
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
            LogUtil.ErrorLog(this, e.getMessage());
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
            setMethod = t.getClass().getMethod(MagicCode.SETDELSTATUS, Integer.class);
        }catch (NoSuchMethodException e) {
            System.out.println("没有这样的方法1！");
        }
        if(setMethod == null) {
            return;
        }

        Method getMethod = null;
        try {
            getMethod = t.getClass().getMethod(MagicCode.GETDELSTATUS);
        }catch (NoSuchMethodException e){
            System.out.println("没有这样的方法！");
        }

        try {
            if (getMethod != null && getMethod.invoke(t) != null) {
                return;
            }


            setMethod.invoke(t, 0);
        }catch (Exception e){
            LogUtil.ErrorLog(this, e.getMessage());
        }
    }

    private void autoSetCommonField(T t, String userId){
        changeValueByFieldIfNull(t, MagicCode.OPERATEDATEFIELD, new Date(), Date.class);
        if(EmptyUtil.isEmpty(userId)){
            return;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);


        if(EmptyUtil.isEmpty((String) getValueByField(t, MagicCode.IDFIELD))){
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
    public Result list(T t, Integer page, Integer limit) throws TipsException, NoSuchMethodException {
        Mapper<T> mapper = mapperFactory.createMapper(t.getClass().getSimpleName());
        List<T> tList = null;
        Result result = null;

        LogUtil.DebugLog(this, "查询条件是：" + JSON.toJSONString(t));
        //给查询赋值
        Condition condition = new Condition(t.getClass().getSuperclass());
        Example.Criteria criteria = condition.createCriteria();
        criteria = sqlCriteriaFactory.GeneraCriteria(criteria,t);

        //排序
        boolean hasDelStatus = false;
        Method[] methods = t.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();

            try {
                Method getMethod = getMethod = t.getClass().getMethod(methodName);
                SqlOrderBy sqlOrderBy = getMethod.getAnnotation(SqlOrderBy.class);
                if(sqlOrderBy!=null){

                    condition.setOrderByClause(sqlOrderBy.value());
                }
            }catch (Exception e){

            }

            if (methodName.equals(MagicCode.SETDELSTATUS)){
                hasDelStatus = true;

            }

        }
        if(hasDelStatus){
            t.getClass().getMethod(MagicCode.SETDELSTATUS, Integer.class);
            criteria.andEqualTo(Constant.DEL_STATUS, Constant.DEL_STATUS_0);
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
    }

    ;

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
            method = t.getClass().getMethod(MagicCode.SETDELSTATUS, Integer.class);
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
            Method method = t.getClass().getMethod(MagicCode.SETDELSTATUS, Integer.class);

            T t1 = null;
            try {
                t1 = (T)t.getClass().newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
                LogUtil.ErrorLog(this,"无法生成t的实例");
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
