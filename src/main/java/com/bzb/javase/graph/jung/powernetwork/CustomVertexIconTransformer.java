package com.bzb.javase.graph.jung.powernetwork;

import edu.uci.ics.jung.visualization.decorators.DefaultVertexIconTransformer;
import java.util.Map;
import javax.swing.Icon;
import org.apache.commons.collections15.Transformer;

public class CustomVertexIconTransformer<V> extends DefaultVertexIconTransformer<V> implements Transformer<V, Icon> {

  private boolean imageFilled = true;
  private boolean imageOutlined = false;

  public CustomVertexIconTransformer(Map<V, Icon> iconMap) {
    super();
    this.iconMap = iconMap;
  }

  public boolean isImageFilled() {
    return imageFilled;
  }

  public void setImageFilled(boolean imageFilled) {
    this.imageFilled = imageFilled;
  }

  public boolean isImageOutlined() {
    return imageOutlined;
  }

  public void setImageOutlined(boolean imageOutlined) {
    this.imageOutlined = imageOutlined;
  }

  public Icon transform(V v) {
    if (imageFilled) {
      return iconMap.get(v);
    } else {
      return null;
    }
  }
}
