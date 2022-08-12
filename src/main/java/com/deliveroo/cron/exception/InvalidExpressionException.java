package com.deliveroo.cron.exception;

public class InvalidExpressionException extends Exception {

  InvalidExpressionException() {
    super();
  }

  public InvalidExpressionException(String message) {
    super(message);
  }

  public InvalidExpressionException(String message, Throwable cause) {
    super(message, cause);
  }

}
