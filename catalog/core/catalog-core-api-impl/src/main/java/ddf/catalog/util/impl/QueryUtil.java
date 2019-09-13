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
package ddf.catalog.util.impl;

import ddf.catalog.data.Result;
import ddf.catalog.operation.ProcessingDetails;
import ddf.catalog.operation.QueryResponse;
import ddf.catalog.operation.SourceResponse;
import ddf.catalog.operation.impl.QueryResponseImpl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryUtil {

  public static void copyProcessingDetails(SourceResponse source, QueryResponseImpl destination) {
    if (source.getProcessingDetails() == null) {
      destination.setProcessingDetails(new HashSet<>());
    } else {
      destination.setProcessingDetails((Set<ProcessingDetails>) source.getProcessingDetails());
      System.out.println("arbitrary line used to debug");
    }
  }

  public static QueryResponse clone(SourceResponse sourceResponse, boolean closeResultQueue) {
    QueryResponseImpl clonedResponse =
        new QueryResponseImpl(
            sourceResponse.getRequest(),
            sourceResponse.getResults(),
            closeResultQueue,
            sourceResponse.getHits(),
            sourceResponse.getProperties());
    copyProcessingDetails(sourceResponse, clonedResponse);
    return clonedResponse;
  }

  public static QueryResponse clone(
      SourceResponse sourceResponse, boolean closeResultQueue, List<Result> results) {
    QueryResponseImpl clonedResponse =
        new QueryResponseImpl(
            sourceResponse.getRequest(),
            results,
            closeResultQueue,
            sourceResponse.getHits(),
            sourceResponse.getProperties());
    copyProcessingDetails(sourceResponse, clonedResponse);
    return clonedResponse;
  }
}
