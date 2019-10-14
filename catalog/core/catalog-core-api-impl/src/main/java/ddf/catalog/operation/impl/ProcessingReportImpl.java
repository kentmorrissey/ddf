package ddf.catalog.operation.impl;

import ddf.catalog.content.operation.ProcessingReport;
import java.util.List;

public class ProcessingReportImpl implements ProcessingReport {

  private final String sourceId;

  private final List<String> information;

  public ProcessingReportImpl(String sourceId, List<String> information) {
    this.sourceId = sourceId;
    this.information = information;
  }

  public String getSourceId() {
    return sourceId;
  }

  public List<String> getInformation() {
    return information;
  }
}
