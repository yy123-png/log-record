package com.logrecord.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yeyu
 * @since 2022-05-11 11:07
 * 旧数据查询执行器
 * 在容器启动完成后 会获取容器中所有的旧数据查询服务Bean
 */
@Slf4j
public class QueryOldExecutor implements ApplicationListener<ContextRefreshedEvent> {

    private final Map<String, IQueryOldService> executors = new HashMap<>(8);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, IQueryOldService> beans = applicationContext.getBeansOfType(IQueryOldService.class);
        beans.forEach((k,v) -> {
            log.info("register queryOldServiceBean named:{}",k);
            executors.put(v.key(),v);
        });
    }

    public Object execute(String key,Object param) {
        return executors.get(key).apply(param);
    }
}
