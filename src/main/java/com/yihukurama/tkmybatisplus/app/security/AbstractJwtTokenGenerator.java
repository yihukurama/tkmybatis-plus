package com.yihukurama.tkmybatisplus.app.security;

import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.UserEntity;
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
     * 生成token
     * @param u
     * @param secret
     * @return
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
