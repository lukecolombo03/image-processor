package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import controller.Features;

/**
 * An interface for the program UI.
 */
public interface ImageProcessGUIView {
  /**
   * It add features.
   * @param controller is the input controller.
   */
  void addFeature(Features controller);

  /**
   * It updated the image window.
   * @param image is the new image.
   */
  void updateImageWindow(BufferedImage image);

  /**
   * It initalize a histogram from uploaded image.
   * @param image is the new image.
   */
  void setupHistogram(Color[][] image);
}
