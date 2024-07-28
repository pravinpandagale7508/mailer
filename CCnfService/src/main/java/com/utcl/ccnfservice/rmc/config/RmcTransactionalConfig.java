package com.utcl.ccnfservice.rmc.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@EnableJpaRepositories(entityManagerFactoryRef = "rmcTransactionEntityManagerFactory", transactionManagerRef = "rmcTransactionManager", basePackages = {
		"com.utcl.ccnfservice.rmc.transaction.repo" })
public class RmcTransactionalConfig {
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


	@Bean(name = "rmcTransactionDataSource")
	public DataSource dataSource() {
		DataSourceUtility d = dataSourceUtility();
		return d.getDataSource(driverClassName, username, password, url);

	}
	//@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
	   return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
	}
	@Bean(name = "rmcTransactionEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean rmcTransactionEntityManagerFactory(
			@Qualifier("rmcTransactionDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", dialect);
		return entityManagerFactoryBuilder().dataSource(dataSource).properties(properties)
				.packages("com.utcl.ccnfservice.rmc.transaction.entity").persistenceUnit("CCnfCementLoi").build();
	}
	
	@Bean(name = "rmcTransactionManager")
	public PlatformTransactionManager rmcTransactionManager(
			@Qualifier("rmcTransactionEntityManagerFactory") EntityManagerFactory rmcTransactionEntityManagerFactory) {
		return new JpaTransactionManager(rmcTransactionEntityManagerFactory);
	}
	
}