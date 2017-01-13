package com.mercateo.rest.schemagen.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mercateo.common.rest.schemagen.link.LinkFactoryContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@WebAppConfiguration
public class JerseyHateoasConfigurationTest {

    @Autowired
    private LinkFactoryContext linkFactoryContext;

    @Test
    public void shouldReturnDefaultBaseUrl() throws Exception {
        assertThat(linkFactoryContext.getBaseUri().toString()).isEqualTo("http://localhost");
    }
}