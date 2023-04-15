package com.fall.adminserver.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author FAll
 * @date 2023/4/14 下午4:27
 * Jwt工具类
 */
@Component
public class JwtUtil {

    @Value("${jwt.expire-time}")
    public String JWT_TTL;

    @Value("${jwt.secret-key}")
    public String JWT_KEY;

    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject,null,getUUID());
        return builder.compact();
    }

    public String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,getUUID());
        return builder.compact();
    }

    private JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if(ttlMillis==null) {
            ttlMillis = Long.parseLong(this.JWT_TTL);
        }
        long expMillis = nowMillis + Long.parseLong(this.JWT_TTL);
        Date expDate = new Date(expMillis);

        return Jwts.builder()
                .setId(uuid)            // 唯一id
                .setSubject(subject)    // 主题
                .setIssuer("fall")      // 签发者
                .setIssuedAt(now)       // 签发时间
                .signWith(signatureAlgorithm,secretKey) // 使用HS256算法结合密钥进行签名
                .setExpiration(expDate);

    }

    public String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    public SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(this.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
