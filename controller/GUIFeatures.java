package cs3500.controller;

import java.awt.image.BufferedImage;

/**
 * This interface will have methods that are application specific events, not Swing specific events.
 * Every method here is like a request from the user—so everything the user might want to do should
 * be a method. The controller will have a features field (composition!) so it can use this code
 * without having to implement this interface.
 *  Avoid any-view specific classes in the Features interface (what does this mean? who knows).
 * Definitely avoid any mention of Swing-specific events though. The whole idea is that each method
 * here is a high level request from the user, rather than a low-level Swing event.
 * Some events could be entirely handled by the view, and thus GUIFeature and the controller never
 * need to know about them. One example is scrolling a single text file
 * When a features event is raised to the controller's attention, the view shouldn't do anything
 * quite yet, but rather wait for permission from the controller. Features are like requests, not
 * demands.
 *
 */
public interface GUIFeatures {

  void save();

  void load();

  void showImage();

  /**
   * if the image is larger than the frame size, moves the image.
   * @param vertical how far up or down to move.
   * @param horizontal how far left or right to move.
   */
  void scroll(int vertical, int horizontal);

  /**
   * updates the histogram (called every action).
   * @param image the image that is currently being used.
   */
  void updateHistogram(BufferedImage image);

  void exitProgram();

  void blur();

  void flipHorizontally();

  void flipVertically();

  void grayScaleBlue();

  void grayScaleColorTransformation();

  void grayScaleGreen();

  void grayScaleIntensity();

  void grayScaleLuma();

  void grayScaleRed();

  void grayScaleValue();

  void sepiaToneColorTransformation();

  void sharpen();
}
