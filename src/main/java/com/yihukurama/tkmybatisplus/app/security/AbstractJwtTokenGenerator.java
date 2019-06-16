package com.yihukurama.tkmybatisplus.app.security;

import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.admin.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 说明： jwt生成类
 * @author yihukurma
 * @date Created in 下午 3:14 2019/6/16 0016
 *  modified by autor in 下午 3:14 2019/6/16 0016
 */
public abstract class AbstractJwtTokenGenerator {

    /**
     * 功能描述:给User生成token
     *
     * @param u
     * @param secret 秘钥
     * @return token
     * @Author:dengshuai
     * @Date:2016年9月27日 下午7:57:13
     */
    public static String generateToken(UserEntity u, String secret) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
