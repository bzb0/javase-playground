package com.bzb.javase.java.nio.socket;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Callable;

public class EchoSocketHandler implements Callable<Void> {

  private final AsynchronousSocketChannel socketChannel;

  public EchoSocketHandler(AsynchronousSocketChannel socketChannel) {
    this.socketChannel = socketChannel;
  }

  @Override
  public Void call() throws Exception {
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    // Receiving data
    while (socketChannel.read(buffer).get() != -1) {
      // Switching to read mode
      buffer.flip();

      // Echoing data back
      socketChannel.write(buffer).get();

      // Compacting buffer
      if (buffer.hasRemaining()) {
        buffer.compact();
      } else {
        buffer.clear();
      }
    }
    socketChannel.close();
    return null;
  }
}
