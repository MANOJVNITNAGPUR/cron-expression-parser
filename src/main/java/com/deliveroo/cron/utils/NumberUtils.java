package com.deliveroo.cron.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtils {

  public static int convertToInt(String str) throws NumberFormatException {
    try {
      return Integer.parseInt(str);
    } catch (NumberFormatException ex) {
      throw ex;
    }
  }

  public static boolean isInteger(String str) {
    try {
      convertToInt(str);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }
}
