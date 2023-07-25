/*
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mercateo.common.rest.schemagen.link.LinkFactoryContext;
import com.mercateo.common.rest.schemagen.plugin.FieldCheckerForSchema;
import com.mercateo.common.rest.schemagen.plugin.MethodCheckerForLink;
import com.mercateo.common.rest.schemagen.plugin.TargetSchemaEnablerForLink;

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
