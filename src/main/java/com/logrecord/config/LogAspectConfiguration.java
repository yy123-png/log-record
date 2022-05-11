package com.logrecord.config;

import com.logrecord.aop.LogAspect;
import com.logrecord.parser.LogParser;
import com.logrecord.service.QueryOldExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yeyu
 * @since 2022-05-11 11:21
 */
@Configuration
@EnableAspectJAutoProxy
public class LogAspectConfiguration {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public LogParser logParser() {
        return new LogParser();
    }

    @Bean
    public QueryOldExecutor queryOldExecutor() {
        return new QueryOldExecutor();
    }
}
