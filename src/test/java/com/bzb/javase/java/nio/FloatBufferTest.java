package com.bzb.javase.java.nio;

import java.nio.FloatBuffer;
import org.junit.Assert;
import org.junit.Test;

public class FloatBufferTest {

  @Test
  public void createFloatBuffer() {
    FloatBuffer buffer = FloatBuffer.allocate(4);
    for (int i = 0; i < buffer.capacity(); ++i) {
      buffer.put((float) (Math.PI * i));
    }

    // Switching to read mode
    buffer.flip();

    Assert.assertEquals(0.0, buffer.get(), 0.0001);
    Assert.assertEquals(3.1415927, buffer.get(), 0.0001);
    Assert.assertEquals(6.2831855, buffer.get(), 0.0001);
    Assert.assertEquals(9.424778, buffer.get(), 0.0001);
  }
}
