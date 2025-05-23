package com.todo.backend.config;

import com.todo.backend.entity.Author;
import com.todo.backend.entity.Book;
import com.todo.backend.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
public class RestApiConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry registry) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.PUT,
                HttpMethod.POST,
                HttpMethod.DELETE,
                HttpMethod.PATCH};

        config.exposeIdsFor(Book.class, Review.class, Author.class);
        disableHttpMethods(config, theUnsupportedActions);

        /* Configure CORS Mapping */
        String theAllowedOrigins = "http://localhost:3000";
        registry.addMapping(config.getBasePath() + "/**")
                .allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedActions,
                                    Class<?>... domainTypes) {
        for (Class<?> domainType : domainTypes) {
            config.getExposureConfiguration()
                    .forDomainType(domainType)
                    .withItemExposure(((metadata, httpMethods) ->
                            httpMethods.disable(theUnsupportedActions)))
                    .withCollectionExposure(((metadata, httpMethods) ->
                            httpMethods.disable(theUnsupportedActions)));
        }
    }
}
