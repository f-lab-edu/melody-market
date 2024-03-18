package com.melodymarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@PropertySource("classpath:/keys.properties")
public class MelodyMarketApplication {

    private static final Logger logger = LoggerFactory.getLogger(MelodyMarketApplication.class);

    public static void main(String[] args) {
        logDatabaseConnectionInfo();
        SpringApplication.run(MelodyMarketApplication.class, args);
    }

    private static void logDatabaseConnectionInfo() {
        // 데이터베이스 연결 정보 환경 변수 또는 시스템 프로퍼티에서 로드
        String dbUrl = System.getenv("SPRING_DATASOURCE_URL");
        String dbUsername = System.getenv("SPRING_DATASOURCE_USERNAME");
        // 환경 변수에서 정보를 찾을 수 없는 경우, 시스템 프로퍼티를 사용합니다.
        if (dbUrl == null) {
            dbUrl = System.getProperty("spring.datasource.url");
        }
        if (dbUsername == null) {
            dbUsername = System.getProperty("spring.datasource.username");
        }

        // 로그 출력
        logger.info("Attempting to connect to database...");
        logger.info("URL: {}", dbUrl);
        logger.info("Username: {}", dbUsername);
        // 비밀번호는 로깅하지 않습니다.
    }

}
