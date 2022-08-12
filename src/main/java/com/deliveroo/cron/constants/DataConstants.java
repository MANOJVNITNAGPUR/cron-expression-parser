package com.deliveroo.cron.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataConstants {
  public static final int EXPRESSION_FIELD_SIZE = 5;

  public static final String INVALID_FIELD_ERROR = "invalid %s field: \"%s\"";

  public static final String CRON_EXPRESSION_IS_EMPTY = "Cron expression is empty.";

  public static final String CRON_EXPRESSION_FIELD_COUNT_ERROR = "Cron expression should have 5 fields";

  public static final String ASTERISK = "*";
  public static final String DASH = "-";
}
