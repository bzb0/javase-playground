package com.bzb.javase.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class SinglePortEchoServer {

  // Asynchronous server socket channel
  private final AsynchronousServerSocketChannel serverSocketChannel;

  public SinglePortEchoServer(int port) throws IOException {
    serverSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port));
  }

  public void startServer() {
    // Listening for new connections
    serverSocketChannel.accept(null, new EchoCompletionHandler(serverSocketChannel));
  }
}
