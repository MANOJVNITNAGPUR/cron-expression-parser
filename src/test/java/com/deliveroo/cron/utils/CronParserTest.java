package com.deliveroo.cron.utils;

import com.deliveroo.cron.constants.DataConstants;
import com.deliveroo.cron.enums.CronField;
import com.deliveroo.cron.exception.InvalidExpressionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

public class CronParserTest {

  @Test
  public void asteriskTest() throws InvalidExpressionException {
    BitSet bitSet = CronParser.parseExpressionPart(CronField.HOUR, DataConstants.ASTERISK);
    Assertions.assertEquals(CronField.HOUR.getLength(), bitSet.cardinality());

    bitSet = CronParser.parseExpressionPart(CronField.MINUTE, DataConstants.ASTERISK);
    Assertions.assertEquals(CronField.MINUTE.getLength(), bitSet.cardinality());

    bitSet = CronParser.parseExpressionPart(CronField.DAY_OF_MONTH, DataConstants.ASTERISK);
    Assertions.assertEquals(CronField.DAY_OF_MONTH.getLength(), bitSet.cardinality());

    bitSet = CronParser.parseExpressionPart(CronField.MONTH, DataConstants.ASTERISK);
    Assertions.assertEquals(CronField.MONTH.getLength(), bitSet.cardinality());

    bitSet = CronParser.parseExpressionPart(CronField.DAY_OF_WEEK, DataConstants.ASTERISK);
    Assertions.assertEquals(CronField.DAY_OF_WEEK.getLength(), bitSet.cardinality());
  }

  @Test
  public void numberParseTest() throws InvalidExpressionException {
    BitSet bitSet = CronParser.parseExpressionPart(CronField.HOUR,"6");
    Assertions.assertEquals(1,bitSet.cardinality());
    Assertions.assertTrue(bitSet.get(6));
  }

  @Test
  public void rangeParseTest() throws InvalidExpressionException {
    BitSet bitSet = CronParser.parseExpressionPart(CronField.HOUR,"1-6");
    Assertions.assertEquals(6,bitSet.cardinality());
    Assertions.assertTrue(bitSet.get(1));
    Assertions.assertTrue(bitSet.get(2));
    Assertions.assertTrue(bitSet.get(3));
    Assertions.assertTrue(bitSet.get(4));
    Assertions.assertTrue(bitSet.get(5));
    Assertions.assertTrue(bitSet.get(6));
  }

  @Test
  public void rangeParseWithStepTest() throws InvalidExpressionException {
    BitSet bitSet = CronParser.parseExpressionPart(CronField.HOUR,"1-6/3");
    Assertions.assertEquals(2,bitSet.cardinality());
    Assertions.assertTrue(bitSet.get(1));
    Assertions.assertTrue(bitSet.get(4));
  }

  @Test
  public void multiValueTest() throws InvalidExpressionException {
    BitSet bitSet = CronParser.parseExpressionPart(CronField.HOUR,"1,3,5");
    Assertions.assertEquals(3,bitSet.cardinality());
    Assertions.assertTrue(bitSet.get(1));
    Assertions.assertTrue(bitSet.get(3));
    Assertions.assertTrue(bitSet.get(5));
  }

  @Test
  public void invalidExpressionTest() {
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"1tayslk"));
  }

  @Test
  public void minMaxValueValidation(){
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"-1"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"26"));

    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.MINUTE,"-1"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.MINUTE,"65"));

    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.DAY_OF_MONTH,"0"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.DAY_OF_MONTH,"35"));

    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.MONTH,"0"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.MONTH,"15"));

    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.DAY_OF_WEEK,"0"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.DAY_OF_WEEK,"9"));
  }

  @Test
  public void rangeMinMaxValidation(){
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"-1-6"));

    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.DAY_OF_WEEK,"0-6"));

    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.DAY_OF_MONTH,"0-35"));
  }

  @Test
  public void invalidStepExpressionTest(){
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"1/"));
  }

  @Test
  public void invalidStepSizeException(){
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"1/0"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"1/*"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"-1/5"));
  }

  @Test
  public void validStepExpressionTest() throws InvalidExpressionException {
    BitSet bitSet = CronParser.parseExpressionPart(CronField.DAY_OF_WEEK,"2/2");
    Assertions.assertEquals(3,bitSet.cardinality());
    Assertions.assertTrue(bitSet.get(2));
    Assertions.assertTrue(bitSet.get(4));
    Assertions.assertTrue(bitSet.get(6));
  }

  @Test
  public void invalidRangeTest(){
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"10-5"));
    Assertions.assertThrows(InvalidExpressionException.class,()->CronParser.parseExpressionPart(CronField.HOUR,"*-5"));
  }
}
