package com.bzb.javase.graph.jung.powernetwork;

import edu.uci.ics.jung.visualization.LayeredIcon;
import edu.uci.ics.jung.visualization.renderers.Checkmark;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Icon;
import org.apache.commons.collections15.Transformer;

/**
 * Item listener which adds a check mark icon to the vertex.
 *
 * @param <V> The vertex type.
 */
public class CheckMarkItemListener<V> implements ItemListener {

  private Icon checkMarkIcon;
  private Transformer<V, Icon> vertexIconTransformer;

  public CheckMarkItemListener(Transformer<V, Icon> vertexIconTransformer) {
    this.vertexIconTransformer = vertexIconTransformer;
    this.checkMarkIcon = new Checkmark();
  }

  public void itemStateChanged(ItemEvent e) {
    Icon icon = vertexIconTransformer.transform((V) e.getItem());
    if (icon != null && icon instanceof LayeredIcon) {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        ((LayeredIcon) icon).add(checkMarkIcon);
      } else {
        ((LayeredIcon) icon).remove(checkMarkIcon);
      }
    }
  }
}
