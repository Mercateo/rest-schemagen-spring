package com.mercateo.rest.schemagen.spring;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.mercateo.common.rest.schemagen.plugin.FieldCheckerForSchema;
import com.mercateo.common.rest.schemagen.plugin.MethodCheckerForLink;

@Configurable
@Import(JerseyHateoasConfiguration.class)
public class TestConfiguration {

    @Bean
    public FieldCheckerForSchema fieldCheckerForSchema() {
        return mock(FieldCheckerForSchema.class);
    }

    @Bean
    public MethodCheckerForLink methodCheckerForLink() {
        return mock(MethodCheckerForLink.class);
    }
}
