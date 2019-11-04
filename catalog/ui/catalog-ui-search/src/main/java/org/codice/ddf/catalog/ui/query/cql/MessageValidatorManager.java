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
package org.codice.ddf.catalog.ui.query.cql;

import com.google.common.annotations.VisibleForTesting;
import ddf.catalog.operation.ProcessingDetails;
import ddf.catalog.validation.MessageValidator;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MessageValidatorManager {

  private List<MessageValidator> validators;

  @VisibleForTesting
  MessageValidatorManager(List<MessageValidator> validators) {
    this.validators = validators;
  }

  void setValidators(List<MessageValidator> validators) {
    this.validators = validators;
  }

  Set<String> getValidWarningsFrom(ProcessingDetails processingDetails) {
    MessageValidator compatibleValidator = null;

    if (canInspect(processingDetails)) {
      compatibleValidator = getCompatibleValidatorOf(processingDetails);
    }

    return compatibleValidator == null
        ? Collections.emptySet()
        : compatibleValidator.validate(processingDetails);
  }

  private boolean canInspect(ProcessingDetails processingDetails) {
    return (processingDetails != null
        && processingDetails.getSourceId() != null
        && validators != null
        && !validators.isEmpty());
  }

  private MessageValidator getCompatibleValidatorOf(ProcessingDetails processingDetails) {
    for (MessageValidator validator : validators) {

      String idOfValidator = null;

      if (validator != null) {
        idOfValidator = validator.getId();
      }

      if (idOfValidator != null && idOfValidator.equals(processingDetails.getSourceId())) {
        return validator;
      }
    }

    return null;
  }
}
