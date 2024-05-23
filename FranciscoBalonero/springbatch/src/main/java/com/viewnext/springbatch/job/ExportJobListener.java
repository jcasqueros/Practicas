package com.viewnext.springbatch.job;

import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * This class is responsible for listening to job execution events for the export job.
 *
 * <p>It extends Spring Batch's {@link JobExecutionListenerSupport} class to provide before and after job execution
 * methods.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class ExportJobListener extends JobExecutionListenerSupport {
    // No-op implementation
}
