package com.melodymarket.infrastructure;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DatabaseInfoLogger {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInfoLogger.class);

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @EventListener(ApplicationReadyEvent.class)
    public void logDatabaseInfo() {
        logger.info("Database URL: {}", databaseUrl);
        logger.info("Database Username: {}", databaseUsername);
    }
}
