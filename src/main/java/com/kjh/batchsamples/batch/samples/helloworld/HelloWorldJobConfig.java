package com.kjh.batchsamples.batch.samples.helloworld;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hello, World! Job Configuration
 * job은 JobRepository를 주입받아야하며, step은 jobRepository와 transactionManager를 주입받아야 한다.
 * 수동으로 설정해줄수도 있지만 그냥 DefaultBatchConfiguration을 상속받는 형태로 구현함
 * EnableBatchProcessing 어노테이션을 사용해서 명시적으로 jobRepository, transactionManager를 주입받을 수도 있음
 *
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class HelloWorldJobConfig extends DefaultBatchConfiguration {

	@Bean
	public Job helloWorldJob(Step helloWorldStep) {
		Job job = new JobBuilder("helloWorldJob", jobRepository())
				.start(helloWorldStep)
				.build();
		return job;
	}

	@Bean
	public Step helloWorldStep() {
		TaskletStep step = new StepBuilder("helloWorldStep", jobRepository())
				.tasklet((contribution, chunkContext) -> {
					log.info("##### HelloWordStep Start #####");
					log.info("Hello, World!");
					log.info("##### HelloWordStep End #####");
					return RepeatStatus.FINISHED;
				}, getTransactionManager()).build();

		return step;
	}

}
