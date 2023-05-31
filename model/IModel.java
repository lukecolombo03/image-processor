package cs3500.model;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * All the functionality that an image should offer.
 */
public interface IModel {

  /**
   * Create a color image. For now, the system outputs text, listing the R,G,B components of
   * each pixel.
   */
  void display(String fileName);

  /**
   * Brighten an image by a certain value.
   * @param value the value by which the image will be brightened.
   */
  void brighten(int value, String oldName, String newName);

  /**
   * Darken an image by a certain value.
   * @param value the value by which the image will be darkened.
   */
  void darken(int value, String oldName, String newName);

  /**
   * Flips an image horizontally with each pixel is reflected across the y-axis.
   */
  void flipHorizontally(String oldName, String newName);

  void flipVertically(String oldName, String newName);

  /**
   * Turns an image gray by converting each pixel's R, G, B to the pixel's value.
   */
  void grayScaleValue(String oldName, String newName);

  /**
   * Turns an image gray by converting each pixel's R, G, B to the pixel's intensity.
   */
  void grayScaleIntensity(String oldName, String newName);

  /**
   * Turns an image gray by converting each pixel's R, G, B to the pixel's luma.
   */
  void grayScaleLuma(String oldName, String newName);

  /**
   * Turns an image gray by converting each pixel's R, G, B to the pixel's R value.
   */
  void grayScaleRed(String oldName, String newName);

  /**
   * Turns an image gray by converting each pixel's R, G, B to the pixel's G value.
   */
  void grayScaleGreen(String oldName, String newName);

  /**
   * Turns an image gray by converting each pixel's R, G, B to the pixel's B value.
   */
  void grayScaleBlue(String oldName, String newName);

  /**
   * Loads an image into the array.
   */
  void load(String fileName, String inProgramFileName);

  /**
   * Save an image to the desired destination on your own hard drive (outside the program).
   * @param inProgramName which file to save.
   * @param filePath the desired directory on your computer where you want to save it.
   */
  void save(String inProgramName, String filePath);

  /**
   * Get the Map of images of this model.
   * @return
   */
  Map<String, BufferedImage> getImages();

  /**
   * Applies "Gaussian Blur" on an image.
   * @param oldName the name of the file to do the operation on.
   * @param newName the name of the new file.
   */
  void blur(String oldName, String newName);

  /**
   * sharpens an image.
   * @param oldName the name of the file to do the operation on.
   * @param newName the name of the new file.
   */
  void sharpen(String oldName, String newName);

  /**
   * applies a grayScale color transformation on a given image.
   * @param oldName the name of the file to do the operation on.
   * @param newName the name of the new file.
   */
  void grayScaleColorTransformation(String oldName, String newName);

  /**
   * applies a sepia tone color transformation on a given image.
   * @param oldName the name of the file to do the operation on.
   * @param newName the name of the new file.
   */
  void sepiaToneColorTransformation(String oldName, String newName);

  /**
   * runs the given script file.
   * @param fileName the given script file (in a .txt format).
   */
  void dashFile(String fileName);

  /**
   * downscales the given image to the given percents.
   * @param oldName the name of the file to do the operation on.
   * @param newName the name of the new file.
   * @param xVal how much to downscale the x plane.
   * @param yVal how much to downscale the y plane.
   */
  void downScale(String oldName, String newName, int xVal, int yVal);

  /**
   * applies a given transformation on a given image.
   * @param oldName the name of the file to do the operation on.
   * @param newName the name of the new file.
   * @param command the given command.
   */
  void maskImage(String oldName, String newName, String command);

}
