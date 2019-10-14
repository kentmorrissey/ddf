package ddf.catalog.content.operation;

import java.util.List;

public interface ProcessingReport {

  String getSourceId();

  List<String> getInformation();
}
