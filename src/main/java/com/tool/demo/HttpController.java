package com.tool.demo;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@RestController
@Slf4j
public class HttpController {


    @RequestMapping("/query/total")
    public HttpDto getQuery(HttpServletRequest request) {
        request.getQueryString();

        HttpDto dto = new HttpDto();
        dto.setQueryString(request.getQueryString());

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String k = parameterNames.nextElement();
            String v = request.getParameter(k);
            dto.addParam(k, v);
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String k = headerNames.nextElement();
            String v = request.getHeader(k);
            dto.addHeader(k, v);
        }

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                dto.addCookie(cookie);
            }
        }

        String body;
        try (InputStream is = request.getInputStream()) {
            if (is != null) {
                body = IOUtils.toString(is, StandardCharsets.UTF_8);
                dto.setBody(body);

                jsonPrint(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        dto.setContentType(request.getContentType());

        log.info(JSONObject.toJSONString(dto, SerializerFeature.PrettyFormat));

        return dto;
    }

    @RequestMapping("/query/body")
    public String getQueryBody(HttpServletRequest request) {

        String body = "无body体";

        try (InputStream is = request.getInputStream()) {
            if (is != null) {
                body = IOUtils.toString(is, StandardCharsets.UTF_8);
                jsonPrint(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    /**
     * 强行解析，若不成功，catch 住即可
     *
     * @param body
     */
    private void jsonPrint(String body) {
        try {
            Object parse = JSONObject.parse(body);
            String s = JSONObject.toJSONString(parse, SerializerFeature.PrettyFormat);
            log.info(s);
        } catch (Exception e) {
            //忽略即可
        }
    }
}
