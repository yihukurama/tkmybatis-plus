package com.yihukurama.tkmybatisplus.framework.service.businessservice.impl;

import com.yihukurama.tkmybatisplus.app.cache.RedisUtils;
import com.yihukurama.tkmybatisplus.app.constant.Constant;
import com.yihukurama.tkmybatisplus.app.utils.EncrUtil;
import com.yihukurama.tkmybatisplus.app.utils.AbstractNumberUtil;
import com.yihukurama.tkmybatisplus.framework.service.businessservice.ISecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 说明： 安全业务类
 * @author yihukurma
 * @date Created in 上午 12:20 2019/8/10 0010
 *  modified by autor in 上午 12:20 2019/8/10 0010
 */
@Service
public class SecurityService implements ISecurity {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public String pwdEncrypt(String pwd) {
        //避免撞库
        pwd = pwd + Constant.encryptKey;
        pwd = EncrUtil.getMd5Code(pwd);

        return pwd;
    }

    @Override
    public String tokenEncrypt(String token) {
        String encryptFirst = EncrUtil.aesEncrypt(token);
        String result = EncrUtil.aesEncrypt(encryptFirst);
        return result;
    }

    @Override
    public String tokenDecrypt(String encryptToken) {
        String encryptFirst = EncrUtil.aesDecrypt(encryptToken);
        String result = EncrUtil.aesDecrypt(encryptFirst);
        return result;
    }

    @Override
    public String generalCode(String key, Integer length, Long expireTime) {
        String checkCode = AbstractNumberUtil.getRandNum(length);
        boolean isSet = redisUtils.set(key,checkCode,expireTime);
        if(isSet){
            return checkCode;
        }
        return null;

    }

    @Override
    public Boolean checkCode(String key, String code) {
        if(redisUtils.exists(key)){
            String exitCode = (String) redisUtils.get(key);
            if(exitCode.equals(code)){
                return true;
            }
        }
        return false;
    }
}
