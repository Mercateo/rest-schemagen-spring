package com.mercateo.rest.schemagen.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mercateo.common.rest.schemagen.types.ListResponseBuilderCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mercateo.common.rest.schemagen.JsonHyperSchemaCreator;
import com.mercateo.common.rest.schemagen.JsonSchemaGenerator;
import com.mercateo.common.rest.schemagen.RestJsonSchemaGenerator;
import com.mercateo.common.rest.schemagen.link.LinkFactoryContext;
import com.mercateo.common.rest.schemagen.link.LinkFactoryContextDefault;
import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.link.helper.BaseUriCreator;
import com.mercateo.common.rest.schemagen.link.helper.BaseUriCreatorDefault;
import com.mercateo.common.rest.schemagen.plugin.FieldCheckerForSchema;
import com.mercateo.common.rest.schemagen.plugin.MethodCheckerForLink;
import com.mercateo.common.rest.schemagen.types.HyperSchemaCreator;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchemaCreator;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;

@Configuration
public class JerseyHateoasConfiguration {

    @Bean
    public JsonSchemaGenerator jsonSchemaGenerator() {
        return new RestJsonSchemaGenerator();
    }

    @Bean
    public LinkMetaFactory linkMetaFactory(JsonSchemaGenerator jsonSchemaGenerator,
            LinkFactoryContext linkFactoryContext) throws URISyntaxException {
        return LinkMetaFactory.create(jsonSchemaGenerator, linkFactoryContext);
    }

    @Bean
    JsonHyperSchemaCreator jsonHyperSchemaCreator() {
        return new JsonHyperSchemaCreator();
    }

    @Bean
    ObjectWithSchemaCreator objectWithSchemaCreator() {
        return new ObjectWithSchemaCreator();
    }

    @Bean
    PaginatedResponseBuilderCreator paginatedResponseBuilderCreator() {
        return new PaginatedResponseBuilderCreator();
    }

    @Bean
    public ListResponseBuilderCreator listResponseBuilderCreator() {
        return new ListResponseBuilderCreator();
    }

    @Bean
    HyperSchemaCreator hyperSchemaCreator(ObjectWithSchemaCreator objectWithSchemaCreator,
            JsonHyperSchemaCreator jsonHyperSchemaCreator) {
        return new HyperSchemaCreator(objectWithSchemaCreator, jsonHyperSchemaCreator);
    }

    @Bean
    @Scope(value = "request")
    HttpServletRequest httpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    LinkFactoryContext linkFactoryContext(HttpServletRequest httpServletRequest, BaseUriCreator baseUriCreator,
            FieldCheckerForSchema fieldCheckerForSchema, MethodCheckerForLink methodCheckerForLink)
            throws URISyntaxException {
        URI baseUri = determineBaseUri(httpServletRequest, baseUriCreator);

        return new LinkFactoryContextDefault(baseUri, methodCheckerForLink, fieldCheckerForSchema);
    }

    private URI determineBaseUri(HttpServletRequest httpServletRequest, BaseUriCreator baseUriCreator)
            throws URISyntaxException {
        URI defaultBaseUri = new URI(httpServletRequest.getRequestURL().toString());
        HashMap<String, List<String>> requestHeaders = requestHeaders(httpServletRequest);
        return baseUriCreator.createBaseUri(defaultBaseUri, requestHeaders);
    }

    private HashMap<String, List<String>> requestHeaders(HttpServletRequest httpServletRequest) {
        HashMap<String, List<String>> requestHeaders = new HashMap<String, List<String>>();

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        for (String headerName : Collections.list(headerNames)) {
            final Enumeration<String> headers = httpServletRequest.getHeaders(headerName);
            for (String header : Collections.list(headers)) {
                requestHeaders.computeIfAbsent(headerName, ignored -> new ArrayList<>()).add(header);
            }
        }

        return requestHeaders;
    }

    @Bean
    BaseUriCreator baseUriCreator() {
        return new BaseUriCreatorDefault();
    }
}
