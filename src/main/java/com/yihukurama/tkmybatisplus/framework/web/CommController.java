package com.yihukurama.tkmybatisplus.framework.web;

import com.alibaba.fastjson.JSON;
import com.yihukurama.tkmybatisplus.app.cache.RedisUtils;
import com.yihukurama.tkmybatisplus.app.constant.Constant;
import com.yihukurama.tkmybatisplus.app.constant.MagicCode;
import com.yihukurama.tkmybatisplus.app.exception.TipsException;
import com.yihukurama.tkmybatisplus.app.utils.EmptyUtil;
import com.yihukurama.tkmybatisplus.app.utils.LogUtil;
import com.yihukurama.tkmybatisplus.app.utils.TreeUtils;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.BaseEntity;
import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.BaseTreeEntity;
import com.yihukurama.tkmybatisplus.framework.service.CrudServiceFactory;
import com.yihukurama.tkmybatisplus.framework.service.domainservice.CrudService;
import com.yihukurama.tkmybatisplus.framework.web.dto.Request;
import com.yihukurama.tkmybatisplus.framework.web.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
/**
 * 说明： 通用控制器基类
 * @author yihukurma
 * @date Created in 下午 3:18 2019/6/16 0016
 *  modified by autor in 下午 3:18 2019/6/16 0016
 */
public class CommController {


    @Autowired
    CrudServiceFactory crudServiceFactory;
    @Autowired
    TreeUtils treeUtils;
    @Autowired
    RedisUtils redisUtils;

    protected String packageD = "";
    private void recordCreator(Object obj, String token) throws TipsException {

        String userId = (String) redisUtils.get(token+ Constant.encryptKey);

        if(EmptyUtil.isEmpty(userId)){
            return;
        }
        Method setCreaterIdMethod = null;
        try {
            setCreaterIdMethod = obj.getClass().getMethod(MagicCode.SETCREATERID, String.class);
        }catch (NoSuchMethodException e){

        }


        try {
            if(setCreaterIdMethod != null){
                setCreaterIdMethod.invoke(obj, userId);
            }
        }catch (Throwable e){
            throw new TipsException(e.getMessage(), e);
        }

    }

    private void recordOperator(Object obj, String token) throws TipsException {
        String userId = (String) redisUtils.get(token+ Constant.encryptKey);

        if(EmptyUtil.isEmpty(userId)){
            return;
        }
        Method setOperatorIdMethod = null;
        Method setOperateDateMethod = null;
        try {
            setOperatorIdMethod = obj.getClass().getMethod(MagicCode.SETOPERATORID, String.class);
            setOperateDateMethod = obj.getClass().getMethod(MagicCode.SETOPERATEDATE, Date.class);

        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }

        try {
            if(setOperatorIdMethod != null){
                setOperatorIdMethod.invoke(obj, userId);
            }
            if(setOperateDateMethod != null){
                setOperateDateMethod.invoke(obj, new Date());
            }

        }catch (Throwable e){
            throw new TipsException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{domain}/create", method = RequestMethod.POST)
    public Result create(@RequestBody Request request, @PathVariable String domain) throws TipsException {

        String realDomain = transferUrlDomainToDomainName(domain);

        String classPackage = packageD + realDomain;
        LogUtil.DebugLog(this,"实体对象路径是"+classPackage);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此业务实体，无法create");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);

        System.out.println(tt);
        CrudService crudService = crudServiceFactory.createService(realDomain);
        recordCreator(tt, request.getToken());
        recordOperator(tt, request.getToken());
        Object o = crudService.create(tt);
        if (o == null) {
            return Result.failed("创建失败");
        }

        return Result.successed(o);
    }

    @RequestMapping(value = "/{domain}/update", method = RequestMethod.POST)
    public Result update(@RequestBody Request request, @PathVariable String domain) throws TipsException {

        String realDomain = transferUrlDomainToDomainName(domain);

        String classPackage = packageD + realDomain;

        Class<?> clazz = null;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此业务实体，无法update");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);

        CrudService crudService = crudServiceFactory.createService(realDomain);
        recordOperator(tt, request.getToken());
        Object o = crudService.update(tt);
        if (o == null) {
            return Result.failed("更新失败");
        }

        return Result.successed(o);
    }

    @RequestMapping(value = "/{domain}/load", method = RequestMethod.POST)
    public Result load(@RequestBody Request request, @PathVariable String domain) throws TipsException {

        String realDomain = transferUrlDomainToDomainName(domain);

        String classPackage = packageD + realDomain;

        Class<?> clazz = null;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此业务实体，无法load");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);

        CrudService crudService = crudServiceFactory.createService(realDomain);
        Object o = crudService.load(tt);


        return Result.successed(o);
    }

    @RequestMapping(value = "/{domain}/load_one_by_condition", method = RequestMethod.POST)
    public Result loadOneByCondition(@RequestBody Request request, @PathVariable String domain) throws TipsException {

        String realDomain = transferUrlDomainToDomainName(domain);

        String classPackage = packageD + realDomain;

        Class<?> clazz = null;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此业务实体，无法loadone");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);

        CrudService crudService = crudServiceFactory.createService(realDomain);
        Object o = crudService.loadOneByCondition(tt);


        return Result.successed(o);
    }


    @RequestMapping(value = "/{domain}/list", method = RequestMethod.POST)
    public Result list(@RequestBody Request request, @PathVariable String domain) throws TipsException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        String realDomain = transferUrlDomainToDomainName(domain);

        String classPackage = packageD + realDomain;

        Class<?> clazz = null;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此业务实体，无法list");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);

        CrudService crudService = crudServiceFactory.createService(realDomain);


        return crudService.list(tt, request.getPage(), request.getLimit());
    }

    @RequestMapping(value = "/{domain}/remove", method = RequestMethod.POST)
    public Result remove(@RequestBody Request request, @PathVariable String domain) throws TipsException {

        String realDomain = transferUrlDomainToDomainName(domain);

        String classPackage = packageD + realDomain;

        Class<?> clazz = null;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此业务实体，无法remove");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);

        CrudService crudService = crudServiceFactory.createService(realDomain);
        recordOperator(tt, request.getToken());
        int row = crudService.remove(tt);
        if (row == 1) {
            return Result.successed(row);
        }

        return Result.failed("删除单条数据失败");
    }


    @RequestMapping(value = "/{domain}/removes", method = RequestMethod.POST)
    public Result removes(@RequestBody Request request, @PathVariable String domain) throws TipsException {

        String realDomain = transferUrlDomainToDomainName(domain);

        String classPackage = packageD + realDomain;

        Class<?> clazz ;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此业务实体，无法removes");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);

        CrudService crudService = crudServiceFactory.createService(realDomain);
        int row = 0;
        try {
            recordOperator(tt, request.getToken());
            row = crudService.removes(tt);
        } catch (NoSuchMethodException e) {
            return Result.failed("删除多条数据失败:" + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            return Result.failed("删除多条数据失败:" + e.getMessage());
        }
        if (row > 0) {
            return Result.successed(row);
        }

        return Result.failed("删除多条数据失败");
    }

    @RequestMapping(value = "/{domain}/tree", method = RequestMethod.POST)
    public Result tree(@RequestBody Request request, @PathVariable String domain) throws TipsException {
        String realDomain = transferUrlDomainToDomainName(domain);
        String classPackage = packageD + realDomain;

        Class<?> clazz = null;
        try {
            clazz = Class.forName(classPackage);
        } catch (ClassNotFoundException e) {
            return Result.failed("无此tree接口");
        }

        if(!BaseTreeEntity.class.isAssignableFrom(clazz)){
            return Result.failed("无此tree接口");
        }
        String json = JSON.toJSONString(request.getData());
        BaseEntity tt = JSON.parseObject(json, (Type) clazz);
        List<BaseTreeEntity> list = treeUtils.treeListByCondition((BaseTreeEntity) tt,  (Class<BaseTreeEntity>) clazz);
        for (int i = 0; i < list.size(); i++) {
            //有父级id则删除
            if(!BaseTreeEntity.ROOT.equals(list.get(i).getParentId())){
                list.remove(i);
            }

        }
        return Result.listSuccessed(list, (long) list.size());
    }

    /**
     * 说明： url上的domain转换成domain的类名
     * @author: ouyaokun
     * @date: Created in 17:23 2018/12/11
     * @modified: by autor in 17:23 2018/12/11
     * @param urlDomain url上的domain
     * @return domain的类名
     */
    private String transferUrlDomainToDomainName(String urlDomain){
        if(EmptyUtil.isEmpty(urlDomain)){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String[] split = urlDomain.toLowerCase().split("_");
        for(String part : split){
            sb.append(part.substring(0, 1).toUpperCase() + part.substring(1));
        }

        return sb.toString();
    }
}
