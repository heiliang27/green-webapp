package com.green.config.properties;

import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProperties {	
	
	public void greenConfig(DruidDataSource dataSource){
		this.pool(dataSource);
		dataSource.setUrl(this.green.getUrl());
		dataSource.setUsername(this.green.getUsername());
		dataSource.setPassword(this.green.getPassword());
		dataSource.setDriverClassName(this.green.getDriverClassName());
	}
	
	public void hadesConfig(DruidDataSource dataSource){
		this.pool(dataSource);
		dataSource.setUrl(this.hades.getUrl());
		dataSource.setUsername(this.hades.getUsername());
		dataSource.setPassword(this.hades.getPassword());
		dataSource.setDriverClassName(this.hades.getDriverClassName());
	}
	
	private void pool(DruidDataSource dataSource) {		
		dataSource.setInitialSize(initialSize); // 定义初始连接数
		dataSource.setMinIdle(minIdle); // 最小空闲
		dataSource.setMaxActive(maxActive); // 定义最大连接数
		dataSource.setMaxWait(maxWait); // 最长等待时间
		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		// 配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);
		// 打开PSCache，并且指定每个连接上PSCache的大小
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Integer initialSize = 2;

	private Integer minIdle = 1;

	private Integer maxActive = 20;

	private Integer maxWait = 60000;

	private Integer timeBetweenEvictionRunsMillis = 60000;

	private Integer minEvictableIdleTimeMillis = 300000;

	private String validationQuery = "SELECT 'x'";

	private Boolean testWhileIdle = true;

	private Boolean testOnBorrow = false;

	private Boolean testOnReturn = false;

	private Boolean poolPreparedStatements = true;

	private Integer maxPoolPreparedStatementPerConnectionSize = 20;

	private String filters = "stat";
	
	private Green green = new Green();
	
	private Hades hades = new Hades();
	
	@Data
	private static class Green{
		private String url = "jdbc:mysql://qdm170159589.my3w.com/qdm170159589_db?useUnicode=true&characterEncoding=UTF8";

		private String username = "qdm170159589";

		private String password = "12345678";

		private String driverClassName = "com.mysql.jdbc.Driver";
	}
	
	@Data
	private static class Hades{
		
		private String url = "jdbc:mysql://qdm170159589.my3w.com/qdm170159589_db?useUnicode=true&characterEncoding=UTF8";

		private String username = "qdm170159589";

		private String password = "12345678";

		private String driverClassName = "com.mysql.jdbc.Driver";
	}
}
