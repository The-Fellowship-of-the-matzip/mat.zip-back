package com.woowacourse.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class ReviewFailoverJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        log.info("[Batch Job Start] Review Failover Batch Job : id = {}, start time = {}",
                jobExecution.getId(),
                jobExecution.getStartTime());
    }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        log.info("[Batch Job End] Review Failover Batch Job : id = {}, start time = {}",
                jobExecution.getId(),
                jobExecution.getStartTime());
    }
}
