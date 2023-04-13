package com.fall.adminserver.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FAll
 * @date 2023/4/13 下午7:46
 * Swagger配置
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI Docket(){
        return new OpenAPI()
                .info(docInfos())
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub repo")
                        .url("https://github.com/RollBook/RoBok"));
    }

    private Info docInfos(){
        return new Info()
                .title("罗伯克管理员接口文档")
                .version("1.0")
                .license(new License()
                        .name("GNU Affero General Public License v3.0")
                        .url("https://www.gnu.org/licenses/agpl-3.0.html"));
    }

}
