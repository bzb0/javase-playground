package com.bzb.javase.designpattern.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class SerializedSingleton implements Serializable {

  private static final SerializedSingleton INSTANCE = new SerializedSingleton();

  private SerializedSingleton() {
  }

  public static SerializedSingleton getInstance() {
    return INSTANCE;
  }

  private Object readResolve() throws ObjectStreamException {
    // Instead of deserializing an byte array, we return the singleton instance
    return INSTANCE;
  }
}
