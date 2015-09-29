package com.bdls.market.config;

import com.bdls.market.MarketApplication;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = MarketApplication.class)
public class DataSourceConfig {//implements TransactionManagementConfigurer {

        @Value("${spring.datasource.username}")
        private String user;
        @Value("${spring.datasource.password}")
        private String password;
        @Value("${spring.datasource.url}")
        private String dataSourceUrl;
        @Value("${spring.datasource.driver-class-name}")
        private String dataSourceClassName;
        @Value("${spring.jpa.database-platform}")
        private String dialect;
        @Value("${spring.jpa.hibernate.ddl-auto}")
        private String hbm2ddlAuto;

        @Bean
        public DataSource configureDataSource() {
                HikariConfig config = new HikariConfig();
                config.setDriverClassName(dataSourceClassName);
                config.setJdbcUrl(dataSourceUrl);
                config.setUsername(user);
                config.setPassword(password);

                return new HikariDataSource(config);
        }

        /*@Bean
        public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory() {
                LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
                entityManagerFactoryBean.setDataSource(configureDataSource());
                entityManagerFactoryBean.setPackagesToScan("com.bdls.market");
                entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

                Properties jpaProperties = new Properties();
                jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
                jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
                entityManagerFactoryBean.setJpaProperties(jpaProperties);

                return entityManagerFactoryBean;
        }

        @Bean
        public PlatformTransactionManager annotationDrivenTransactionManager() {
                return new JpaTransactionManager();
        }*/
}
