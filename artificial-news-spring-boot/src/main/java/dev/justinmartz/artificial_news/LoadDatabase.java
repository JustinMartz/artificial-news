package dev.justinmartz.artificial_news;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@Configuration
@ConditionalOnProperty(name = "test-data.enabled", havingValue = "true")
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DataSource dataSource) {
        return args -> {
            Resource resource = new ClassPathResource("seed-data.sql");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
            log.info("Database seeded from seed-data.sql");
        };
    }
}
