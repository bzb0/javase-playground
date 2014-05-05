package com.bzb.javase.graph.jgraph;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class GraphTest {

  @Test
  public void testCreateUndirectedSparseGraph() {
    // Creating a Graph of three vertices (integers) and two edges (strings)
    Graph<Integer, String> graph = new UndirectedSparseGraph<>();
    graph.addVertex(1);
    graph.addVertex(2);
    graph.addVertex(3);
    graph.addEdge("Edge1", 1, 2);
    graph.addEdge("Edge2", 2, 3);

    Assert.assertArrayEquals(graph.getVertices().toArray(), Arrays.asList(1, 2, 3).toArray());
    Assert.assertArrayEquals(graph.getEdges().toArray(), Arrays.asList("Edge2", "Edge1").toArray());
  }

  @Test
  public void testCreateSparseMultigraph_withParallelAndDirectedEdges() {
    Graph<Integer, String> graph = new SparseMultigraph<>();
    graph.addVertex(1);
    graph.addVertex(2);
    graph.addVertex(3);
    graph.addEdge("Edge1", 1, 3);
    graph.addEdge("Edge2", 2, 3, EdgeType.DIRECTED);
    graph.addEdge("Edge3", 3, 2, EdgeType.DIRECTED);
    graph.addEdge("Edge4", 2, 3); // A parallel edge

    Assert.assertEquals(2, graph.getOutEdges(2).size());
    Assert.assertEquals(4, graph.getEdges().size());
    Assert.assertEquals("Vertices:1,2,3\nEdges:Edge4[2,3] Edge3[3,2] Edge2[2,3] Edge1[1,3] ", graph.toString());
  }
}
