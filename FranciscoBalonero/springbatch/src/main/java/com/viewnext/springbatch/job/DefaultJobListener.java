package com.viewnext.springbatch.job;

import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * This class is a default job listener.
 *
 * <p>It extends Spring Batch's {@link JobExecutionListenerSupport} class to provide before and after job execution
 * methods.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class DefaultJobListener extends JobExecutionListenerSupport {
    // No-op implementation
}
