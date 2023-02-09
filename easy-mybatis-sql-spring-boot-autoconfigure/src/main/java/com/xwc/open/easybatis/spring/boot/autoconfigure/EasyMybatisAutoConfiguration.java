//package com.xwc.open.easybatis.spring.boot.autoconfigure;
//
//import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
//import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfiguration;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//
//import javax.sql.DataSource;
//
///**
// * 类描述：
// * 作者：徐卫超 (cc)
// * 时间 2023/2/9 15:06
// */
//@org.springframework.context.annotation.Configuration
//@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
//@ConditionalOnSingleCandidate(DataSource.class)
//@EnableConfigurationProperties(MybatisProperties.class)
//@AutoConfigureAfter({DataSourceAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class})
//public class EasyMybatisAutoConfiguration {
//}
