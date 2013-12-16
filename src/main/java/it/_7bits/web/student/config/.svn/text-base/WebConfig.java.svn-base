package it._7bits.web.student.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Web part spring config
 */
@Configuration
@EnableWebMvc
// Setting up base package(s)
@ComponentScan(basePackages = {"it._7bits.web.student"})
public class WebConfig extends WebMvcConfigurerAdapter {
    private static final String RESOURCE_HANDLER = "/resources/**";
    private static final String RESOURCE_LOCATION = "/resources/";
    private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/jsp/";
    private static final String VIEW_RESOLVER_SUFFIX = ".jsp";
    private static final String MESSAGE_SOURCE_BASENAME = "/WEB-INF/messages/messages";
    private static final boolean MESSAGE_SOURCE_DISPLAY_KEY_IF_KEY_NOT_FOUND = true;
    private static final String MESSAGE_SOURCE_DEFAULT_ENCODING = "UTF-8";
    private static final int MESSAGE_SOURCE_CACHE_SECONDS = 0;

    /**
     * Setting up resources folder
     * @param registry auto wired by Spring
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler (RESOURCE_HANDLER).
                addResourceLocations (RESOURCE_LOCATION);
    }

    /**
     * Setting up views
     * @return views resolver
     */
    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix (VIEW_RESOLVER_PREFIX);
        resolver.setSuffix (VIEW_RESOLVER_SUFFIX);
        return resolver;
    }

    /**
     * Setting up Spring Message Source
     * @return Message Source
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames (MESSAGE_SOURCE_BASENAME);
        // if true, the key of the message will be displayed if the key is not
        // found, instead of throwing a NoSuchMessageException
        messageSource.setUseCodeAsDefaultMessage (MESSAGE_SOURCE_DISPLAY_KEY_IF_KEY_NOT_FOUND);
        messageSource.setDefaultEncoding (MESSAGE_SOURCE_DEFAULT_ENCODING);
        // # -1 : never reload, 0 always reload
        messageSource.setCacheSeconds (MESSAGE_SOURCE_CACHE_SECONDS);
        return messageSource;
    }
}
