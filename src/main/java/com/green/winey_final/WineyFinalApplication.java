package com.green.winey_final;

import com.green.winey_final.common.config.properties.AppProperties;
import com.green.winey_final.common.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, CorsProperties.class})
public class WineyFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(WineyFinalApplication.class, args);
    }

}
