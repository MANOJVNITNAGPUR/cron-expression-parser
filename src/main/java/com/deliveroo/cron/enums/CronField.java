package com.deliveroo.cron.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public enum CronField {
  MINUTE(0, 0, 59, 60, "Minute"),
  HOUR(1, 0, 23, 24, "Hour"),
  DAY_OF_MONTH(2, 1, 31, 31, "Day of Month"),
  MONTH(3, 1, 12, 12, "Month"),
  DAY_OF_WEEK(4, 1, 7, 7, "Day of Week");

  private int expressionIndex;
  private int minimumValue;
  private int maximumValue;
  private int length;
  private String fieldName;

  private CronField(int expressionIndex, int minValue, int maxValue, int length, String fieldName) {
    this.expressionIndex = expressionIndex;
    this.minimumValue = minValue;
    this.maximumValue = maxValue;
    this.length = length;
    this.fieldName = fieldName;
  }
}
