package com.min.auto.api.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.min.auto.api.bean.ApiBean;
import com.min.auto.api.enums.ResultEnum;
import com.min.auto.api.exception.ServerException;
import com.min.auto.api.repository.MockApiRepository;
import com.min.auto.api.util.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by minyangcheng on 2017/7/28.
 */
@Service
public class MockApiService {

    private Logger logger = LoggerFactory.getLogger(MockApiService.class);

    @Autowired
    private MockApiRepository mockApiRepository;

    public String getRespData(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRequestURI();
        ApiBean apiBean = mockApiRepository.findByPathAndFlag(path, 1);
        if (apiBean != null) {
//            checkParams(request,apiBean);
            return apiBean.getRespData();
        } else {
            throw new ServerException(ResultEnum.FAIL.getCode(), "未知接口");
        }
    }

    /**
     * 后期扩展校验请求参数功能
     *
     * @param request
     * @param apiBean
     */
    private void checkParams(HttpServletRequest request, ApiBean apiBean) {
        Map<String, String[]> reqDataMap = request.getParameterMap();
        if (reqDataMap != null && StringUtils.isNotEmpty(apiBean.getRespData())) {
            Map<String, String> needMap = GsonUtil.gson.fromJson(apiBean.getReqData()
                    , new TypeToken<Map<String, String>>() {
                    }.getType());
            Set<String> needSet = needMap.keySet();
            Set<String> reqParams = reqDataMap.keySet();
            Iterator<String> iterator = needSet.iterator();
            String s;
            while (iterator.hasNext()) {
                s = iterator.next();
                if (!reqParams.contains(s)) {
                    throw new ServerException(ResultEnum.FAIL.getCode(), "缺少参数" + s);
                }
            }
        }
    }

    public void saveApiBean(ApiBean apiBean) {
        int flag = getRespDataValidFlag(apiBean.getPath(), apiBean.getRespData());
        apiBean.setFlag(flag);

        ApiBean lastBean = mockApiRepository.findByPath(apiBean.getPath());
        if (lastBean != null) {
            apiBean.setId(lastBean.getId());
            if (StringUtils.equals(GsonUtil.toJson(lastBean), GsonUtil.toJson(apiBean))) {
                mockApiRepository.saveAndFlush(apiBean);
            }
        } else {
            mockApiRepository.save(apiBean);
        }
    }

    public int getRespDataValidFlag(String path, String respData) {
        int flag = 1;
        try {
            new JsonParser().parse(respData).getAsJsonObject();
        } catch (Exception e) {
            logger.info("接口文档书写错误：path={},response={}", path, respData);
            flag = 0;
        }
        return flag;
    }

}
