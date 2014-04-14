package com.bzb.javase.graph.jung;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class JungGraphFactory {

  private JungGraphFactory() {
    // Factory class
  }

  public static Graph<CustomVertex, WeightedEdge> createDirectedGraph() {
    Graph<CustomVertex, WeightedEdge> graph = new DirectedSparseMultigraph<>();

    // Creating five vertices
    CustomVertex vertex1 = new CustomVertex(1);
    CustomVertex vertex2 = new CustomVertex(2);
    CustomVertex vertex3 = new CustomVertex(3);
    CustomVertex vertex4 = new CustomVertex(4);
    CustomVertex vertex5 = new CustomVertex(5);

    // Connecting the vertices with edges
    graph.addEdge(new WeightedEdge(0.0, 0), vertex1, vertex2, EdgeType.DIRECTED);
    graph.addEdge(new WeightedEdge(0.0, 0), vertex2, vertex3);
    graph.addEdge(new WeightedEdge(0.0, 0), vertex3, vertex5, EdgeType.DIRECTED);
    graph.addEdge(new WeightedEdge(0.0, 0), vertex5, vertex4, EdgeType.DIRECTED);
    graph.addEdge(new WeightedEdge(0.0, 0), vertex4, vertex2);
    graph.addEdge(new WeightedEdge(0.0, 0), vertex3, vertex1);
    graph.addEdge(new WeightedEdge(0.0, 0), vertex2, vertex5, EdgeType.DIRECTED);

    return graph;
  }

  public static Graph<CustomVertex, WeightedEdge> createWeightedDirectedGraph() {
    Graph<CustomVertex, WeightedEdge> graph = new DirectedSparseMultigraph<>();

    // Creating five vertices
    CustomVertex vertex1 = new CustomVertex(1);
    CustomVertex vertex2 = new CustomVertex(2);
    CustomVertex vertex3 = new CustomVertex(3);
    CustomVertex vertex4 = new CustomVertex(4);
    CustomVertex vertex5 = new CustomVertex(5);

    // Connecting the vertices with edges
    graph.addEdge(new WeightedEdge(2.0, 50), vertex1, vertex2, EdgeType.DIRECTED);
    graph.addEdge(new WeightedEdge(4.0, 12), vertex2, vertex3, EdgeType.DIRECTED);
    graph.addEdge(new WeightedEdge(10.0, 20), vertex3, vertex5);
    graph.addEdge(new WeightedEdge(12.0, 44), vertex5, vertex4);
    graph.addEdge(new WeightedEdge(16.0, 100), vertex4, vertex2);
    graph.addEdge(new WeightedEdge(20.0, 9), vertex3, vertex1);
    graph.addEdge(new WeightedEdge(50.0, 20), vertex2, vertex5, EdgeType.DIRECTED);

    return graph;
  }

}
