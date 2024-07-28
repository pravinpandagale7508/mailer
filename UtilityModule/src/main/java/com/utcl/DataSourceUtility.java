package com.utcl;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class DataSourceUtility {
	private HashMap<String, DataSource> dataSources = new HashMap<>();
	public DataSource getDataSource(String driverClassName,String username,String password,String url) {
        if (dataSources.get(driverClassName) != null) {
            return dataSources.get(driverClassName);
        }
        DataSource dataSource = createDataSource(driverClassName, username, password, url);
        if (dataSource != null) {
            dataSources.put(driverClassName, dataSource);
        }
        return dataSource;
    }
	private DataSource createDataSource(String driverClassName,String username,String password,String url) {
        if (driverClassName != null &&username != null && password != null && url != null ) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(driverClassName)
                    .username(username)
                    .password(password)
                    .url(url);
            DataSource ds = factory.build();
//            if (config.getInitialize()) {
//                initialize(ds);
//            }
            return ds;
        }
        return null;
    }
}
