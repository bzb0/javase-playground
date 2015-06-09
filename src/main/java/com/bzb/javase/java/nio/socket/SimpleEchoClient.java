package com.bzb.javase.java.nio.socket;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class SimpleEchoClient {

	private AsynchronousSocketChannel socketChannel;
	private final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
	private final CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

	public SimpleEchoClient(String host, int port) throws IOException, ExecutionException, InterruptedException {
		socketChannel = AsynchronousSocketChannel.open();
		socketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * 1024);
		socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * 1024);
		socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

		socketChannel.connect(new InetSocketAddress("127.0.0.1", 12345)).get();
	}

	public void sendData() throws ExecutionException, InterruptedException {
		// Writing data into the byte buffer
		String randomStr = "Hello" + new Random().nextInt();
		byteBuffer.put(randomStr.getBytes());

		// Switching to read mode
		byteBuffer.flip();

		// Sending data
		socketChannel.write(byteBuffer).get();

		// Clearing the buffer
		byteBuffer.clear();
	}

	public String receiveData() throws ExecutionException, InterruptedException, CharacterCodingException {
		String receivedData = null;
		while (socketChannel.read(byteBuffer).get() != -1) {
			// Switching to read mode
			byteBuffer.flip();

			// Receiving data
			receivedData = decoder.decode(byteBuffer).toString();

			// Compacting the buffer
			if (byteBuffer.hasRemaining()) {
				byteBuffer.compact();
			} else {
				byteBuffer.clear();
			}
		}
		return receivedData;
	}
}