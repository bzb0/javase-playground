package com.bzb.javase.graph.jung;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

public class CustomVertexEdgeTransformerTest {

  public static void main(String[] args) {
    // Defining a custom vertex painter/transformer
    Transformer<CustomVertex, Paint> vertexPainter = new Transformer<CustomVertex, Paint>() {
      public Paint transform(CustomVertex i) {
        return Color.GREEN;
      }
    };
    // Defining a custom edge stroke
    Transformer<WeightedEdge, Stroke> edgeStroke = new Transformer<WeightedEdge, Stroke>() {
      public Stroke transform(WeightedEdge s) {
        return new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
      }
    };

    BasicVisualizationServer<CustomVertex, WeightedEdge> visualizationServer = new BasicVisualizationServer<>(
        new CircleLayout(JungGraphFactory.createDirectedGraph()));
    visualizationServer.setPreferredSize(new Dimension(600, 600));
    visualizationServer.getRenderContext().setVertexFillPaintTransformer(vertexPainter);
    visualizationServer.getRenderContext().setEdgeStrokeTransformer(edgeStroke);
    visualizationServer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<CustomVertex>());
    visualizationServer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<WeightedEdge>());
    visualizationServer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

    JFrame frame = new JFrame("Custom Vertex & Edge Transformers");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(visualizationServer);
    frame.pack();
    frame.setVisible(true);
  }
}
