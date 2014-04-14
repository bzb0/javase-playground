package com.bzb.javase.graph.jung;

import java.util.Objects;

public class CustomVertex {

  private final int id;

  public CustomVertex(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CustomVertex)) {
      return false;
    }
    CustomVertex that = (CustomVertex) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "CustomVertex[id=" + id + "]";
  }
}
