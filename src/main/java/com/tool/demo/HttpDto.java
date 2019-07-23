package com.tool.demo;


import lombok.Data;
import lombok.Getter;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
public class HttpDto {

    private Map<String, String> params;
    private List<Cookie> cookies;
    private Map<String, String> headers;

    private String body;

    private String queryString;
    private String contentType;


    public HttpDto() {
        params = new HashMap<>();
        cookies = new ArrayList<>();
        headers = new HashMap<>();
    }

    public void addParam(String k, String v) {
        params.put(k, v);
    }

    public void addCookie(Cookie e) {
        cookies.add(e);
    }

    public void addHeader(String k, String v) {
        headers.put(k, v);
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
