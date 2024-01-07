package com.kjh.batchsamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BatchsamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchsamplesApplication.class, args);
	}

}
