/**
 * Copyright © 2017 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mercateo.rest.schemagen.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

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
