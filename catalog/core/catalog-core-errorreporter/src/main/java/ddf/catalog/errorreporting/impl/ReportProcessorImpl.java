/**
 * Copyright (c) Codice Foundation
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package ddf.catalog.errorreporting.impl;

import com.google.common.collect.ImmutableMap;
import ddf.catalog.content.operation.ReportProcessor;
import ddf.catalog.operation.ProcessingDetails;
import ddf.catalog.operation.impl.ProcessingReportImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportProcessorImpl implements ReportProcessor {

  private static Map<String, String> mapOfStrings = ImmutableMap.of("hufflepuff", "foobar");

  public boolean canHandle(ProcessingDetails details) {
    if (details != null
        && details.getSourceId() != null
        && details.getSourceId().equals("NclCswSource")
        && details.getWarnings() != null) {
      return true;
    }

    return false;
  }

  public ProcessingReportImpl generateReport(ProcessingDetails details) {

    if (!canHandle(details)) {
      return null;
    }

    List<String> warnings = details.getWarnings();

    List<String> information = new ArrayList<>();
    for (String warning : warnings) {
      if (mapOfStrings.containsKey(warning)) {
        information.add(mapOfStrings.get(warning));
      }
    }

    if (information.isEmpty()) {
      return null;
    }

    return new ProcessingReportImpl(details.getSourceId(), information);
  }
}
