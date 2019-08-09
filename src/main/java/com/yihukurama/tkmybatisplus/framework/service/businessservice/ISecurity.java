package com.yihukurama.tkmybatisplus.framework.service.businessservice;

/**
 * 说明： 系统安全接口
 *
 * @author dengshuai
 * @date Created in 14:48 2018/12/12
 * @modified by autor in 14:48 2018/12/12
 */
public interface ISecurity {

    /**
     * 密码加密
     * @param pwd
     * @return
     */
    String pwdEncrypt(String pwd);

    /**
     * token加密
     * @param token
     * @return
     */
    String tokenEncrypt(String token);
    /**
     * token解密
     * @param encryptToken
     * @return
     */
    String tokenDecrypt(String encryptToken);

    /**
     * 说明： 生成随机码
     * @author: ouyaokun
     * @date: Created in 18:02 2019/1/5
     * @modified: by autor in 18:02 2019/1/5
     * @param key 存放随机码的key
     * @param length 随机码长度
     * @param expireTime 有效期 单位秒
     * @return 随机码
     */
    String generalCode(String key, Integer length, Long expireTime);

    /**
     * 说明： 检查随机码是否有效
     * @author: ouyaokun
     * @date: Created in 18:03 2019/1/5
     * @modified: by autor in 18:03 2019/1/5
     * @param key 存放随机码的key
     * @param code 随机码
     * @return 随机码是否正确
     */
    Boolean checkCode(String key, String code);
}
