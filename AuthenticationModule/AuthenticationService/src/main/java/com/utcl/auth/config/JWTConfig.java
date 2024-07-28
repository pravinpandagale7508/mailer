package com.utcl.auth.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.utcl.DataSourceUtility;
import com.utcl.auth.filter.JwtAuthFilter;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:jwtdata.properties")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "jwtEntityManagerFactory", transactionManagerRef = "jwtTransactionManager", basePackages = {
		"com.utcl.auth.repository" })
public class JWTConfig {
	@Value("${jwt.datasource.driverclassname}")
	String driverClassName;

	@Value("${jwt.datasource.username}")
	String username;

	@Value("${jwt.datasource.password}")
	String password;

	@Value("${jwt.datasource.url}")
	String url;

	@Value("${jwt.datasource.dialect}")
	String dialect;

	@Autowired
	private JwtAuthFilter authFilter;

	// @Bean
	DataSourceUtility dataSourceUtility() {
		return new DataSourceUtility();
	}

	@Bean(name = "jwtDataSource")
	public DataSource dataSource() {
		DataSourceUtility d = dataSourceUtility();
		return d.getDataSource(driverClassName, username, password, url);

	}

	// @Bean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
	}

	@Bean(name = "jwtEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean jwtEntityManagerFactory(
			@Qualifier("jwtDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", dialect);
		return entityManagerFactoryBuilder().dataSource(dataSource).properties(properties)
				.packages("com.utcl.auth.entity").persistenceUnit("RefreshToken").build();
	}

	@Bean(name = "jwtTransactionManager")
	public PlatformTransactionManager jwtTransactionManager(
			@Qualifier("jwtEntityManagerFactory") EntityManagerFactory jwtEntityManagerFactory) {
		return new JpaTransactionManager(jwtEntityManagerFactory);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(requests -> requests.requestMatchers("/user/signUp", "/user/login",
						"/user/refreshToken", "/user/generate_refresh_token/{userId}",
						"/user/generate_access_token/{userId}", "/user/is_valid_token/{accessToken}").permitAll())
				.authorizeHttpRequests(requests -> requests.requestMatchers("/user/**").authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}