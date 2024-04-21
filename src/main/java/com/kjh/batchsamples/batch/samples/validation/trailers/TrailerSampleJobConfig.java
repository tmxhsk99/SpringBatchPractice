package com.kjh.batchsamples.batch.samples.validation.trailers;

import com.kjh.batchsamples.batch.samples.validation.trailers.litener.TrailerSampleItemWriterListener;
import com.kjh.batchsamples.batch.samples.validation.trailers.litener.TrailerSampleJobListener;
import com.kjh.batchsamples.batch.service.DummyApiTransService;
import com.kjh.batchsamples.util.CommonUtil;
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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Trailer Sample Job Configuration
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class TrailerSampleJobConfig extends DefaultBatchConfiguration {

    private final TrailerSampleJobListener trailerSampleJobListener;
    private final DummyApiTransService dummyApiTransService;
    private final String ITEM_SEPERATOR = " | ";

    public static String TrailerSampleJobOutputFilePath ="D:/project/SpringBatchPractice/file/trailer";

    @Bean
    public Job trailerSampleJob(Step trailerStep) {
        Job trailerJob = new JobBuilder("trailerSampleJob", jobRepository())
                .start(trailerStep)
                .listener(trailerSampleJobListener)
                .build();
        return trailerJob;
    }

    /**
     * 전체 해당 job의 트레일러를 생성한다.
     * @return
     */
    @Bean
    public Step trailerStep(FlatFileItemWriter trailerItemWriter, TrailerSampleItemWriterListener trailerSampleItemWriterListener) throws Exception {

        return new StepBuilder("trailerStep", jobRepository())
                .<Map<String, Object>, Map<String, Object>>chunk(1,getTransactionManager())
                .reader(trailerItemReader())
                .processor(trailerItemProcessor())
                .writer(trailerItemWriter)
                .listener(trailerSampleItemWriterListener)
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
     * 현재는 그냥 로그만 찍는다.
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

    /**
     * 지정된 파일 경로에 데이터를 쓴다.
     * @return
     */
    @Bean
    @StepScope
    public FlatFileItemWriter<Map<String, Object>> trailerItemWriter(
            @Value("#{jobParameters['mid']}") String mid, // 거래대사 요청 mid
            @Value("#{jobParameters['startTrxDt']}") String startTrxDt, // 거래대사 조회 시작 거래 일자
            @Value("#{jobParameters['startTrxDt']}") String endTrxDt // 거래대사 조회 종료 거래 일자

    ) {
        String fileName = createTrxBatchFileName();

        FlatFileItemWriter<Map<String, Object>> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(fileName));

        // header 로우 생성
        writer.setHeaderCallback(writer1 -> writer1.write("00"  + ITEM_SEPERATOR + mid + ITEM_SEPERATOR + startTrxDt + ITEM_SEPERATOR + endTrxDt));

        // Body 로우 생성
        FieldExtractor<Map<String, Object>> fieldExtractor = item -> {

            if(item != null){
                Set<String> ketSet = item.keySet();

                Object[] lineValue = ketSet.stream()
                        .map(key -> item.get(key))  // key를 이용하여 value를 가져온다.
                        .toArray();

                return lineValue;
            }
            return null;
        };

        DelimitedLineAggregator<Map<String, Object>> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(ITEM_SEPERATOR);
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);

        return writer;
    }

    public static String createTrxBatchFileName() {
        String yyyyMMdd = CommonUtil.getCurDay_yyyyMMdd();
        String fileName = TrailerSampleJobOutputFilePath + "/trailer_sample_" + yyyyMMdd + ".txt";
        return fileName;
    }


}
