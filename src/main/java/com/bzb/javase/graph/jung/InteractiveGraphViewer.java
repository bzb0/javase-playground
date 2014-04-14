package com.bzb.javase.graph.jung;

import static com.bzb.javase.graph.jung.JungGraphFactory.createDirectedGraph;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ViewScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class InteractiveGraphViewer {

  public static DefaultModalGraphMouse<CustomVertex, WeightedEdge> createGraphMouse() {
    return new DefaultModalGraphMouse<CustomVertex, WeightedEdge>() {
      protected void loadPlugins() {
        pickingPlugin = new PickingGraphMousePlugin();
        animatedPickingPlugin = new AnimatedPickingGraphMousePlugin();
        translatingPlugin = new TranslatingGraphMousePlugin(InputEvent.BUTTON1_MASK);
        scalingPlugin = new ScalingGraphMousePlugin(new ViewScalingControl(), 0);
        rotatingPlugin = new RotatingGraphMousePlugin();
        shearingPlugin = new ShearingGraphMousePlugin();
        add(scalingPlugin);
        setMode(Mode.PICKING);
      }
    };
  }

  public static void main(String[] args) {
    // Creating a visualization viewer that will display the vertex and edge names
    VisualizationViewer<CustomVertex, WeightedEdge> visualizationViewer = new VisualizationViewer<>(new CircleLayout(createDirectedGraph()));
    visualizationViewer.setGraphMouse(createGraphMouse());
    visualizationViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<CustomVertex>());
    visualizationViewer.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<WeightedEdge>());
    visualizationViewer.setPreferredSize(new Dimension(800, 800));
    visualizationViewer.setDoubleBuffered(true);

    JFrame frame = new JFrame("Interactive Graph View");
    frame.getContentPane().add(visualizationViewer);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
