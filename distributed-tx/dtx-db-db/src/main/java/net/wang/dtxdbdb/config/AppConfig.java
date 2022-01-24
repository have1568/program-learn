package net.wang.dtxdbdb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

import javax.sql.DataSource;

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
    public JdbcTemplate userJdbcTemplate(@Qualifier("userDataSource") DataSource userDataSource) {
        return new JdbcTemplate(userDataSource);
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
     * 链式事务管理器，提交和回滚是拿到所有注册到链式事务迭代提交或者回滚
     * {@link org.springframework.data.transaction.ChainedTransactionManager#commit(TransactionStatus)}
     * {@link org.springframework.data.transaction.ChainedTransactionManager#rollback(TransactionStatus)}
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("userDataSource") DataSource userDataSource,
                                                         @Qualifier("orderDataSource") DataSource orderDataSource) {
        DataSourceTransactionManager userTM = new DataSourceTransactionManager(userDataSource);
        DataSourceTransactionManager orderTM = new DataSourceTransactionManager(orderDataSource);
        ChainedTransactionManager chainedTransactionManager = new ChainedTransactionManager(userTM, orderTM);
        return chainedTransactionManager;
    }
}
