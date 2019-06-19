package com.yihukurama.tkmybatisplus.app.security;

import com.yihukurama.tkmybatisplus.framework.domain.tkmapper.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * 说明： jwt验证类
 * @author yihukurma
 * @date Created in 下午 3:14 2019/6/16 0016
 *  modified by autor in 下午 3:14 2019/6/16 0016
 */
public abstract class AbstractJwtTokenValidator {

    public static UserEntity parseToken(String token, String secret) {
        UserEntity u = null;

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            u = new UserEntity();
            u.setUsername(body.getSubject());
            u.setId((String) body.get("userId"));

        } catch (JwtException e) {
            // Simply print the exception and null will be returned for the userDto
            e.printStackTrace();
        }
        return u;
    }
}

