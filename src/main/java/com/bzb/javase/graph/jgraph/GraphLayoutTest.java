package com.bzb.javase.graph.jgraph;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

public class GraphLayoutTest {

  public static void main(String[] args) {
    // Instantiating the graph
    GraphModel model = new DefaultGraphModel();
    GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());
    JGraph graph = new JGraph(model, view);
    graph.setScale(2);

    DefaultGraphCell[] cells = new DefaultGraphCell[3];
    // Setting up Hello node in the graph
    cells[0] = new DefaultGraphCell("Hello");
    cells[0].add(new DefaultPort());
    GraphConstants.setBounds(cells[0].getAttributes(), new Rectangle2D.Double(20, 20, 60, 25));
    GraphConstants.setBackground(cells[0].getAttributes(), Color.orange);
    GraphConstants.setOpaque(cells[0].getAttributes(), true);

    // Setting up World node in the graph
    cells[1] = new DefaultGraphCell("World");
    cells[1].add(new DefaultPort());
    GraphConstants.setBounds(cells[1].getAttributes(), new Rectangle2D.Double(300, 300, 60, 25));
    GraphConstants.setGradientColor(cells[1].getAttributes(), Color.red);
    GraphConstants.setOpaque(cells[1].getAttributes(), true);

    // Creating an edge between the two cells
    DefaultEdge edge = new DefaultEdge();
    // Adding buckle points to the edge.
    List<Point> bucklePoints = new ArrayList<>();
    bucklePoints.add(new Point(20, 100));
    bucklePoints.add(new Point(200, 150));
    bucklePoints.add(new Point(300, 300));
    GraphConstants.setPoints(edge.getAttributes(), bucklePoints);
    GraphConstants.setLineColor(edge.getAttributes(), Color.RED);
    GraphConstants.setLineWidth(edge.getAttributes(), 3);
    edge.setSource(cells[0].getChildAt(0));
    edge.setTarget(cells[1].getChildAt(0));
    cells[2] = edge;

    // Adding the cells to the graph layout.
    graph.getGraphLayoutCache().insert(cells);

    /* Creating a JFrame to display the graph. */
    JFrame frame = new JFrame("GraphLayout Test");
    frame.getContentPane().add(new JScrollPane(graph));
    frame.pack();
    frame.setVisible(true);
    frame.setSize(800, 800);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}
