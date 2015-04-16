package com.bzb.javase.java.io.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class DatagramClient {

  private final DatagramSocket socket;

  public DatagramClient() throws SocketException {
    socket = new DatagramSocket();
  }

  public void sendData(String data, String destinationHost, int port) throws IOException {
    socket.send(new DatagramPacket(data.getBytes(), data.getBytes().length, InetAddress.getByName(destinationHost), port));
  }

  public String receiveData() throws IOException {
    byte[] buffer = new byte[256];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
    socket.receive(packet);
    return new String(packet.getData(), 0, packet.getLength());
  }

  public void closeSocket() {
    socket.close();
  }
}
