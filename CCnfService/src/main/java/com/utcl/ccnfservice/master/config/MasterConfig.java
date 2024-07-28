package com.utcl.ccnfservice.master.config;

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
@PropertySource("classpath:masterdata.properties")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "masterEntityManagerFactory", transactionManagerRef = "masterTransactionManager", basePackages = {
		"com.utcl.ccnfservice.master.repo" })
public class MasterConfig {
	@Value("${master.datasource.driverclassname}")
	String driverClassName;
	
	@Value("${master.datasource.username}")
	String username;
	
	@Value("${master.datasource.password}")
	String password;
	
	@Value("${master.datasource.url}")
	String url;
	
	@Value("${master.datasource.dialect}")
	String dialect;
	
	//@Bean
	DataSourceUtility dataSourceUtility() {
		return new DataSourceUtility();
	};


	@Bean(name = "masterDataSource")
	public DataSource dataSource() {
		DataSourceUtility d = dataSourceUtility();
		return d.getDataSource(driverClassName, username, password, url);

	}
	//@Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
	   return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
	}
	@Bean(name = "masterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(
			@Qualifier("masterDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", dialect);
		return entityManagerFactoryBuilder().dataSource(dataSource).properties(properties)
				.packages("com.utcl.ccnfservice.master.entity").persistenceUnit("Agency").build();
	}
	
	@Bean(name = "masterTransactionManager")
	public PlatformTransactionManager masterTransactionManager(
			@Qualifier("masterEntityManagerFactory") EntityManagerFactory masterEntityManagerFactory) {
		return new JpaTransactionManager(masterEntityManagerFactory);
	}
	
}