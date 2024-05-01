package com.kjh.batchsamples.batch.samples.validation.trailers.csvitemcount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

@Slf4j
public class ItemCountListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {

    }

    @Override
    public void afterChunk(ChunkContext context) {
        long readCount = context.getStepContext().getStepExecution().getReadCount();
        log.info("Read count: {}", readCount);
    }

    @Override
    public void afterChunkError(ChunkContext context) {

    }
}
