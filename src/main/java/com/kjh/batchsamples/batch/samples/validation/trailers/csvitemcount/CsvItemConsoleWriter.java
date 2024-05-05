package com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount;

import com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class CsvItemConsoleWriter implements ItemWriter<Employee>, StepExecutionListener, ItemWriteListener<Employee> {
    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void write(Chunk<? extends Employee> chunk) throws Exception {
        chunk.getItems().forEach(employee -> log.info(employee.toString()));
    }

}
