# rest-schemagen-spring
[![Build Status](https://travis-ci.org/Mercateo/rest-schemagen-spring.svg?branch=master)](https://travis-ci.org/Mercateo/rest-schemagen-spring)
[![Coverage Status](https://coveralls.io/repos/Mercateo/rest-schemagen-spring/badge.svg?branch=master&service=github)](https://coveralls.io/github/Mercateo/rest-schemagen-spring?branch=master)
[![MavenCentral](https://img.shields.io/maven-central/v/com.mercateo/rest-schemagen-spring.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.mercateo%22%20AND%20a%3A%22rest-schemagen-spring%22)

Spring support for [Mercateo/rest-schemagen](https://github.com/Mercateo/rest-schemagen). See [TNG/rest-demo-jersey](https://github.com/TNG/rest-demo-jersey) for a comprehensive example.

## Getting started

After adding this projects dependency, the following minimal configuration is required for a working schema generator:

```java
@Configuration
@Import(JerseyHateoasConfiguration.class)
public class WeatherServerConfiguration {

    @Bean
    public FieldCheckerForSchema fieldCheckerForSchema() {
        return (field, callContext) -> true;
    }

    @Bean
    public MethodCheckerForLink methodCheckerForLink() {
        return scope -> true;
    }
}
```

Particular link factories can be created via:
```java
    @Bean
    @Named("stationsLinkFactory")
    LinkFactory<FooResource> stationsResourceLinkFactory(LinkMetaFactory linkMetaFactory) {
        return linkMetaFactory.createFactoryFor(FooResource.class);
    }
```