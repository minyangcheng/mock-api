package com.min.auto.api.crawler;

import com.min.auto.api.bean.ApiBean;
import com.min.auto.api.service.MockApiService;
import com.min.auto.api.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class DbPipeline implements Pipeline {

    private MockApiService mockApiService;

    public DbPipeline(){
        mockApiService= SpringUtil.getBean(MockApiService.class);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        saveData(resultItems,task);
    }

    public void saveData(ResultItems resultItems, Task task){
        String apiPath=resultItems.get(RegexUtil.KEY_URL_PATH);
        String respData=resultItems.get(RegexUtil.KEY_RESP_DATA);
        if(StringUtils.isEmpty(apiPath)||StringUtils.isEmpty(respData)){
            return;
        }
        ApiBean apiBean=new ApiBean(apiPath,"",respData);
        mockApiService.saveApiBean(apiBean);
    }

}
