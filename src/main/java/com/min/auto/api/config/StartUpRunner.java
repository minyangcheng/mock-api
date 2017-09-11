package com.min.auto.api.config;

import com.min.auto.api.crawler.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartUpRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info(".........StartUpRunner.........");
        Crawler.start();
    }

}
