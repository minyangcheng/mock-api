package com.min.auto.api.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by minyangcheng on 2017/8/4.
 */
public class RegexUtil {

    public static final String KEY_URL_PATH="urlPathKey";
    public static final String KEY_RESP_DATA="respDataKey";

    public static String ADD_TARGET_URL_PATTERN="http://10\\.10\\.13\\.12/.+";

    public static boolean matchTargetPage(String url){
        Pattern p = Pattern.compile(".+page_id=\\d+");
        Matcher m = p.matcher(url);
        return m.matches();
    }

}
