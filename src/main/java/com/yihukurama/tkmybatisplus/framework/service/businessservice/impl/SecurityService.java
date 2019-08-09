package com.yihukurama.tkmybatisplus.framework.service.businessservice.impl;

import com.yihukurama.tkmybatisplus.app.cache.RedisUtils;
import com.yihukurama.tkmybatisplus.app.constant.Constant;
import com.yihukurama.tkmybatisplus.app.utils.EncrUtil;
import com.yihukurama.tkmybatisplus.app.utils.NumberUtil;
import com.yihukurama.tkmybatisplus.framework.service.businessservice.ISecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SecurityService implements ISecurity {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public String pwdEncrypt(String pwd) {
        //避免撞库
        pwd = pwd + Constant.encryptKey;
        pwd = EncrUtil.GetMD5Code(pwd);

        return pwd;
    }

    @Override
    public String tokenEncrypt(String token) {
        String encryptFirst = EncrUtil.AESEncrypt(token);
        String result = EncrUtil.AESEncrypt(encryptFirst);
        return result;
    }

    @Override
    public String tokenDecrypt(String encryptToken) {
        String encryptFirst = EncrUtil.AESDecrypt(encryptToken);
        String result = EncrUtil.AESDecrypt(encryptFirst);
        return result;
    }

    @Override
    public String generalCode(String key, Integer length, Long expireTime) {
        String checkCode = NumberUtil.getRandNum(length);
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
