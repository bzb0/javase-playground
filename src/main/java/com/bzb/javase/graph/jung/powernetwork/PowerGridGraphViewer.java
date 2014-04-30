package com.bzb.javase.graph.jung.powernetwork;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.LayeredIcon;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.PickableEdgePaintTransformer;
import edu.uci.ics.jung.visualization.decorators.PickableVertexPaintTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.DefaultVertexLabelRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class PowerGridGraphViewer {

  VisualizationViewer<String, String> createVisualizationViewer() {
    // Creating a visualization viewer with a self-organizing map layout
    VisualizationViewer<String, String> vizViewer = new VisualizationViewer<>(new ISOMLayout<String, String>(createPowerGridGraph()),
        new Dimension(800, 800));

    // Setting background color
    vizViewer.setBackground(Color.white);

    // Setting paint transformers for the vertices and edges
    vizViewer.getRenderContext().setVertexFillPaintTransformer(new PickableVertexPaintTransformer<String>(
        vizViewer.getPickedVertexState(), Color.BLACK, Color.YELLOW));
    vizViewer.getRenderContext().setEdgeDrawPaintTransformer(new PickableEdgePaintTransformer<String>(
        vizViewer.getPickedEdgeState(), Color.BLACK, Color.YELLOW));

    // Setting vertex and edges label transformers/renderers
    vizViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
    vizViewer.getRenderContext().setVertexLabelRenderer(new DefaultVertexLabelRenderer(Color.BLUE));
    vizViewer.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.BLUE));

    // Setting vertex icon transformer
    vizViewer.getRenderContext().setVertexIconTransformer(new CustomVertexIconTransformer<String>(getVertexIcons()));

    // Setting vertex tool tip transformer
    vizViewer.setVertexToolTipTransformer(new ToStringLabeller<String>());

    // Setting a custom picked vertex listener
    vizViewer.getPickedVertexState().addItemListener(new CheckMarkItemListener<String>(
        vizViewer.getRenderContext().getVertexIconTransformer()));

    return vizViewer;
  }

  private UndirectedSparseGraph<String, String> createPowerGridGraph() {
    UndirectedSparseGraph<String, String> graph = new UndirectedSparseGraph<String, String>();
    // Generator
    graph.addVertex("G1");
    // Node 1
    graph.addVertex("N1");
    // Transformer
    graph.addVertex("T1");
    // Node 2
    graph.addVertex("N2");
    // Load
    graph.addVertex("LO1");
    // Solar panel
    graph.addVertex("SP1");

    // Connecting the vertices
    graph.addEdge("G1-N1", "G1", "N1");
    graph.addEdge("T1-N1", "T1", "N1");
    graph.addEdge("T1-N2", "T1", "N2");
    graph.addEdge("LO1-N2", "LO1", "N2");
    graph.addEdge("SP1-N2", "SP1", "N2");
    return graph;
  }

  private Map<String, String> createVertexMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("G1", "powergrid-icons/generator.png");
    map.put("T1", "powergrid-icons/transformer.png");
    map.put("N1", "powergrid-icons/node.png");
    map.put("N2", "powergrid-icons/node.png");
    map.put("LO1", "powergrid-icons/motor.png");
    map.put("SP1", "powergrid-icons/pv.png");
    return map;
  }

  private Map<String, Icon> getVertexIcons() {
    Map<String, Icon> iconMap = new HashMap<String, Icon>();
    for (Map.Entry<String, String> entry : createVertexMap().entrySet()) {
      String vertexName = entry.getKey();
      String iconName = entry.getValue();
      try {
        URL imageUrl = PowerGridGraphViewer.class.getResource("/" + iconName);
        iconMap.put(vertexName, new LayeredIcon(new ImageIcon(imageUrl).getImage()));
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return iconMap;
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Power Grid Graph");
    frame.getContentPane().add(new PowerGridGraphViewer().createVisualizationViewer());
    frame.pack();
    frame.setVisible(true);
    frame.setSize(800, 800);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
