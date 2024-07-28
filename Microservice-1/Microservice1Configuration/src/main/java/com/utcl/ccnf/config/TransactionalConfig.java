package com.utcl.ccnf.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.utcl.DataSourceUtility;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@PropertySource("classpath:transactiondata.properties")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "transactionEntityManagerFactory", transactionManagerRef = "transactionTransactionManager", basePackages = {
		"com.utcl.transactionrepo" })
public class TransactionalConfig {
	@Value("${transaction.datasource.driverclassname}")
	String driverClassName;
	
	@Value("${transaction.datasource.username}")
	String username;
	
	@Value("${transaction.datasource.password}")
	String password;
	
	@Value("${transaction.datasource.url}")
	String url;
	
	@Value("${transaction.datasource.dialect}")
	String dialect;
	
	//@Bean
	DataSourceUtility dataSourceUtility() {
		return new DataSourceUtility();
	};


	@Bean(name = "transactionDataSource")
	public DataSource dataSource() {
		DataSourceUtility d = dataSourceUtility();
		return d.getDataSource(driverClassName, username, password, url);

	}
	//@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
	   return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
	}
	@Bean(name = "transactionEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean transactionEntityManagerFactory(
			@Qualifier("transactionDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", dialect);
		return entityManagerFactoryBuilder().dataSource(dataSource).properties(properties)
				.packages("com.utcl.transactionEntity").persistenceUnit("VendorTrans").build();
	}
	
	@Bean(name = "transactionTransactionManager")
	public PlatformTransactionManager transactionTransactionManager(
			@Qualifier("transactionEntityManagerFactory") EntityManagerFactory transactionEntityManagerFactory) {
		return new JpaTransactionManager(transactionEntityManagerFactory);
	}
	
}