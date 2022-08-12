package com.deliveroo.cron.utils;

import static com.deliveroo.cron.utils.NumberUtils.isInteger;

import com.deliveroo.cron.constants.DataConstants;
import com.deliveroo.cron.enums.CronField;
import com.deliveroo.cron.exception.InvalidExpressionException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.BitSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CronParser {
  public static BitSet parseExpressionPart(CronField cronField, String expressionPart)
      throws InvalidExpressionException {

    if (expressionPart.indexOf(",") > -1) {
      BitSet bitSet = new BitSet(cronField.getLength());
      String[] parts = expressionPart.split(",");
      for (String part : parts) {
        bitSet.or(parseExpressionPart(cronField, part));
      }
      return bitSet;
    }

    if (expressionPart.indexOf("/") > -1) {
      return parseStep(cronField, expressionPart);
    }

    if (expressionPart.indexOf("-") > -1) {
      return parseRange(cronField, expressionPart);
    }

    if (expressionPart.equalsIgnoreCase("*")) {
      return parseAsterisk(cronField);
    }

    return parseLiteral(cronField, expressionPart);
  }

  private static BitSet parseStep(CronField cronField, String expressionPart) throws InvalidExpressionException {
    String[] fieldParts = expressionPart.split("/");
    if (fieldParts.length != 2) {
      throw new InvalidExpressionException(
          String.format(DataConstants.INVALID_FIELD_ERROR, cronField.getFieldName(), expressionPart));
    }
    int stepSize = parseNumber(cronField.getFieldName(), expressionPart, fieldParts[1]);
    if (stepSize < 1) {
      throw new InvalidExpressionException(
          String.format("invalid %s field: \"%s\". minimum allowed step (every) value is \"1\"",
              cronField.getFieldName(), expressionPart));
    }
    String rangePart = fieldParts[0];
    if (NumberUtils.isInteger(fieldParts[0])) {
      rangePart = String.format("%s-%d", fieldParts[0], cronField.getMaximumValue());
    }

    BitSet numSet = parseExpressionPart(cronField, rangePart);
    BitSet stepsSet = new BitSet(cronField.getMaximumValue());
    for (int i = numSet.nextSetBit(0); i < cronField.getMaximumValue(); i += stepSize) {
      stepsSet.set(i);
    }
    stepsSet.and(numSet);
    return stepsSet;
  }


  private static BitSet parseRange(CronField cronField, String expressionPart) throws InvalidExpressionException {
    String[] rangeParts = expressionPart.split(DataConstants.DASH);
    if (rangeParts.length != 2) {
      throw new InvalidExpressionException(
          getErrorMessage(DataConstants.INVALID_FIELD_ERROR, cronField.getFieldName(), expressionPart));
    }

    int from = parseNumber(cronField.getFieldName(), expressionPart, rangeParts[0]);
    int to = parseNumber(cronField.getFieldName(), expressionPart, rangeParts[1]);
    validateNumberField(from, cronField.getMinimumValue(), cronField.getMaximumValue(), cronField.getFieldName(),
        expressionPart);
    validateNumberField(to, cronField.getMinimumValue(), cronField.getMaximumValue(), cronField.getFieldName(),
        expressionPart);
    if (to < from) {
      throw new InvalidExpressionException(getErrorMessage(
          "invalid %s field: \"%s\". the start of range value must be less than or equal the end value",
          cronField.getFieldName(), expressionPart));
    }

    BitSet bitSet = new BitSet(cronField.getLength());
    bitSet.set(from, to + 1);
    return bitSet;

  }

  private static BitSet parseAsterisk(CronField cronField) {
    BitSet bitSet = new BitSet(cronField.getLength() + cronField.getMinimumValue());
    bitSet.set(cronField.getMinimumValue(), cronField.getMaximumValue() + 1);
    return bitSet;
  }

  private static BitSet parseLiteral(CronField cronField, String expressionPart) throws InvalidExpressionException {
    BitSet bitSet = new BitSet(cronField.getLength());
    int number = parseNumber(cronField.getFieldName(), expressionPart, expressionPart);
    validateNumberField(number, cronField.getMinimumValue(), cronField.getMaximumValue(), cronField.getFieldName(),
        expressionPart);
    bitSet.set(number);
    return bitSet;
  }

  private static String getErrorMessage(String errorString, Object... arguments) {
    return String.format(errorString, arguments);
  }

  private static void validateNumberField(int number, int minValue, int maxValue, String fieldName,
      String expressionPart)
      throws InvalidExpressionException {
    if (number < 0) {
      throw new InvalidExpressionException(
          getErrorMessage(DataConstants.INVALID_FIELD_ERROR, fieldName, expressionPart));
    }
    if (number < minValue) {
      throw new InvalidExpressionException(
          getErrorMessage("invalid %s field: \"%s\". minimum allowed value for %s field is \"%d\"",
              fieldName, expressionPart, fieldName, minValue));
    }
    if (number > maxValue) {
      throw new InvalidExpressionException(
          getErrorMessage("invalid %s field: \"%s\". maximum allowed value for %s field is \"%d\"",
              fieldName, expressionPart, fieldName, maxValue));
    }
  }

  private static int parseNumber(String fieldName, String expressionPart, String str)
      throws InvalidExpressionException {
    try {
      return NumberUtils.convertToInt(str);
    } catch (NumberFormatException ex) {
      throw new InvalidExpressionException(
          getErrorMessage(DataConstants.INVALID_FIELD_ERROR, fieldName, expressionPart), ex);
    }
  }


}
