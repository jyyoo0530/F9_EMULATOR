package com.emulator.f9.model.ftr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@PropertySource("classpath:multiple_db.properties")
@EnableJpaRepositories(basePackages = "com.emulator.f9.model.ftr", entityManagerFactoryRef = "mysqlEntityManager", transactionManagerRef = "mysqlTransactionManager")
public class MySqlConfiguration {
    @Autowired
    private Environment env;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean mysqlEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(myMySQLDataSource());
        em.setPackagesToScan("com.emulator.f9.model.ftr");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("spring.jpa.hibernate.ddl-auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    @Primary
    public DataSource myMySQLDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.mysql.jdbc.driverClassName")));
        dataSource.setUrl(env.getProperty("spring.mysql.jdbc.url"));
        dataSource.setUsername(env.getProperty("spring.mysql.user"));
        dataSource.setPassword(env.getProperty("spring.mysql.pass"));

        return dataSource;
    }

    @Bean
    @Primary
    public PlatformTransactionManager mysqlTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mysqlEntityManager().getObject());
        return transactionManager;
    }
}

