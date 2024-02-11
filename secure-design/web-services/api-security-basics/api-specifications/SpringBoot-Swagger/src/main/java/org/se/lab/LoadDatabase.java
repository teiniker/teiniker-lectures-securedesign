package org.se.lab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase
{
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ArticleRepository repository)
    {
        return args -> {
            log.info("Preloading " + repository.save(new Article("Design Patterns", 4295)));
            log.info("Preloading " + repository.save(new Article("Effective Java", 3336)));
        };
    }

}
