package com.deliveroo.cron.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class StringUtils {

  public static boolean isStringEmpty(String str) {
    return str == null || str.length() == 0;
  }
}
