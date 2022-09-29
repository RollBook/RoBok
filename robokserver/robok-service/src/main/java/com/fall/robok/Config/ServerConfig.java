package com.fall.robok.Config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @author FAll
 * @date 2022/9/29 15:25
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ServerConfig {

    @Value("${my-path}")
    private String path;

    @Value("${server.port}")
    private String port;

    @Value("${spring.profiles.active}")
    private String environment;

    @PostConstruct
    private void checkDir() {
        File pfile = new File(path);
        if (!pfile.exists()) {
            // 不存在时,创建文件夹
            log.warn("========> 未创建上传文件夹,开始创建......");
            pfile.mkdirs();
            log.info("========> 上传文件夹创建成功");
        } else {
            log.info("========> 上传文件夹加载完成");
        }
    }



}
