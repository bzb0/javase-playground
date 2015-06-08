package com.bzb.javase.java.nio.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class EchoCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

  // Server socket channel
  private final AsynchronousServerSocketChannel listener;

  public EchoCompletionHandler(AsynchronousServerSocketChannel listener) {
    this.listener = listener;
  }

  @Override
  public void completed(AsynchronousSocketChannel ch, Void att) {
    /* Accept the next connection. */
    listener.accept(null, this);

    try {
      read(ch);
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      // Closing connection after a timeout
      ch.write(ByteBuffer.wrap("Closing connection\n".getBytes()));
    } finally {
      if (ch.isOpen()) {
        try {
          ch.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void failed(Throwable exc, Void attachment) {
    //
  }

  private void read(AsynchronousSocketChannel ch) throws ExecutionException, InterruptedException, TimeoutException {
    ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
    int bytesRead = ch.read(byteBuffer).get(2000, TimeUnit.MILLISECONDS);
    while (bytesRead != -1) {
      // Checking the client sent data (empty line means end of conversation)
      if (byteBuffer.position() > 2) {
        // Switching to read mode
        byteBuffer.flip();

        // Converting the byte buffer into a string
        byte[] echoByteBuffer = new byte[bytesRead];
        byteBuffer.get(echoByteBuffer, 0, bytesRead);
        // Echoing back to the client
        ch.write(ByteBuffer.wrap(new String(echoByteBuffer).getBytes()));

        // Reading next line
        byteBuffer.clear();
        bytesRead = ch.read(byteBuffer).get(5000, TimeUnit.MILLISECONDS);
      } else {
        // Ending conversation after an empty line
        break;
      }
    }
  }
}
