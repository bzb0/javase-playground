package com.bzb.javase.graph.jgraph;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.jgraph.JGraph;

/**
 * Example usage of a the JGraph library for creating visual graphs.
 */
public class JGraphTest extends JFrame {

  public JGraphTest() {
    super("JGraph Test");
    JGraph graph = new JGraph();
    graph.setAntiAliased(true);
    graph.setScale(2);

    this.getContentPane().add(new JScrollPane(graph), BorderLayout.CENTER);
    this.pack();
    this.setVisible(true);
    this.setSize(1000, 600);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    new JGraphTest();
  }
}
