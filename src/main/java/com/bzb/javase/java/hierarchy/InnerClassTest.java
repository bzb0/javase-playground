package com.bzb.javase.java.hierarchy;

public class InnerClassTest {

  private class InnerClass {

    public String getName() {
      return "Inner class";
    }
  }

  private static class StaticNestedClass {

    public String getName() {
      return "Static nested class";
    }
  }

  public InnerClass innerClass() {
    // Creating an inner class
    InnerClassTest test = new InnerClassTest();
    InnerClass inner = test.new InnerClass();
    return inner;
  }

  public StaticNestedClass staticNestedClass() {
    // Creating a static nested class
    StaticNestedClass test = new StaticNestedClass();
    return test;
  }

  public void localClass() {
    // Local (inner) class defined inside a method
    class Local {

      public String getName() {
        return "Local class";
      }
    }

    // Creating an instance of local inner class
    Local local = new Local();
    local.getName();
  }

  public Thread anonymousClass() {
    return new Thread(new Runnable() {
      {
        System.out.println("Anonymous class constructor.");
      }

      @Override
      public void run() {
        System.out.println("Anonymous class");
      }
    });
  }
}
