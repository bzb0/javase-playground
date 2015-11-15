package com.bzb.javase.java.trait;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class MethodTraverserTest {

  @Test
  public void traverseMethodsTraversesAllMethods() {
    Tester traverser = new Tester();

    String traversedMethods = traverser.traverseMethods();

    Assert.assertEquals("[void getName(), int getId(java.util.List), class java.lang.String compute(int, double)]", traversedMethods);
  }

  private static class Tester implements MethodTraverser {

    public void getName() {
    }

    public String compute(int a, double b) {
      return null;
    }

    public int getId(List<String> ids) {
      return 0;
    }
  }
}
