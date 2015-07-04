package com.bzb.javase.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class MultiPortEchoServer {

  // The list of ports the server will listen on
  private final int ports[];
  // The echo byte buffer
  private final ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

  public MultiPortEchoServer(int ports[]) {
    this.ports = ports;
  }

  public void startServer() throws IOException {
    // Creating a new selector
    Selector selector = Selector.open();
    // Creating a listener on each port
    createServerSockets(selector);
    // Listening for incoming connections
    listen(selector);
  }

  public void listen(Selector selector) throws IOException {
    while (true) {
      selector.select();
      Iterator<SelectionKey> it = selector.selectedKeys().iterator();
      while (it.hasNext()) {
        SelectionKey key = it.next();
        it.remove();
        if (key.isAcceptable()) {
          createSocketChannel(selector, key);
        } else if (key.isReadable()) {
          echoReceivedData(key);
        }
      }
    }
  }

  public void createServerSockets(Selector selector) throws IOException {
    // Opening a listener on each port and registering it with the selector
    for (int i = 0; i < ports.length; i++) {
      // Creating a server socket channel and putting it in non-blocking mode
      ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.configureBlocking(false);

      // Creating a server socket and binding it with the local address and the given port
      ServerSocket serverSocket = serverSocketChannel.socket();
      serverSocket.bind(new InetSocketAddress(ports[i]));

      // Registering the server socket channel with the selector
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
  }

  private void createSocketChannel(Selector selector, SelectionKey key) throws IOException {
    // Accepting the connection
    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
    // Creating a separate socket channel and putting it non-blocking mode
    SocketChannel socketChannel = serverSocketChannel.accept();
    socketChannel.configureBlocking(false);

    // Registering the socket channel with the selector
    socketChannel.register(selector, SelectionKey.OP_READ);
  }

  private void echoReceivedData(SelectionKey key) throws IOException {
    // Reading data from the socket channel
    SocketChannel socketChannel = (SocketChannel) key.channel();
    // Echoing received data
    while (true) {
      echoBuffer.clear();
      // Reading from the socket channel and writing to the byte buffer
      int r = socketChannel.read(echoBuffer);
      if (r <= 0) {
        break;
      }
      // Switching to read mode
      echoBuffer.flip();
      // Writing to the socket channel
      socketChannel.write(echoBuffer);
    }
  }
}
