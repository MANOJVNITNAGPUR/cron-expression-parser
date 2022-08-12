package com.deliveroo.cron;

import static java.lang.System.exit;

import com.deliveroo.cron.enums.CronField;
import com.deliveroo.cron.exception.InvalidExpressionException;
import com.deliveroo.cron.models.CronSchedule;
import com.deliveroo.cron.service.CronExpressionParserService;
import com.deliveroo.cron.service.impl.CronExpressionParserServiceImpl;

import java.util.BitSet;
import java.util.Scanner;

public class CronExpressionParserDriver {

  private static CronExpressionParserService cronExpressionParserService = new CronExpressionParserServiceImpl();
  private static Scanner scanner = new Scanner(System.in);
  private static final String DISPLAY_WIDTH = "%-14s";

  private static void printMenu(String[] options) {
    for (String option : options) {
      System.out.println(option);
    }
    System.out.print("Choose your option : ");
  }

  private static String getDisplayString(String fieldName, BitSet bitSet) {
    StringBuilder builder = new StringBuilder(String.format(DISPLAY_WIDTH, fieldName));
    for (int index = 0; index < bitSet.length(); index++) {
      if (bitSet.get(index)) {
        builder.append(index + " ");
      }
    }
    return builder.toString();
  }

  private static void printExpression(CronSchedule schedule) {
    System.out.println("-----------------------(" + schedule.getCronExpression() + ")---------------------------");
    System.out.println(getDisplayString(CronField.MINUTE.getFieldName(), schedule.getMinutes()));
    System.out.println(getDisplayString(CronField.HOUR.getFieldName(), schedule.getHours()));
    System.out.println(getDisplayString(CronField.DAY_OF_MONTH.getFieldName(), schedule.getDayOfMonth()));
    System.out.println(getDisplayString(CronField.MONTH.getFieldName(), schedule.getMonth()));
    System.out.println(getDisplayString(CronField.DAY_OF_WEEK.getFieldName(), schedule.getDaysOfWeek()));
    System.out.println(String.format(DISPLAY_WIDTH, "command")+schedule.getCommand());
    System.out.println("--------------------------------------------------");
  }

  private static void parseExpression() {
    try {
      System.out.println("Enter cron expression:");
      String cronExpression = scanner.nextLine();
      CronSchedule cronSchedule = cronExpressionParserService.parseExpression(cronExpression);
      printExpression(cronSchedule);
    } catch (InvalidExpressionException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public static void main(String[] args) {
    String[] options = {
        "1- Enter the expression",
        "2- Exit",
    };
    int option = 0;
    while (option != 2) {
      printMenu(options);
      try {
        option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
          case 1:
            parseExpression();
            break;
          case 2:
            exit(0);
        }
      } catch (Exception ex) {
        System.out.println("Please enter an integer value between 1 and " + options.length);
        scanner.next();
      }
    }

  }
}
