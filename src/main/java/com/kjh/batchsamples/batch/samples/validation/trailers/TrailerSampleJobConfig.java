package com.kjh.batchsamples.batch.samples.validation.trailers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjh.batchsamples.util.DummyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * Trailer Sample Job Configuration
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TrailerSampleJobConfig extends DefaultBatchConfiguration {

    private final ObjectMapper objectMapper;
    @Bean
    public Job trailerSampleJob(Step trailerStep) {
        Job trailerJob = new JobBuilder("trailerSampleJob", jobRepository())
                .start(trailerStep)
                .build();
        return trailerJob;
    }

    @Bean
    @StepScope
    public Step trailerStep() {
        return null;
    }


    @Bean
    public ItemReader<Map<String,Object>> trailerItemReader() {
        // ItemReader 구현체를 생성하고 반환 합니다.
        ItemReader<Map<String, Object>> trailerItemReader = new ItemReader<>() {
            @Override
            public Map<String, Object> read() throws Exception {
                List<Map<String, Object>> maps = transTossTrxApi();
                if (maps.size() > 0) {
                    for (Map<String, Object> map : maps) {
                        return map;
                    }
                }
                return null;
            }
        };

        return trailerItemReader;
    }

    @Bean
    public ItemProcessor<Map<String,Object>, Map<String,Object>> trailerItemProcessor() {
        // ItemProcessor 구현체를 생성하고 반환합니다.
        return null;
    }

    @Bean
    public ItemWriter<Map<String,Object>> trailerItemwWriter() {
        // ItemWriter 구현체를 생성하고 반환합니다.
        return null;
    }


    private List<Map<String,Object>> transTossTrxApi() throws JsonProcessingException {
        // API 호출 했다 치자
        List<Map<String,Object>> resultList = objectMapper.readValue(DummyUtil.DUMMY_TRX_RESULT, List.class);

        return resultList;
    }
}
