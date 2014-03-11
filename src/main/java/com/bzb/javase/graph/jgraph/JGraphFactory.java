package com.bzb.javase.graph.jgraph;

import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.Edge;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.Port;

public class JGraphFactory {

  private JGraphFactory() {
    // Factory class
  }

  private static DefaultGraphCell createVertex(String name, Rectangle bounds, Color backgroundColor, boolean opaque, Border border,
      Color borderColor) {
    AttributeMap attributes = new AttributeMap();
    attributes.put(GraphConstants.OPAQUE, opaque);
    if (bounds != null) {
      attributes.put(GraphConstants.BOUNDS, bounds);
    }
    if (backgroundColor != null) {
      attributes.put(GraphConstants.BACKGROUND, backgroundColor);
    }
    if (border != null) {
      attributes.put(GraphConstants.BORDER, border);
    }
    if (borderColor != null) {
      attributes.put(GraphConstants.BORDERCOLOR, borderColor);
    }

    DefaultGraphCell vertex = new DefaultGraphCell(name);
    vertex.setAttributes(attributes);
    return vertex;
  }

  private static DefaultEdge createEdge(String name, int lineEnd, boolean endFill) {
    AttributeMap attributes = new AttributeMap();
    attributes.put(GraphConstants.LINEEND, lineEnd);
    attributes.put(GraphConstants.ENDFILL, endFill);

    DefaultEdge edge = new DefaultEdge(name);
    edge.setAttributes(attributes);
    return edge;
  }

  private static void insert(JGraph graph, Edge edge, Port sourcePort, Port targetPort, Object srcVetex, Object targetVertex) {
    graph.getModel().insert(new Object[]{edge, srcVetex, targetVertex}, null,
        new ConnectionSet(edge, sourcePort, targetPort), null, null);
  }

  public static JGraph createGraph() {
    JGraph graph = new JGraph(new DefaultGraphModel());
    graph.setAntiAliased(true);
    graph.setScale(1.5);
    graph.setEditable(false);
    graph.setMoveable(false);

    // Creating three vertices
    DefaultPort port1 = new DefaultPort();
    DefaultGraphCell vertex1 = createVertex("Vertex1", new Rectangle(100, 100, 100, 80),
        Color.GREEN, false, null, Color.RED);
    vertex1.add(port1);

    DefaultPort port2 = new DefaultPort();
    DefaultGraphCell vertex2 = createVertex("Vertex2", new Rectangle(350, 100, 100, 80),
        Color.YELLOW, true, BorderFactory.createEtchedBorder(), Color.BLACK);
    vertex2.add(port2);

    DefaultPort port3 = new DefaultPort();
    DefaultGraphCell vertex3 = createVertex("Vertex3", new Rectangle(600, 100, 100, 80),
        Color.ORANGE, true, BorderFactory.createLoweredBevelBorder(), Color.BLUE);
    vertex3.add(port3);

    // Creating two edges
    DefaultEdge edge1 = createEdge("Edge1", GraphConstants.ARROW_CLASSIC, true);
    DefaultEdge edge2 = createEdge("Edge2", GraphConstants.ARROW_DOUBLELINE, true);

    insert(graph, edge1, port1, port2, vertex1, vertex2);
    insert(graph, edge2, port2, port3, vertex2, vertex3);

    return graph;
  }
}
