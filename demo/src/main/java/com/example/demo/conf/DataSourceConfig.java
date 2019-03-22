package com.example.demo.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@MapperScan(basePackages="com.example.demo.service.mapper")
@EnableTransactionManagement
public class DataSourceConfig {
	
     @Bean
     public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
    	 SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    	 sqlSessionFactory.setDataSource(ds);
    	 sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap/*Sql.xml"));
    	 return (SqlSessionFactory) sqlSessionFactory.getObject();
     }
     
//     mybatis batch  전용으로 따로 빼야 되는 경우 사용 하면 됨
//     @Bean
//     public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
//    	 return new SqlSessionTemplate(sqlSessionFactory);
//     }
//     
//     @Bean(name="sqlBatchSession")
//     public SqlSessionTemplate sqlBatchSession(SqlSessionFactory sqlSessionFactory) {
//         return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
//     }

	/*
	 * @Autowired private DataSourceProperties properties;
	 * 
	 * @Bean(destroyMethod="close") public DataSource dataSource() { HikariConfig
	 * config = new HikariConfig(); config.setMaximumPoolSize(5); //나중에 변경확인
	 * config.setDriverClassName(properties.getDriverClassName());
	 * config.setJdbcUrl(properties.getUrl());
	 * config.setUsername(properties.getUsername());
	 * config.setPassword(properties.getPassword());
	 * config.setPoolName("Common-HikariCP-Pool"); //
	 * config.addDataSourceProperty("useServerPrepStmts", "true"); //
	 * config.addDataSourceProperty("cachePrepStmts", "true"); //
	 * config.addDataSourceProperty("prepStmtCacheSize", "250"); //
	 * config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); return new
	 * HikariDataSource(config); }
	 */
     
}
