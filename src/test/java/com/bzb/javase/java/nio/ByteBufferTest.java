package com.bzb.javase.java.nio;

import java.nio.ByteBuffer;
import org.junit.Assert;
import org.junit.Test;

public class ByteBufferTest {

  @Test
  public void createByteBuffer_withByteArrayWrap() {
    // Wrapping a byte array in a byte buffer
    ByteBuffer buffer = ByteBuffer.wrap(new byte[1024]);

    // Writing in the buffer
    buffer.put((byte) 'a');
    buffer.put((byte) 'b');
    buffer.put((byte) 'c');

    // Switching to read mode
    buffer.flip();

    Assert.assertEquals('a', (char) buffer.get());
    Assert.assertEquals('b', (char) buffer.get());
    Assert.assertEquals('c', (char) buffer.get());
  }

  @Test
  public void createByteBuffer_withAllocation() {
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    // Writing in the byte buffer
    buffer.put((byte) 'a');
    buffer.put((byte) 'b');
    buffer.put((byte) 'c');

    // Switching to read mode
    buffer.flip();

    Assert.assertEquals('a', (char) buffer.get());
    Assert.assertEquals('b', (char) buffer.get());
    Assert.assertEquals('c', (char) buffer.get());
  }

  @Test
  public void byteBufferWithDifferentDataTypes() {
    ByteBuffer buffer = ByteBuffer.allocate(64);

    buffer.putInt(25);
    buffer.putLong(6000000000000L);
    buffer.putDouble(Math.E);

    // Switching to read mode
    buffer.flip();

    Assert.assertEquals(25, buffer.getInt());
    Assert.assertEquals(6000000000000L, buffer.getLong());
    Assert.assertEquals(Math.E, buffer.getDouble(), 0.001);
  }

  @Test
  public void byteBufferSlice() {
    // Creating and filling the buffer with elements
    ByteBuffer buffer = ByteBuffer.allocate(5);
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.put((byte) i);
    }

    // Setting the position pointer to 2
    buffer.position(2);
    // Setting the limit pointer to 4
    buffer.limit(4);

    // Slicing the buffer in the range [2,4] - 2 bytes
    ByteBuffer slicedBuffer = buffer.slice();
    // Modifying the sliced buffer
    for (int i = 0; i < slicedBuffer.capacity(); ++i) {
      byte b = slicedBuffer.get(i);
      b += 10;
      slicedBuffer.put(i, b);
    }

    // Resetting buffer pointers
    buffer.position(0);
    buffer.limit(buffer.capacity());

    Assert.assertEquals(0, buffer.get());
    Assert.assertEquals(1, buffer.get());
    Assert.assertEquals(12, buffer.get());
    Assert.assertEquals(13, buffer.get());
    Assert.assertEquals(4, buffer.get());
  }
}
