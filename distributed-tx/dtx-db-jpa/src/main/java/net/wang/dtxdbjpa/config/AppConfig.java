package net.wang.dtxdbjpa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Collection;

@Configuration
public class AppConfig {


    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource_user")
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource userDataSource(@Qualifier(value = "userDataSourceProperties") DataSourceProperties userDataSourceProperties) {
        return userDataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("userDataSource") DataSource userDataSource) {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);

        entityManager.setDataSource(userDataSource);
        entityManager.setPackagesToScan("net.wang.dtxdbjpa.domain");
        entityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        return entityManager;


    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource_order")
    public DataSourceProperties orderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource orderDataSource(@Qualifier(value = "orderDataSourceProperties") DataSourceProperties orderDataSourceProperties) {
        return orderDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public JdbcTemplate orderJdbcTemplate(@Qualifier("orderDataSource") DataSource orderDataSource) {
        return new JdbcTemplate(orderDataSource);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????
     * {@link ChainedTransactionManager#commit(TransactionStatus)}
     * {@link ChainedTransactionManager#rollback(TransactionStatus)}
     * ????????????????????????{@link ChainedTransactionManager#reverse(Collection)}}
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("orderDataSource") DataSource orderDataSource,
                                                         @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        PlatformTransactionManager orderTM = new DataSourceTransactionManager(orderDataSource);
        PlatformTransactionManager userTM = new JpaTransactionManager(entityManagerFactory);
        ChainedTransactionManager chainedTransactionManager = new ChainedTransactionManager(orderTM, userTM); //????????????????????????
        return chainedTransactionManager;
    }
}
