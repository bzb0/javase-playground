package com.bzb.javase.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadedEchoServer {

  private final AsynchronousServerSocketChannel serverSocket;
  // The thread pool
  private final ExecutorService threadPool = Executors.newCachedThreadPool(Executors.defaultThreadFactory());

  public MultiThreadedEchoServer(int port) throws IOException {
    serverSocket = AsynchronousServerSocketChannel.open();
    serverSocket.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
    serverSocket.setOption(StandardSocketOptions.SO_REUSEADDR, true);

    // Binding the server to the local address
    serverSocket.bind(new InetSocketAddress("127.0.0.1", port));
  }

  public void startServer() {
    while (true) {
      // Accepting a new connection
      Future<AsynchronousSocketChannel> socketFuture = serverSocket.accept();
      try {
        // Handing data reception in a separate thread
        threadPool.submit(new EchoSocketHandler(socketFuture.get()));
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
        // Closing the thread pool
        threadPool.shutdown();
      }
    }
  }
}
