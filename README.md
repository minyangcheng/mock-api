## sping boot 自动化mock接口

移动端开发中经常需要mock接口数据，一般的接口数据模拟都是通过nodejs、spring mvc、spring boot去手写每个接口的请求和返回数据。其实我们可以将这一过程自动化实现，我们可以将接口文档上定义的接口数据爬下来存在数据库中，然后根据数据库中的相应的接口数据进行返回给调用方。

### 技术点

1. 爬虫相关的技术，这里我采用的是[webmagic爬虫框架](http://webmagic.io/)
2. [spring-boot](http://projects.spring.io/spring-boot/)
3. [spring-data-jpa](http://projects.spring.io/spring-data-jpa/)

### 工作流程分析

* 启动spring boot项目时，开启爬虫

```
@Component
public class StartUpRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartUpRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info(".........StartUpRunner.........");
        Crawler.start();
    }

}
```

```
public class Crawler {

    public static void start() {
        Spider.create(new ShowDocPageProcessor())
                .addUrl(Constants.SHOW_DOC_ADDRESS)
                .addPipeline(new DbPipeline())
                .thread(3)
                .run();
    }

}
```

* 根据指定的css节点，从接口文档上爬取数据并保存到数据库中

```
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
```

```
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
```

* 统一处理所有的接口请求

```
@RestController
public class MockApiController {

    @Autowired
    private MockApiService mockApiService;

    @RequestMapping("/**/*")
    public String mock(HttpServletRequest request, HttpServletResponse response) {
        String respData = mockApiService.getRespData(request, response);
        return respData;
    }

}
```

```
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
```

