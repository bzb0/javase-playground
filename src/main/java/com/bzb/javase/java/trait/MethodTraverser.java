package com.bzb.javase.java.trait;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface MethodTraverser {

  default String traverseMethods() {
    Method[] methods = this.getClass().getDeclaredMethods();
    return Arrays.stream(methods).map(method -> {
      String parameters = Arrays.stream(method.getParameterTypes())
          .map(param -> param.getName())
          .collect(Collectors.joining(", "));
      return method.getReturnType() + " " + method.getName() + "(" + parameters + ")";
    }).collect(Collectors.joining(", ", "[", "]"));
  }
}
