package com.deliveroo.cron.utils.service;

import com.deliveroo.cron.exception.InvalidExpressionException;
import com.deliveroo.cron.models.CronSchedule;
import com.deliveroo.cron.service.CronExpressionParserService;
import com.deliveroo.cron.service.impl.CronExpressionParserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CronExpressionParserServiceTest {

  private CronExpressionParserService service = new CronExpressionParserServiceImpl();

  @Test
  public void emptyExpressionTest(){
    Assertions.assertThrows(InvalidExpressionException.class,()->service.parseExpression(null));
    Assertions.assertThrows(InvalidExpressionException.class,()->service.parseExpression(""));
  }

  @Test
  public void invalidExpressionTest(){
    Assertions.assertThrows(InvalidExpressionException.class,()->service.parseExpression("xyz"));
  }

  @Test
  public void invalidExpressionFieldTest(){
    Assertions.assertThrows(InvalidExpressionException.class,()->service.parseExpression("* * * * xyz"));
  }

  @Test
  public void validExpressionTest() throws InvalidExpressionException {
    CronSchedule schedule = service.parseExpression("*/15 0 1,15 * 1-5");
    Assertions.assertEquals(4,schedule.getMinutes().cardinality());
    Assertions.assertEquals(1,schedule.getHours().cardinality());
    Assertions.assertEquals(2,schedule.getDayOfMonth().cardinality());
    Assertions.assertEquals(12,schedule.getMonth().cardinality());
    Assertions.assertEquals(5,schedule.getDaysOfWeek().cardinality());
  }
}
