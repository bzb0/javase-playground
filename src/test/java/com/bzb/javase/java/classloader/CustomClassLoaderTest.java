package com.bzb.javase.java.classloader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.Assert;
import org.junit.Test;

public class CustomClassLoaderTest {

  @Test
  public void loadingClassWithCustomClassLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
      NoSuchMethodException, InvocationTargetException {
    ClassLoader parentClassLoader = CustomClassLoaderTest.class.getClassLoader();
    CustomClassLoader customClassLoader = new CustomClassLoader(parentClassLoader);

    Class<TestClass> clazz = customClassLoader.loadClass("com.bzb.javase.java.classloader.TestClass");
    Object instance = clazz.newInstance();
    Method method = clazz.getMethod("getId");

    Assert.assertEquals("1", method.invoke(instance));
    Assert.assertEquals(clazz.getClassLoader(), customClassLoader);
    Assert.assertNotEquals(TestClass.class.getClassLoader(), customClassLoader);
  }
}
