package com.mercateo.rest.schemagen.spring;

import com.mercateo.common.rest.schemagen.link.LinkFactoryContext;
import com.mercateo.common.rest.schemagen.plugin.FieldCheckerForSchema;
import com.mercateo.common.rest.schemagen.plugin.MethodCheckerForLink;
import com.mercateo.common.rest.schemagen.plugin.TargetSchemaEnablerForLink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class JerseyHateoasConfigurationTest {

    @Autowired
    private LinkFactoryContext linkFactoryContext;

    @Test
    public void shouldReturnDefaultBaseUrl() {
        assertThat(linkFactoryContext.getBaseUri().toString()).isEqualTo("http://localhost");
    }

    @Configuration
    @Import(JerseyHateoasConfiguration.class)
    public static class TestConfiguration {

        @Bean
        public FieldCheckerForSchema fieldCheckerForSchema() {
            return mock(FieldCheckerForSchema.class);
        }

        @Bean
        public MethodCheckerForLink methodCheckerForLink() {
            return mock(MethodCheckerForLink.class);
        }

        @Bean
        public TargetSchemaEnablerForLink targetSchemaEnablerForLink() {
            return mock(TargetSchemaEnablerForLink.class);
        }
    }
}