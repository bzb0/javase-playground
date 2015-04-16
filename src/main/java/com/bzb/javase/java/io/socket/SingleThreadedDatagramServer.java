package com.bzb.javase.java.io.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class SingleThreadedDatagramServer {

  protected final DatagramSocket socket;

  public SingleThreadedDatagramServer(int port) throws IOException {
    socket = new DatagramSocket(port);
  }

  public void startServer() {
    while (true) {
      try {
        byte[] buf = new byte[256];
        // Receiving a UDP datagram packet
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        String dataStr = new String(packet.getData());
        System.out.println("Received data: " + new String(packet.getData()));
        if ("\n".equals(dataStr)) {
          break;
        }

        // Sending the response
        String response = "Server" + new Random().nextDouble();
        byte[] responseBytes = response.getBytes();
        socket.send(new DatagramPacket(responseBytes, responseBytes.length, packet.getAddress(), packet.getPort()));
      } catch (IOException e) {
        e.printStackTrace();
        break;
      }
    }
    socket.close();
  }
}
