package com.min.auto.api.crawler;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ShowDocPageProcessor implements PageProcessor {

    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000);

    public void process(Page page) {
        if (RegexUtil.matchTargetPage(page.getRequest().getUrl())) {
            String apiPath = page.getHtml().css("html body div ul li code", "text").toString();
            String responseData = page.getHtml().css("pre code", "text").toString();
            page.putField(RegexUtil.KEY_URL_PATH, apiPath);
            page.putField(RegexUtil.KEY_RESP_DATA, responseData);
        }
        page.addTargetRequests(page.getHtml().links().regex(RegexUtil.ADD_TARGET_URL_PATTERN).all());
    }

    public Site getSite() {
        return site;
    }

}
