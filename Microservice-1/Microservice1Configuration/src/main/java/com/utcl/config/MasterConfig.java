package com.utcl.config;

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
@PropertySource("classpath:m1data.properties")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "microservice1EntityManagerFactory", transactionManagerRef = "m1TransactionManager", basePackages = {
		"com.utcl.m1repo" })
public class MasterConfig {
	@Value("${m1.datasource.driverclassname}")
	String driverClassName;
	
	@Value("${m1.datasource.username}")
	String username;
	
	@Value("${m1.datasource.password}")
	String password;
	
	@Value("${m1.datasource.url}")
	String url;
	
	@Value("${m1.datasource.dialect}")
	String dialect;
	
	@Bean
	DataSourceUtility dataSourceUtility() {
		return new DataSourceUtility();
	};


	@Bean(name = "m1DataSource")
	public DataSource dataSource() {
		DataSourceUtility d = dataSourceUtility();
		return d.getDataSource(driverClassName, username, password, url);

	}
	//@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
	   return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
	}
	@Bean(name = "microservice1EntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean microservice1EntityManagerFactory(
			@Qualifier("m1DataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", dialect);
		return entityManagerFactoryBuilder().dataSource(dataSource).properties(properties)
				.packages("com.utcl.m1Entity").persistenceUnit("Microservice1Table").build();
	}
	
	@Bean(name = "m1TransactionManager")
	public PlatformTransactionManager m1TransactionManager(
			@Qualifier("microservice1EntityManagerFactory") EntityManagerFactory microservice1EntityManagerFactory) {
		return new JpaTransactionManager(microservice1EntityManagerFactory);
	}
	
}