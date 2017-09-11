package com.min.auto.api.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

public final class ResponseUtils {

  public static final Logger log = Logger.getLogger(ResponseUtils.class);

  public static void renderText(HttpServletResponse response, String text) {
    render(response, "text/plain;charset=UTF-8", text);
  }

  public static void renderJson(HttpServletResponse response, String text) {
    render(response, "application/json;charset=UTF-8", text);
  }

  public static void renderXml(HttpServletResponse response, String text) {
    render(response, "text/xml;charset=UTF-8", text);
  }

  public static void render(HttpServletResponse response, String contentType, String text) {
    String str = StringUtils.isEmpty(text) ? "" : text;
    response.setContentType(contentType);
    response.setCharacterEncoding("UTF-8");
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    try {
      response.getWriter().write(str);
      response.getWriter().flush();
      response.getWriter().close();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
  
  public static void responseJson(HttpServletResponse response, Object data) {
	  if(data==null){
		  renderJson(response, "");
	  }else{
		  renderJson(response, GsonUtil.toJson(data));
	  }
  }
  
}
