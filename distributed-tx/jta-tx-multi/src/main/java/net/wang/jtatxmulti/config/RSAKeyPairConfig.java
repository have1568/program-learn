package net.wang.jtatxmulti.config;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@DependsOn({"jtaTransactionManager"})
@EnableJpaRepositories(basePackages = {"net.wang.jtatxmulti.repo.rsa"}, entityManagerFactoryRef = "entityManagerFactoryRSA", transactionManagerRef = "jtaTransactionManager")
public class RSAKeyPairConfig  {
    @Resource
    JpaVendorAdapter jpaVendorAdapter;
    @Bean(name = "dataSourceRSA")
    public DataSource dataSourceRSA() {
        AtomikosDataSourceBean bean = new AtomikosDataSourceBean();
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl("jdbc:mysql://localhost:3306/jta_rsa?useUnicode=true&useSSL=true");
        mysqlXaDataSource.setUser("root");
        mysqlXaDataSource.setPassword("123456");
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        bean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        bean.setXaDataSource(mysqlXaDataSource);
        bean.setUniqueResourceName("dataSourceRSA");
        bean.setMaxPoolSize(50);
        bean.setBorrowConnectionTimeout(1000);
        return bean;
    }

    @Bean(name = "entityManagerFactoryRSA")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRSA(@Qualifier(value = "dataSourceRSA") DataSource dataSourceRSA) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        Map<String, String> jpaPropertyMap = new HashMap<>();
        jpaPropertyMap.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        jpaPropertyMap.put("javax.persistence.transactionType", "JTA");
        entityManagerFactoryBean.setJpaPropertyMap(jpaPropertyMap);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setJtaDataSource(dataSourceRSA);
        entityManagerFactoryBean.setPackagesToScan("net.wang.jtatxmulti.domain.rsa");
        entityManagerFactoryBean.setPersistenceUnitName("rsaPersistenceUnit");
        return entityManagerFactoryBean;
    }
}
