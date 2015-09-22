package com.bzb.javase.java.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFileCreator {

  private static final int NUM_VALUES = 10000;
  private final ThreadLocalRandom localRandom = ThreadLocalRandom.current();

  public void createRandomFile(String filePath) throws Exception {
    try (FileOutputStream outputStream = new FileOutputStream(filePath); FileChannel outputChannel = outputStream.getChannel()) {
      // Writing random data to the byte buffer
      ByteBuffer buffer = ByteBuffer.allocate(1024);
      for (int i = 0; i < NUM_VALUES; i++) {
        buffer.putInt(localRandom.nextInt());
      }
      // Switching to read mode
      buffer.flip();

      // Writing the random bytes to the channel
      outputChannel.write(buffer);
      outputChannel.force(true);
    }
  }
}
