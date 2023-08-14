package view;

import java.awt.image.BufferedImage;

/**
 * Represents the interface for an ImageFrame enabling panels to
 * add images.
 */
public interface ImageFrame {
  /**
   * Adds image to the JFrame.
   */
  void addImage(BufferedImage image);
}
