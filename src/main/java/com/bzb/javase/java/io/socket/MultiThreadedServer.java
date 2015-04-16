package com.bzb.javase.java.io.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MultiThreadedServer {

  private final ServerSocket socket;
  private volatile boolean stopped = false;

  public MultiThreadedServer(int port) throws IOException {
    socket = new ServerSocket(port);
  }

  public void startServer() throws IOException {
    while (!stopped) {
      new SocketHandler(socket.accept(), "Server-Thread-" + new Random().nextInt()).start();
    }
  }

  public void stopServer() throws IOException {
    stopped = true;
    socket.close();
  }

  private static class SocketHandler extends Thread {

    private final Socket socket;

    public SocketHandler(final Socket socket, String threadName) {
      super(threadName);
      this.socket = socket;
    }

    @Override
    public void run() {
      try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
          DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
        // Receiving data from client
        System.out.println("Received: " + inputStream.readUTF());
        // Sending data to client
        outputStream.writeUTF("Server:" + new Random().nextFloat());

        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
