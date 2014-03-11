package com.bzb.javase.graph.jgraph;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class GraphFactoryTest {

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.getContentPane().add(new JScrollPane(JGraphFactory.createGraph()), BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    frame.setSize(1200, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
