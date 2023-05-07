package com.fall.robok.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

/**
 * @author FAll
 * @date 2022/9/23 13:47
 */
@Component
@Data
@ConfigurationProperties(prefix = "wx-app")
public class WxConfig {

    private String appSecret;

    private String appID;

    private String mckKey;

    private String mchId;

}
