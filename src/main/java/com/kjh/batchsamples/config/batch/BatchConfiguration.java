package com.kjh.batchsamples.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    private final DataSource dataSource;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public JobRepositoryFactoryBean jobRepositoryFactoryBean(){
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        // 추가 설정이 필요하면 여기에 작성
        return factory;
    }
}
