package com.bzb.javase.graph.jung;

import edu.uci.ics.jung.algorithms.flows.EdmondsKarpMaxFlow;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.junit.Assert;
import org.junit.Test;

public class GraphAlgorithmsTest {

  @Test
  public void testDijkstraShortestUnweightedPath() {
    Graph<String, String> graph = new DirectedSparseMultigraph<String, String>();
    String vertex1 = "v1", vertex2 = "v2", vertex3 = "v3", vertex4 = "v4", vertex5 = "v5";
    String edge1 = "e1", edge2 = "e2", edge3 = "e3", edge4 = "e4", edge5 = "e5", edge6 = "e6", edge7 = "e7";
    graph.addEdge(edge1, vertex1, vertex2);
    graph.addEdge(edge2, vertex2, vertex3);
    graph.addEdge(edge3, vertex3, vertex5);
    graph.addEdge(edge4, vertex5, vertex4);
    graph.addEdge(edge5, vertex4, vertex2);
    graph.addEdge(edge6, vertex3, vertex1);
    graph.addEdge(edge7, vertex2, vertex5);

    DijkstraShortestPath<String, String> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    List<String> shortestPath = dijkstraShortestPath.getPath(vertex1, vertex4);

    Assert.assertArrayEquals(shortestPath.toArray(), Arrays.asList(edge1, edge7, edge4).toArray());
  }

  @Test
  public void testDijkstraShortestWeightedPath() {
    Graph<CustomVertex, WeightedEdge> graph = JungGraphFactory.createWeightedDirectedGraph();

    DijkstraShortestPath<CustomVertex, WeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph, createEdgeWeightTransformer());

    Number distance = dijkstraShortestPath.getDistance(new CustomVertex(1), new CustomVertex(4));
    Assert.assertEquals(28.0, distance);
  }

  @Test
  public void test() {
    Graph<CustomVertex, WeightedEdge> graph = JungGraphFactory.createWeightedDirectedGraph();

    EdmondsKarpMaxFlow<CustomVertex, WeightedEdge> maxFlowAlgo = new EdmondsKarpMaxFlow((DirectedGraph) graph, new CustomVertex(2),
        new CustomVertex(5), createEdgeCapacityTransformer(), new HashMap<CustomVertex, Double>(), createEdgeFactory());
    maxFlowAlgo.evaluate();

    Assert.assertEquals(32, maxFlowAlgo.getMaxFlow());
    Assert.assertEquals(2,  maxFlowAlgo.getMinCutEdges().size() );
  }

  private Transformer<WeightedEdge, Double> createEdgeWeightTransformer() {
    return new Transformer<WeightedEdge, Double>() {
      public Double transform(WeightedEdge edge) {
        return edge.getWeight();
      }
    };
  }

  private Transformer<WeightedEdge, Double> createEdgeCapacityTransformer() {
    return new Transformer<WeightedEdge, Double>() {
      public Double transform(WeightedEdge edge) {
        return edge.getCapacity();
      }
    };
  }

  private Factory<WeightedEdge> createEdgeFactory() {
    return new Factory<WeightedEdge>() {
      public WeightedEdge create() {
        return new WeightedEdge(1.0, 1.0);
      }
    };
  }
}
