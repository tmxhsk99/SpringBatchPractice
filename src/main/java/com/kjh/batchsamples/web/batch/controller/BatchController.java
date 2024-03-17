package com.kjh.batchsamples.web.batch.controller;


import com.kjh.batchsamples.web.batch.model.BatchRunReq;
import com.kjh.batchsamples.web.batch.service.BatchRunService;
import com.kjh.batchsamples.web.common.model.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kjh.batchsamples.util.MessageUtil.createSuccessResult;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchRunService batchRunService;

    /**
     * 배치를 웹으로 요청 받아 수동으로 실행 한다.
     * @return
     */
    @PostMapping("/run")
    public ApiResult batchHandWriteRun(@RequestBody BatchRunReq batchRunReq){
        batchRunService.run(batchRunReq);
        return createSuccessResult();
    }

}
