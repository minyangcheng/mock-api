package com.min.auto.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.min.auto.api.util.GsonUtil;

/**
 * Created by minyangcheng on 2017/7/28.
 */

public class Test {

    @org.junit.Test
    public void testGson(){
        String s="{ \"code\": 10000, \"data\": [{ \"businessObjectProcessInfoId\":\"流程实例ID\", \"businessTypeCode\":\"流程编号\", \"businessId\":\"业务ID\", \"businessTypeLogo\":\"流程标识图片路径\", \"customerName\":\"客户名称\", \"busiName\":\"业务名称\", \"lastNodeName\":\"上一环节名称\", \"lastSubmitTime\":\"上一环节提交时间\", \"currentNodeKey\": \"当前环节编码\", \"currentNodeName\":\"当前环节名称\", \"submitTime\":\"办理时间\", \"isRead\": false }], \"totalItem\": 0, \"message\": \"\" }";
        JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
        if(jsonObject.has("flowType1")){
            System.out.println("flowType");
        }
        System.out.println(jsonObject.toString());
    }

}
