package com.mercateo.rest.schemagen.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

    URI getDefaultBaseUri(HttpServletRequest request) throws URISyntaxException {
        final URI uri = new URI(request.getRequestURL().toString());
        String path = request.getContextPath() + request.getServletPath();

        return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), path, null, null);
    }
}
