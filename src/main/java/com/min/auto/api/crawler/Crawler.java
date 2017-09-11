package com.min.auto.api.crawler;

import us.codecraft.webmagic.Spider;

/**
 * Created by minyangcheng on 2017/8/4.
 */
public class Crawler {

    public static void start() {
        Spider.create(new ShowDocPageProcessor())
                .addUrl(Constants.SHOW_DOC_ADDRESS)
                .addPipeline(new DbPipeline())
                .thread(3)
                .run();
    }

}
