package com.mercateo.rest.schemagen.spring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

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
import com.mercateo.common.rest.schemagen.types.ListResponseBuilderCreator;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchemaCreator;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;

@Configuration
public class JerseyHateoasConfiguration {

    private final HttpRequestMapper httpRequestMapper;

    JerseyHateoasConfiguration() {
        httpRequestMapper = new HttpRequestMapper();
    }

    @Bean
    JsonSchemaGenerator jsonSchemaGenerator() {
        return new RestJsonSchemaGenerator();
    }

    @Bean
    LinkMetaFactory linkMetaFactory(JsonSchemaGenerator jsonSchemaGenerator, LinkFactoryContext linkFactoryContext)
            throws URISyntaxException {
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
    ListResponseBuilderCreator listResponseBuilderCreator() {
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
            FieldCheckerForSchema fieldCheckerForSchema, MethodCheckerForLink methodCheckerForLink,
            @Named("requestHeaders") Map<String, List<String>> requestHeaders) throws URISyntaxException {
        URI defaultBaseUri = new URI(httpServletRequest.getRequestURL().toString());
        URI baseUri = baseUriCreator.createBaseUri(defaultBaseUri, requestHeaders);

        return new LinkFactoryContextDefault(baseUri, methodCheckerForLink, fieldCheckerForSchema);
    }

    @Bean
    @Named("requestHeaders")
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    Map<String, List<String>> requestHeaders(HttpServletRequest httpServletRequest) {
        return httpRequestMapper.requestHeaders(httpServletRequest);
    }

    @Bean
    BaseUriCreator baseUriCreator() {
        return new BaseUriCreatorDefault();
    }
}
