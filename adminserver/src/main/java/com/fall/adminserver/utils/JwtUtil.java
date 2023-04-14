package com.fall.adminserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author FAll
 * @date 2023/4/14 下午4:27
 */
public class JwtUtil {
    // TODO: 导出配置
    public static final Long JWT_TTL = 60 * 60 * 1000L;

    public static final String JWT_KEY = "RollBookFAllTan";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject,null,getUUID());
        return builder.compact();
    }

    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,getUUID());
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if(ttlMillis==null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + JWT_TTL;
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)            // 唯一id
                .setSubject(subject)    // 主题
                .setIssuer("fall")      // 签发者
                .setIssuedAt(now)       // 签发时间
                .signWith(signatureAlgorithm,secretKey) // 使用HS256算法结合密钥进行签名
                .setExpiration(expDate);

    }

    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
