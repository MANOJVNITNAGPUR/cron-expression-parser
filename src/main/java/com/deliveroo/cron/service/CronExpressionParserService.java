package com.deliveroo.cron.service;

import com.deliveroo.cron.exception.InvalidExpressionException;
import com.deliveroo.cron.models.CronSchedule;

public interface CronExpressionParserService {

  CronSchedule parseExpression(String expression) throws InvalidExpressionException;
}
