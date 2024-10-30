package com.como.server.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.como.server.repository")
public class MapperConfig {

    @Value("${mybatis.mapper-locations}")
    private String MAPPER_LOCATIONS;

    @Value("${mybatis.config-location}")
    private String CONFIG_LOCATION;


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        Resource[] res = new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATIONS);
        sessionFactory.setMapperLocations(res);

        Resource configResource =
                new PathMatchingResourcePatternResolver().getResource(CONFIG_LOCATION);
        sessionFactory.setConfigLocation(configResource);

        return sessionFactory.getObject();
    }

}
