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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ddf.catalog.operation.ProcessingDetails;
import ddf.catalog.validation.MessageValidator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class MessageValidatorManagerTest {

  private final ProcessingDetails mockedProcessingDetailsWithNullSourceId =
      mock(ProcessingDetails.class);

  private final ProcessingDetails mockedProcessingDetailsFromTestSource =
      mock(ProcessingDetails.class);

  private final MessageValidator mockedValidatorWithNullId = mock(MessageValidator.class);

  private final MessageValidator mockedValidatorOfNotTestSource = mock(MessageValidator.class);

  private final MessageValidator firstMockedValidatorOfTestSource = mock(MessageValidator.class);

  private Set validWarningsFromRunOfThisTest;

  private final Set emptySet = Collections.emptySet();

  private final Set<String> validWarningsFromFirstMockedValidatorOfTestSource = new HashSet<>();

  private final List<MessageValidator> emptyListOfValidators = Collections.emptyList();

  private final List<MessageValidator> listOfValidatorsWithNullEntry =
      Collections.singletonList(null);

  private final List<MessageValidator> orderedListOfValidators = new ArrayList<>();

  @Before
  public void setUp() {

    when(mockedProcessingDetailsWithNullSourceId.getSourceId()).thenReturn(null);

    String testSource = "test source";
    when(mockedProcessingDetailsFromTestSource.getSourceId()).thenReturn(testSource);

    when(mockedValidatorWithNullId.getId()).thenReturn(null);

    when(mockedValidatorOfNotTestSource.getId()).thenReturn("not test source");

    when(firstMockedValidatorOfTestSource.getId()).thenReturn(testSource);

    validWarningsFromFirstMockedValidatorOfTestSource.add(
        "Your query failed for this specific reason.");
    validWarningsFromFirstMockedValidatorOfTestSource.add(
        "Your query failed for a reason which we cannot specify.");

    when(firstMockedValidatorOfTestSource.validate(mockedProcessingDetailsFromTestSource))
        .thenReturn(validWarningsFromFirstMockedValidatorOfTestSource);

    MessageValidator secondMockedValidatorOfTestSource = mock(MessageValidator.class);
    when(secondMockedValidatorOfTestSource.getId()).thenReturn(testSource);

    orderedListOfValidators.add(null);
    orderedListOfValidators.add(mockedValidatorWithNullId);
    orderedListOfValidators.add(mockedValidatorOfNotTestSource);
    orderedListOfValidators.add(firstMockedValidatorOfTestSource);
    orderedListOfValidators.add(secondMockedValidatorOfTestSource);
  }

  @Test
  public void testNullProcessingDetails() {
    createManagerFromAndValidateMessagesOf(firstMockedValidatorOfTestSource, null);
    assertThat(validWarningsFromRunOfThisTest, is(emptySet));
  }

  @Test
  public void testProcessingDetailsWithNullSourceId() {
    createManagerFromAndValidateMessagesOf(
        firstMockedValidatorOfTestSource, mockedProcessingDetailsWithNullSourceId);
    assertThat(validWarningsFromRunOfThisTest, is(emptySet));
  }

  @Test
  public void testNullValidators() {
    createManagerFromAndValidateMessagesOf(
        (List<MessageValidator>) null, mockedProcessingDetailsFromTestSource);
    assertThat(validWarningsFromRunOfThisTest, is(emptySet));
  }

  @Test
  public void testEmptyValidators() {
    createManagerFromAndValidateMessagesOf(
        emptyListOfValidators, mockedProcessingDetailsFromTestSource);
    assertThat(validWarningsFromRunOfThisTest, is(emptySet));
  }

  @Test
  public void testValidatorsWithNullEntry() {
    createManagerFromAndValidateMessagesOf(
        listOfValidatorsWithNullEntry, mockedProcessingDetailsFromTestSource);
    assertThat(validWarningsFromRunOfThisTest, is(emptySet));
  }

  @Test
  public void testValidatorWithNullId() {
    createManagerFromAndValidateMessagesOf(
        mockedValidatorWithNullId, mockedProcessingDetailsFromTestSource);
    assertThat(validWarningsFromRunOfThisTest, is(emptySet));
  }

  @Test
  public void testProcessingDetailsWithUnsupportedSourceId() {
    createManagerFromAndValidateMessagesOf(
        mockedValidatorOfNotTestSource, mockedProcessingDetailsFromTestSource);
    assertThat(validWarningsFromRunOfThisTest, is(emptySet));
  }

  @Test
  public void testProcessingDetailsWithSupportedSourceId() {
    createManagerFromAndValidateMessagesOf(
        firstMockedValidatorOfTestSource, mockedProcessingDetailsFromTestSource);
    assertThat(
        validWarningsFromRunOfThisTest, is(validWarningsFromFirstMockedValidatorOfTestSource));
  }

  @Test
  public void testMultipleValidators() {
    createManagerFromAndValidateMessagesOf(
        orderedListOfValidators, mockedProcessingDetailsFromTestSource);
    assertThat(
        validWarningsFromRunOfThisTest, is(validWarningsFromFirstMockedValidatorOfTestSource));
  }

  private void createManagerFromAndValidateMessagesOf(
      MessageValidator validator, ProcessingDetails details) {
    createManagerFromAndValidateMessagesOf(Collections.singletonList(validator), details);
  }

  private void createManagerFromAndValidateMessagesOf(
      List<MessageValidator> validators, ProcessingDetails details) {
    MessageValidatorManager messageValidatorManager = new MessageValidatorManager(validators);
    validWarningsFromRunOfThisTest = messageValidatorManager.getValidWarningsFrom(details);
  }
}
