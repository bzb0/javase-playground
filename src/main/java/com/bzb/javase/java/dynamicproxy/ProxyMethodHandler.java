package com.bzb.javase.java.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class ProxyMethodHandler implements InvocationHandler {

  private static final String GET_PREFIX = "get";
  private static final String SET_PREFIX = "set";
  private static final String IS_PREFIX = "is";

  private final Map<String, Object> propertiesMap;

  public ProxyMethodHandler(Map<String, Object> propertiesMap) {
    this.propertiesMap = propertiesMap;
  }

  public static Object newProxyInstance(Map<String, Object> map, Class<?>[] interfaces) {
    return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, new ProxyMethodHandler(map));
  }

  @Override
  public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
    String methodName = m.getName();
    if (methodName.startsWith(GET_PREFIX)) {
      String propertyName = methodName.substring(methodName.indexOf(GET_PREFIX) + GET_PREFIX.length());
      return propertiesMap.get(propertyName);
    } else if (methodName.startsWith(SET_PREFIX)) {
      String propertyName = methodName.substring(methodName.indexOf(SET_PREFIX) + SET_PREFIX.length());
      propertiesMap.put(propertyName, args[0]);
      return null;
    } else if (methodName.startsWith(IS_PREFIX)) {
      String propertyName = methodName.substring(methodName.indexOf(IS_PREFIX) + IS_PREFIX.length());
      return propertiesMap.get(propertyName);
    }
    return m.invoke(proxy, args);
  }
}
