package com.bzb.javase.java.dynamicproxy;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

public class ProxyMethodHandlerTest {

  @Test
  public void testSetterProxyMethodInvocation() {
    String projectName = "Big Project A";
    HashMap<String, Object> identity = new HashMap<>();

    Project project = (Project) ProxyMethodHandler.newProxyInstance(identity, new Class[] { Project.class });
    project.setName(projectName);

    Assert.assertEquals(projectName, project.getName());
  }

  @Test
  public void testBooleanProxyMethodInvocation() {
    boolean cancelled = false;
    HashMap<String, Object> identity = new HashMap<>();

    Project project = (Project) ProxyMethodHandler.newProxyInstance(identity, new Class[] { Project.class });
    project.setCancelled(cancelled);

    Assert.assertEquals(cancelled, project.isCancelled());
  }
}
