package com.min.auto.api.task;

import com.min.auto.api.crawler.Crawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class RegularTask {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "0 0 10,14,16 * * ?")
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> RegularTask ... ");
        Crawler.start();
    }

}
