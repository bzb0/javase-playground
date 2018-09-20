package com.bzb.javase.javafun.vavr;

import io.vavr.Function2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Assert;
import org.junit.Test;

public class TryTest {

  @Test
  public void safeDivide_successfulDivision() {
    Try<Integer> result = safeDivide(5, 1);

    Assert.assertEquals(true, result.isSuccess());
  }

  @Test
  public void safeDivide_DivisionByZero() {
    Try<Integer> result = safeDivide(5, 0);

    Assert.assertEquals(true, result.isFailure());
    Assert.assertEquals(new ArithmeticException("/ by zero").getMessage(), result.getCause().getMessage());
  }

  @Test
  public void divide() {
    Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
    Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

    Option<Integer> result = safeDivide.apply(5, 0);

    Assert.assertEquals(true, result.isEmpty());
    Assert.assertEquals(new Integer("-1"), result.getOrElse(new Integer("-1")));
  }

  private Try<Integer> safeDivide(Integer dividend, Integer divisor) {
    return Try.of(() -> dividend / divisor);
  }
}
