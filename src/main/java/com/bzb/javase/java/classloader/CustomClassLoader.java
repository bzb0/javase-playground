package com.bzb.javase.java.classloader;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Custom class loader that will load only classes from the package 'com.bzb.javase.java.classloader'.
 */
public class CustomClassLoader extends ClassLoader {

  private static final String PACKAGE_NAME = "com.bzb.javase.java.classloader";

  public CustomClassLoader(ClassLoader parentClassLoader) {
    super(parentClassLoader);
  }

  @Override
  public Class loadClass(String name) throws ClassNotFoundException {
    if (name.startsWith(PACKAGE_NAME)) {
      return loadClassFromFile(name);
    }
    return super.loadClass(name);
  }

  /**
   * Loads a class from a class file.
   *
   * @param name Fully classified name of the class.
   * @return The class object.
   */
  private Class loadClassFromFile(String name) {
    try {
      // Reading the file data into a byte array
      byte[] fileByteArray = readClassFile(name.replace('.', File.separatorChar) + ".class");
      // Convert byte array in a class object
      Class clazz = defineClass(name, fileByteArray, 0, fileByteArray.length);
      resolveClass(clazz);
      return clazz;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Reads a class file into a byte array.
   *
   * @param name The file name.
   * @return Class file contents as byte array.
   * @throws IOException thrown if an error occurs while reading class file
   */
  private byte[] readClassFile(String name) throws IOException {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name);
    byte buffer[] = new byte[inputStream.available()];
    try (DataInputStream in = new DataInputStream(inputStream)) {
      in.readFully(buffer);
      return buffer;
    }
  }
}
