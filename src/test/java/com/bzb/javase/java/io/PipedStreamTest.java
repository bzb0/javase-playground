package com.bzb.javase.java.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PipedStreamTest {

  @Test
  public void pipedInputOutputStreamTest() throws IOException, InterruptedException {
    final PipedOutputStream outputStream = new PipedOutputStream();
    final PipedInputStream inputStream = new PipedInputStream(outputStream);

    StreamReader streamReader = new StreamReader(inputStream);
    Thread readerThread = new Thread(streamReader);
    Thread writerThread = new Thread(new StreamWriter(outputStream));

    readerThread.start();
    writerThread.start();

    readerThread.join();
    writerThread.join();

    Character[] expectedData = new Character[]{'P', 'i', 'p', 'e', 'd', ' ', 's', 't', 'r', 'e', 'a', 'm', ',', ' ', 't', 'e', 's', 't', '!'};
    Assert.assertArrayEquals(expectedData, streamReader.getReceivedData().toArray());
  }

  private static class StreamWriter implements Runnable {

    private final PipedOutputStream outputStream;

    public StreamWriter(PipedOutputStream outputStream) {
      this.outputStream = outputStream;
    }

    @Override
    public void run() {
      try {
        outputStream.write("Piped stream,".getBytes());
        outputStream.write(" test!".getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private static class StreamReader implements Runnable {

    private final PipedInputStream inputStream;
    private final List<Character> receivedData = new ArrayList<>();

    public StreamReader(PipedInputStream inputStream) {
      this.inputStream = inputStream;
    }

    @Override
    public void run() {
      try {
        int data = -1;
        while ((data = inputStream.read()) != -1) {
          receivedData.add((char) data);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    public List<Character> getReceivedData() {
      return receivedData;
    }
  }
}
