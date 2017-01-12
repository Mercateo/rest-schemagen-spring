package com.mercateo.rest.schemagen.spring;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

class HttpRequestMapper {
    Map<String, List<String>> requestHeaders(HttpServletRequest httpServletRequest) {
        HashMap<String, List<String>> requestHeaders = new HashMap<>();

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        for (String headerName : Collections.list(headerNames)) {
            final Enumeration<String> headers = httpServletRequest.getHeaders(headerName);
            requestHeaders.put(headerName, Collections.list(headers));
        }
        return requestHeaders;
    }
}
