package com.deliveroo.cron.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.BitSet;

@Getter
@Setter
@Builder
public class CronSchedule {
  private String cronExpression;
  private BitSet minutes;
  private BitSet hours;
  private BitSet dayOfMonth;
  private BitSet month;
  private BitSet daysOfWeek;
}
