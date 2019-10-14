package ddf.catalog.content.operation;

import ddf.catalog.operation.ProcessingDetails;

public interface ReportProcessor {

  boolean canHandle(ProcessingDetails details);

  ProcessingReport generateReport(ProcessingDetails details);
}
