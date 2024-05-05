package com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount;

import com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

/**
 *
 * ItemWriterListener 는 ItemWriter 가 동작하기 전, 후에 처리할 로직을 정의 할 수 있다.
 * StepExecutionListener 를 구현하여 ExecutionContext 에 저장된 값을 가져올 수 있다.
 * StepExecutionListener 를 구현하여 Step 이 종료된 후 처리할 로직을 정의 할 수 있다.
 *
 * ItemWriter 처리후 청크단위 실행시마다 해당 중간 누계값들을 StepExecution 에 저장함.
 * Step 종료 후 StepExecution 에 저장된 값을 가져와서 총 누계값을 계산함.
 */
@Component
@StepScope
@Slf4j
public class ItemCountWriterListener implements ItemWriteListener<Employee>, StepExecutionListener{
    private StepExecution stepExecution;


    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;

    }


    // StepExecutionListener 인터페이스의 afterStep 메소드도 구현해야 합니다.
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }


    /**
     * 쓰기 전 처리
     * @param items
     */
    @Override
    public void beforeWrite(Chunk<? extends Employee> items) {

    }

    /**
     * 쓰기 후 처리
     * @param items
     */
    @Override
    public void afterWrite(Chunk<? extends Employee> items) {
        // 기존의 ExecutionContext 에 저장된 totalChunkAge 를 가져온다.
        int chunkTotalAge = stepExecution.getExecutionContext().getInt("chunkTotalAge", 0);

        /**
         * 한 청크 당 totalAge 를 계산 하여 ExecutionContext 에 저장 한다.
         */
        int chunkAge = items.getItems().stream()
                .mapToInt(employee -> Integer.parseInt(employee.getAge()))
                .sum();

        chunkTotalAge += chunkAge;

        stepExecution.getExecutionContext().putInt("chunkTotalAge", chunkTotalAge);
    }


    @Override
    public void onWriteError(Exception exception, Chunk<? extends Employee> items) {
        // 쓰기 오류 발생 시 로직 (필요 시)
    }
}
