package com.kjh.batchsamples.batch.samples.validation.trailers.litener;

import com.kjh.batchsamples.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TrailerSampleItemWriterListener<T> implements ItemWriteListener<Map<String, Object>>{
    private StepExecution stepExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    @Override
    public void beforeWrite(Chunk<? extends Map<String, Object>> items) {
        ItemWriteListener.super.beforeWrite(items);
    }

    @Override
    public void afterWrite(Chunk<? extends Map<String, Object>> items) {
        int count = items != null ? items.size() : 0;
        if (stepExecution != null) {
            // 기존에 저장된 카운트를 가져옵니다
            Integer existingCount = stepExecution.getExecutionContext().containsKey("itemsWritten")
                    ? (Integer) stepExecution.getExecutionContext().getInt("itemsWritten")
                    : 0;

            // 새로운 총합을 계산
            stepExecution.getExecutionContext().put("itemsWritten", existingCount + count);

            // 기존 총 거래 금액을 가져온다.
            Long existingTotalAmount = stepExecution.getExecutionContext().containsKey("totalAmount")
                    ? (Long) stepExecution.getExecutionContext().getLong("totalAmount")
                    : 0L;

            // 새로운 총 거래 금액을 계산
            List<Map<String, Object>> itemsList = (items != null && items.getItems() != null)
                    ? (List<Map<String, Object>>) items.getItems()
                    : new ArrayList<>();

            // Stream을 사용해서 Map의 amount를 가져와서 더한다.
            long currentAmount = itemsList.stream()
                    .filter(item -> item.containsKey("amount") && item.get("amount") != null)
                    .mapToLong(item -> Long.getLong(item.get("amount").toString()))
                    .sum();
            stepExecution.getExecutionContext().put("totalAmount", existingTotalAmount + currentAmount);
        }
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends Map<String, Object>> items) {
        log.error("TrailerRecordFooterListener onProcessError : {}", MessageUtil.parseErrorMsgFormat(exception));
    }

}
