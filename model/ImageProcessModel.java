package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import commands.ImageCommand;

/**
 * Represents the interface of an image process model.
 */
public interface ImageProcessModel {

  /**
   * Applies the given command to the buffered image coreresponding to the given name.
   * @param name name of the image to be modified
   * @param command command to be applied to the given image corresponding to given name
   */
  void apply(String name, ImageCommand command) throws IOException;

  /**
   * Adds the given name and maps it to the image on the list of images this model holds.
   * @param name the name of the image
   * @param image the image to be added stored as a 2d array of Colors
   */
  void addImage(String name, Color[][] image);

  /**
   * Adds the given name and maps it to the provided extension, which the model holds.
   * @param name the name of the image
   * @param extension the extension of the image
   */
  void addExtension(String name, String extension);

  /**
   * Check if this model is holding any images.
   */
  boolean checkEmpty();

  /**
   * Converts the 2d array associated with the given name into a stringbuilder.
   * @param name the name associated with the 2d array of colors to be converted into
   *             a StringBuilder
   * @return the colors of the 2d array associated with the given name in StringBuilder
   */
  StringBuilder convertNameToStringBuilder(String name);

  /**
   * Returns the 2d array of Colors associated with the given name.
   * @param name the name associated with the 2d array of colors
   * @return the 2d array of colors
   */
  Color[][] arrayOfName(String name);

  /**
   * Returns the extension of the given name.
   * @param name the name being searched for its extension
   * @return the extension of the given name
   */
  String extensionOfName(String name);

  /**
   * Returns the BufferedImage content of the given name.
   * @return the BufferedImage of the given name
   */
  BufferedImage imageContents();
}
