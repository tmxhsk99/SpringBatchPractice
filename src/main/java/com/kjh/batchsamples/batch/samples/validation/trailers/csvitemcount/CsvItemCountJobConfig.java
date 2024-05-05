package com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount;

import com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount.model.Employee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CsvItemCountJobConfig extends DefaultBatchConfiguration {

    private final ItemCountWriterListener itemCountWriterListener;

    /**
     * 읽어 들일 csv 파일
     */
    private Resource sampleCsvResource = new FileSystemResource("D:/project/SpringBatchPractice/file/trailer/read_resource/smaple.csv");

    @Bean
    public Job CsvItemCountJob() {
        return new JobBuilder("csvItemCountJob", jobRepository())
                .incrementer(new RunIdIncrementer())
                .start(ItemCountStep1 ())
                .listener(jobExecutionListener())
                .build();
    }

    @Bean
    public Step ItemCountStep1 () {
        return new StepBuilder("itemCountStep1", jobRepository())
                .<Employee, Employee>chunk(1, getTransactionManager())
                .reader(csvItemCountReader())
                .writer(csvItemConsoleWriter())
                .listener((StepExecutionListener) itemCountWriterListener)
                .listener((ItemWriteListener<? super Employee>) itemCountWriterListener)
                .build();
    }
    @Bean
    public StepExecutionListener stepExecutionListener(){
        return this.itemCountWriterListener;
    }
    @Bean
    public ItemCountChunkListener chunkListener(){
        return new ItemCountChunkListener();
    }

    @Bean
    public ItemWriteListener writerListener(){
        return this.itemCountWriterListener;
    }

    @Bean
    public ItemCountJobExecutionListener jobExecutionListener(){
        return new ItemCountJobExecutionListener();
    }

    @Bean
    public ItemCountItemStream csvItemCountStream(){
        return new ItemCountItemStream();
    }

    @Bean
    public FlatFileItemReader<Employee> csvItemCountReader(){
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(sampleCsvResource);
        return itemReader;
    }


    @Bean
    public LineMapper<Employee> lineMapper() {
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setNames(new String[] {"id","firstName","lastName","age"});
        lineTokenizer.setIncludedFields(new int[] {0, 1, 2, 3});

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    /**
     * 콘솔로 읽은 데이터를 출력한다.
     * @return
     */
    @Bean
    public ItemWriter<Employee> csvItemConsoleWriter(){
        return items -> items.forEach(item -> log.info(item.toString()));
    }

}
