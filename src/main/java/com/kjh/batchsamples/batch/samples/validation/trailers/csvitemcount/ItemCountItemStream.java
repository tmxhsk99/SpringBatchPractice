package com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount;


/**
 * ItemStream 인터페이스를 구현하여 스트림 처리를 구현한다.
 *
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

@Slf4j
public class ItemCountItemStream implements ItemStream {

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        ItemStream.super.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // 주기적으로 레코드 수 기록
        log.info("Item count: {}", executionContext.get("FlatFileItemReader.read.count"));

    }

    @Override
    public void close() throws ItemStreamException {

    }

    // Job에 Stream 코드 적용시

    /**
     * 예시 다음과 같이 적용할 것
     *     @Bean
     *     public Step ItemCountStep1 () {
     *         return new StepBuilder("itemCountStep1", jobRepository())
     *                 .<Employee, Employee>chunk(1, getTransactionManager())
     *                 .reader(csvItemCountReader())
     *                 .writer(csvItemConsoleWriter())
     *                 .stream(csvItemCountStream())
     *                 .build();
     *     }
     */
}
