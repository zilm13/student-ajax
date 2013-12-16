package it._7bits.web.student.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Main Spring initializer class
 *
 * - Connecting all configs
 * - Configuring dispatcher servlet
 */
public class WebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Loading core configuration (ORM database setup, DAOs, Services)
     * @return configuration classes
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { DaoConfig.class, ServiceConfig.class,
                HibernateJpaConfig.class, DefaultValuesConfig.class};
    }

    /**
     * Loading configuration for web
     * @return configuration classes
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    /**
     * Setting up default servlet mapping
     * @return servlet mapping
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * Setting up servlet filters
     * @return servlet filters
     */
    @Override
    protected Filter[] getServletFilters() {

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        // Setting up UTF-8 environment
        characterEncodingFilter.setEncoding("UTF-8");
        return new Filter[] { characterEncodingFilter};
    }
}
