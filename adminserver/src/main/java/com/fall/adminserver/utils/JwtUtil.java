package com.fall.adminserver.utils;

import com.fall.adminserver.model.vo.ResponseRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
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

    private final ObjectMapper mapper;

    public JwtUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * @author FAll
     * @description 获取UUID
     * @return: java.lang.String
     * @date 2023/4/15 下午12:52
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * @author FAll
     * @description 签发JWT (使用默认过期时间)
     * @param subject 签发载体
     * @return: java.lang.String
     * @date 2023/4/15 下午12:52
     */
    public String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject,null,getUUID());
        return builder.compact();
    }

    /**
     * @author FAll
     * @description 签发JWT (自定义过期时间)
     * @param subject 签发载体
     * @param ttlMillis 过期时间
     * @return: java.lang.String
     * @date 2023/4/15 下午12:53
     */
    public String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject,ttlMillis,getUUID());
        return builder.compact();
    }

    /**
     * @author FAll
     * @description JWT建造者
     * @param subject 签发载体
     * @param ttlMillis 过期时间
     * @param uuid UUID
     * @return: io.jsonwebtoken.JwtBuilder
     * @date 2023/4/15 下午12:54
     */
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

    /**
     * @author FAll
     * @description 获取密钥
     * @return: javax.crypto.SecretKey
     * @date 2023/4/15 下午12:55
     */
    public SecretKey generalKey(){
        byte[] encodedKey = Base64.getDecoder().decode(this.JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * @author FAll
     * @description 解析JWT（失败则抛出异常）
     * @param jwt JWT
     * @return: io.jsonwebtoken.Claims
     * @date 2023/4/15 下午12:56
     */
    public Claims parseJWT(String jwt) throws JwtException {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();

    }

}
