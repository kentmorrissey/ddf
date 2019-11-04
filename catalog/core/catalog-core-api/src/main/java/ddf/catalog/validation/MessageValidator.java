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
package ddf.catalog.validation;

import ddf.catalog.operation.ProcessingDetails;
import java.util.Set;

public interface MessageValidator {

  /**
   * Get the identifier of this validator
   *
   * @return the identifier, or the empty {@link String} when no identifier exists
   */
  String getId();

  /**
   * Determine whether this {@link MessageValidator} has the capacity to validate the message(s) of
   * the {@link ProcessingDetails} passed to it. If it has, examine the {@link ProcessingDetails}'s
   * message(s) and create a {@link Set} composed exclusively of the valid message(s) found in the
   * {@link ProcessingDetails}.
   *
   * @param details - contains the message(s) which this method validates
   * @return a {@link Set} composed exclusively of the parameter's valid message(s), or an empty
   *     {@link Set} if the parameter contains no valid message
   */
  Set<String> validate(ProcessingDetails details);
}
