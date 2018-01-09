package com.mercateo.rest.schemagen.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.mercateo.common.rest.schemagen.plugin.FieldCheckerForSchema;
import com.mercateo.common.rest.schemagen.plugin.MethodCheckerForLink;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mercateo.common.rest.schemagen.link.LinkFactoryContext;

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class JerseyHateoasConfigurationTest {

    @Autowired
    private LinkFactoryContext linkFactoryContext;

    @Test
    public void shouldReturnDefaultBaseUrl() throws Exception {
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
    }
}