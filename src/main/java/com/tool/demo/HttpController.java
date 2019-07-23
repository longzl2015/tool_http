package com.tool.demo;


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


    @RequestMapping("/query")
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

        try (InputStream is = request.getInputStream()) {
            if (is != null) {
                String s = IOUtils.toString(is, StandardCharsets.UTF_8);
                dto.setBody(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        dto.setContentType(request.getContentType());

        return dto;
    }
}
