package com.bzb.javase.java.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopier {

  public void copyFile(String sourceFile, String targetFile) throws IOException {
    try (FileInputStream inputStream = new FileInputStream(sourceFile); FileOutputStream outputStream = new FileOutputStream(targetFile);
        FileChannel inputChannel = inputStream.getChannel(); FileChannel outputChannel = outputStream.getChannel()) {

      // Allocating a direct system buffer
      ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
      while (true) {
        buffer.clear();
        // Reading from the input channel and writing to the buffer
        int r = inputChannel.read(buffer);
        if (r == -1) {
          break;
        }
        // Switching to read mode
        buffer.flip();
        // Writing to the output channel
        outputChannel.write(buffer);
      }
    }
  }
}
