package com.hm.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static String signKey = "SVRIRUlNQQ==";
    private static Long expire = 4320000000L;

    /**
     * 生成JWT令牌
     * @return
     */
    public static String generateJwt(Map<String,Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)//把内容加入载荷
                .signWith(SignatureAlgorithm.HS256, signKey)//设置加密算法，并给个名字
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}