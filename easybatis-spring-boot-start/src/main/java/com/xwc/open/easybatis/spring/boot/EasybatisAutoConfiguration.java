package com.xwc.open.easybatis.spring.boot;


import com.xwc.open.easybatis.core.EasybatisProperties;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(EasybatisProperties.class)
public class EasybatisAutoConfiguration {
}
