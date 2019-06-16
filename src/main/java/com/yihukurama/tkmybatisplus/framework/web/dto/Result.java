package com.yihukurama.tkmybatisplus.framework.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 说明： 返回格式规范
 * @author yihukurma
 * @date Created in 下午 3:18 2019/6/16 0016
 *  modified by autor in 下午 3:18 2019/6/16 0016
 */
@Data
@NoArgsConstructor
public class Result {

    private Object data;

    private String msg;

    private Boolean success = false;

    protected Long total = 0L;

    private Integer errorCode = 0;


    private static final String Success="操作成功";
    private static final String Fail="操作失败";
    public static final Boolean RESULT_SUCCESSED = true;

    public static final Boolean RESULT_FAILED = false;

    public static final Integer ERRORCODE_ERROR = -1;

    public Integer getErrorCode() {
        return errorCode;
    }

    private void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Result(String msg, Boolean success) {

        this.msg = msg;
        this.success = success;
    }

    public Result(Object data, String msg, Boolean success, Long total) {

        this.data = data;
        this.msg = msg;
        this.success = success;
        this.total = total;
    }
    public static Result successed(String msg){
        return new Result(msg,true);
    }
    public static Result successed(Object data) {

        return successed(data,Success);
    }

    public static Result successed(Object data, String msg) {
        long a =getTotal(data);
       return  new Result(data, msg,true,a);
    }

	public static Result listSuccessed(Object data,Long total){
		return new Result(data,"查询成功",true,total);
	}

    public static Result failed() {
       return new Result(Fail, false);
    }

    public static Result failed(String msg) {
        return new Result(msg, false);
    }

    public static Result failed(Object data, String msg, Integer errorcode) {
        Result result = new Result(msg, false);
        result.setData(data);
        result.setErrorCode(errorcode);
        return result;
    }
    private static long getTotal(Object obj){
        if(obj instanceof List){
            return ((List) obj).size();
        }
        if(obj instanceof Map){
            return ((Map) obj).size();
        }
        return 0L;
    }



}
