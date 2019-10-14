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
package ddf.catalog.errorreporter.impl;

import com.google.common.collect.ImmutableMap;
import ddf.catalog.content.operation.ReportProcessor;
import ddf.catalog.operation.ProcessingDetails;
import ddf.catalog.operation.impl.ProcessingReportImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportProcessorImpl implements ReportProcessor {

  private static Map<String, String> mapOfStrings = ImmutableMap.of("Your query has failed because it contains invalid attribute", "Your query has failed because it contains invalid attribute");

  public boolean canHandle(ProcessingDetails details) {
    if (details != null
        && details.getSourceId() != null
        && details.getSourceId().equals("NDL-East")
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

    Set<String> keysOfMapOfStrings = mapOfStrings.keySet();
    for (String warning : warnings) {
      for(String key : keysOfMapOfStrings) {
        if(warning.contains(key)) {
          switch(key) {
            case "Your query has failed because it contains invalid attribute": //we want to specify the source in the information we give to ProcessingReportImpl here, but...????
              String[] invalidAttributes = warning.split(", and ");
//              for(String invalidAttribute : invalidAttributes) {
//                if()
//              }
          }
        }
      }
    }

    if (information.isEmpty()) {
      return null;
    }

    return new ProcessingReportImpl(details.getSourceId(), information);
  }
}
