package it._7bits.web.student.config;

import org.apache.log4j.Logger;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration for JPA via Hibernate
 */
@Configuration
@EnableTransactionManagement
public class HibernateJpaConfig {
    protected final Logger LOG = Logger.getLogger (getClass());
    private static final String JNDI_NAME_DATASOURCE = "jdbc/MyDS";
    private static final String JNDI_FULL_NAME_DATASOURCE = "java:comp/env/jdbc/MyDS";
    private static final String PROPERTY_HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL5Dialect";
    private static final String PROPERTY_HIBERNATE_DDL_AUTO = "create-drop";
    private static final String PROPERTY_HIBERNATE_SHOW_SQL = "true";
    private static final String PROPERTY_HIBERNATE_SQL_SCRIPT = "populate_sql.sql";
    private static final String PROPERTY_HIBERNATE_PERSISTANCE_VALIDATION = "none";
    private static final String PROPERTY_HIBERNATE_SESSION_CONTEXT_CLASS = "org.hibernate.context.internal.ThreadLocalSessionContext";
    //private static final String PROPERTY_HIBERNATE_SESSION_CONTEXT_CLASS = "thread";
    private static final String PROPERTY_HIBERNATE_POOL_SIZE = "10";
    private static final String DOMAIN_PACKAGES_TOSCAN = "it._7bits.web.student.dao.hibernate.entity";
    private static final Class PERSISTANCE_PROVIDER_CLASS = HibernatePersistence.class;


    /**
     * Lookup DataSource via JNDI
     * @return DataSource
     */
    @Bean
    @Resource(name=JNDI_NAME_DATASOURCE)
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef (true);
        DataSource dataSource = dsLookup.getDataSource (JNDI_FULL_NAME_DATASOURCE);
        return dataSource;
    }

    /**
     * Transaction Manager for JPA Hibernate
     * @return Transaction Manager
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory (entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    /**
     * Setting Hibernate properties for JPA
     * @return (Properties) Hibernate properties
     */
    @Bean
    public Properties hibernateJpaProperties() {
        Properties properties = new Properties();
        properties.put ("hibernate.dialect", PROPERTY_HIBERNATE_DIALECT);
        properties.put ("hibernate.hbm2ddl.auto", PROPERTY_HIBERNATE_DDL_AUTO);
        //properties.put ("javax.persistence.validation.mode", PROPERTY_HIBERNATE_PERSISTANCE_VALIDATION);
        properties.put ("hibernate.show_sql",PROPERTY_HIBERNATE_SHOW_SQL);
        properties.put ("hibernate.hbm2ddl.import_files", PROPERTY_HIBERNATE_SQL_SCRIPT);
        //properties.put ("hibernate.current_session_context_class",PROPERTY_HIBERNATE_SESSION_CONTEXT_CLASS);
        //properties.put ("hibernate.connection.pool_size",PROPERTY_HIBERNATE_POOL_SIZE);
        return properties;
    }

    /**
     * Entity Manager Factory for JPA Hibernate
     * @return Entity Manager Factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource (dataSource());
        entityManagerFactoryBean.setPackagesToScan(DOMAIN_PACKAGES_TOSCAN);
        entityManagerFactoryBean.setPersistenceProviderClass(PERSISTANCE_PROVIDER_CLASS);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties (hibernateJpaProperties());
        return entityManagerFactoryBean;
    }
}
