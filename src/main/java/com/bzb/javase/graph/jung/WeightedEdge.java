package com.bzb.javase.graph.jung;

import java.util.Objects;
import java.util.Random;

public class WeightedEdge {

  private final int id;
  private final double capacity;
  private final double weight;

  public WeightedEdge(double weight, double capacity) {
    this.id = new Random().nextInt(100);
    this.weight = weight;
    this.capacity = capacity;
  }

  public int getId() {
    return id;
  }

  public double getCapacity() {
    return capacity;
  }

  public double getWeight() {
    return weight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WeightedEdge)) {
      return false;
    }
    WeightedEdge that = (WeightedEdge) o;
    return id == that.id && Double.compare(that.capacity, capacity) == 0 && Double.compare(that.weight, weight) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, capacity, weight);
  }

  @Override
  public String toString() {
    return "WeightedEdge[id=" + id + ", capacity=" + capacity + ", weight=" + weight + "]";
  }
}
