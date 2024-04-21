package com.kjh.batchsamples.batch.samples.validation.trailers;

import com.kjh.batchsamples.batch.service.DummyApiTransService;
import com.kjh.batchsamples.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;
import java.util.Map;

/**
 * Trailer Sample Job Configuration
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TrailerSampleJobConfig extends DefaultBatchConfiguration {

    private final DummyApiTransService dummyApiTransService;
    private String TrailerSampleJobOutputFilePath ="D:/project/SpringBatchPractice/file/trailer";

    @Bean
    public Job trailerSampleJob(Step trailerStep) {
        Job trailerJob = new JobBuilder("trailerSampleJob", jobRepository())
                .start(trailerStep)
                .build();
        return trailerJob;
    }

    /**
     * 전체 해당 job의 트레일러를 생성한다.
     * @return
     */
    @Bean
    public Step trailerStep() throws Exception {

        return new StepBuilder("trailerStep", jobRepository())
                .<Map<String, Object>, Map<String, Object>>chunk(1,getTransactionManager())
                .reader(trailerItemReader())
                .processor(trailerItemProcessor())
                .writer(trailerItemWriter())
                .build();

    }

    /**
     * 더미 아이템을 읽어서 반환한다.
     * @return
     */
    @Bean
    public ItemReader<Map<String,Object>> trailerItemReader() throws Exception {
        // ItemReader 구현체를 생성하고 반환 합니다.
        List<Map<String, Object>> returnTrxList = dummyApiTransService.transTossTrxApi();

        if(returnTrxList == null || returnTrxList.size() == 0) {
            log.info("거래 대사 데이터 없음");
            return null;
        }

        return new IteratorItemReader<>(dummyApiTransService.transTossTrxApi());

    }

    /**
     * 읽은 데이터를 내부에서 사용하는 형식으로 파싱한다.
     * @return
     */
    @Bean
    public ItemProcessor<Map<String,Object>, Map<String,Object>> trailerItemProcessor() {
        return item -> {
            log.info("##### TrailerItemProcessor Start #####");
            log.info("item : {}", item);
            log.info("##### TrailerItemProcessor End #####");
            return item;
        };
    }

    @Bean
    public ItemWriter<Map<String,Object>> trailerItemWriter() {
        String yyyyMMdd = CommonUtil.getCurDay_yyyyMMdd();
        String fileName = TrailerSampleJobOutputFilePath + "/trailer_sample_" + yyyyMMdd + ".txt";
        return items -> {
            log.info("##### TrailerItemWriter Start #####");
            log.info("items : {}", items);

            FlatFileItemWriter<Map<String, Object>> writer = new FlatFileItemWriter<>();
            writer.setResource(new FileSystemResource(fileName));

            DelimitedLineAggregator<Map<String, Object>> lineAggregator = new DelimitedLineAggregator<>();

            FieldExtractor<Map<String, Object>> fieldExtractor = item -> item.keySet().stream().toArray(String[]::new);
            lineAggregator.setFieldExtractor(fieldExtractor);

            writer.setLineAggregator(lineAggregator);

            log.info("##### TrailerItemWriter End #####");
        };
    }

}
