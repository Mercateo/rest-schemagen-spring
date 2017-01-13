package com.mercateo.rest.schemagen.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpRequestMapperTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private HttpRequestMapper mapper;

    @Test
    public void shouldCreateRequestHeaders() throws Exception {
        when(request.getHeaderNames()).thenReturn(enumerate("foo", "bar"));
        when(request.getHeaders("foo")).thenReturn(enumerate("baz", "qux"));
        when(request.getHeaders("bar")).thenReturn(enumerate("quux"));

        final Map<String, List<String>> headers = mapper.requestHeaders(request);

        assertThat(headers)
                .hasSize(2)
                .containsEntry("foo", Arrays.asList("baz", "qux"))
                .containsEntry("bar", Collections.singletonList("quux"));
    }

    @Test
    public void name() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://user:pass@host:8080/foo/bar"));
        when(request.getServletPath()).thenReturn("/api");
        final String defaultBaseUri = mapper.getDefaultBaseUri(request).toString();

        assertThat(defaultBaseUri).isEqualTo("http://user:pass@host:8080/api");
    }

    @SafeVarargs
    final private <T> Enumeration<T> enumerate(T... elements) {
        final Iterator<T> names = Arrays.asList(elements).iterator();

        return new Enumeration<T>() {
            @Override
            public boolean hasMoreElements() {
                return names.hasNext();
            }

            @Override
            public T nextElement() {
                return names.next();
            }
        };
    }
}