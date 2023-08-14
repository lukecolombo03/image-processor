package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import commands.ImageCommand;
import commands.ImageUtil;

/**
 * Represents the model segment of the Image Process program.
 */
public class ImageModel implements ImageProcessModel {
  private final Map<String, Color[][]> images;
  private final Map<String, String> extensions;

  /**
   * Public constructor for this model.
   */
  public ImageModel() {
    this.images = new HashMap<String, Color[][]>();
    this.extensions = new HashMap<String, String>();
  }

  @Override
  public void apply(String name, ImageCommand command) {
    try {
      command.run(this);
      System.out.println("Model applied to command!");
    }
    catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public void addImage(String name, Color[][] image) {
    this.images.put(name, image);
  }

  @Override
  public void addExtension(String name, String extension) {
    this.extensions.put(name, extension);
  }

  @Override
  public boolean checkEmpty() {
    return this.images.isEmpty();
  }

  @Override
  public StringBuilder convertNameToStringBuilder(String name) {
    StringBuilder builder = new StringBuilder();
    Color[][] image = this.images.get(name);
    for (int row = 0; row < image.length; row++) {
      for (int col = 0; col < image[0].length; col++) {
        Color color = image[row][col];
        builder.append(color.getRed() + System.lineSeparator() + color.getGreen()
                + System.lineSeparator() + color.getBlue() + System.lineSeparator());
      }
    }
    return builder;
  }

  @Override
  public Color[][] arrayOfName(String name) {
    return this.images.get(name);
  }

  @Override
  public String extensionOfName(String name) {
    return this.extensions.get(name);
  }

  @Override
  public BufferedImage imageContents() {
    if (this.images.containsKey("currentImage")) {
      System.out.println("has currentImage");
      return new ImageUtil().arrayToBufferedImage(this.images.get("currentImage"));
    }
    else {
      System.out.println("no image found");
      return null;
    }
  }
}