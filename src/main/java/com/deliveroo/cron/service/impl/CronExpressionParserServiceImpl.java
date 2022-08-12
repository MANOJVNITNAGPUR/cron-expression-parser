package com.deliveroo.cron.service.impl;

import com.deliveroo.cron.constants.DataConstants;
import com.deliveroo.cron.enums.CronField;
import com.deliveroo.cron.exception.InvalidExpressionException;
import com.deliveroo.cron.models.CronSchedule;
import com.deliveroo.cron.service.CronExpressionParserService;
import com.deliveroo.cron.utils.CronParser;
import com.deliveroo.cron.utils.StringUtils;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class CronExpressionParserServiceImpl implements CronExpressionParserService {

  private static final String EXPRESSION_SPLITTER = "\\s+";

  @Override
  public CronSchedule parseExpression(String expression) throws InvalidExpressionException {
    if (StringUtils.isStringEmpty(expression)) {
      throw new InvalidExpressionException(DataConstants.CRON_EXPRESSION_IS_EMPTY);
    }

    String[] expressionFields = expression.trim().toLowerCase().split(EXPRESSION_SPLITTER);
    if (DataConstants.EXPRESSION_FIELD_SIZE != expressionFields.length) {
      throw new InvalidExpressionException(DataConstants.CRON_EXPRESSION_FIELD_COUNT_ERROR);
    }
    String[] cronExpressionFields = Arrays.copyOfRange(expressionFields, 0, expressionFields.length - 1);
    String command = expressionFields[5];

    CronSchedule cronSchedule = CronSchedule.builder()
        .cronExpression(String.join(" ", cronExpressionFields))
        .command(command).build();

    BitSet bitSet;

    for (CronField cronField : CronField.values()) {
      bitSet = parseExpressionPart(cronExpressionFields, cronField);
      setBitset(cronField, cronSchedule, bitSet);
    }
    return cronSchedule;
  }

  private BitSet parseExpressionPart(String[] cronExpressionFields, CronField cronField)
      throws InvalidExpressionException {
    int index = cronField.getExpressionIndex();
    String expressionPart = cronExpressionFields[index];
    return CronParser.parseExpressionPart(cronField, expressionPart);
  }

  private void setBitset(CronField cronField, CronSchedule cronSchedule, BitSet bitSet) {
    switch (cronField) {
      case MINUTE:
        cronSchedule.setMinutes(bitSet);
        break;
      case HOUR:
        cronSchedule.setHours(bitSet);
        break;
      case DAY_OF_MONTH:
        cronSchedule.setDayOfMonth(bitSet);
        break;
      case MONTH:
        cronSchedule.setMonth(bitSet);
        break;
      case DAY_OF_WEEK:
        cronSchedule.setDaysOfWeek(bitSet);
        break;
      default:
        break;
    }
  }
}
