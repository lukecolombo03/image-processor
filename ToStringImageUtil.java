package cs3500;

import java.awt.Color;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method
 * as required.
 */
public class ToStringImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static String readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    return builder.toString();

  }

  /**
   * demo main.
   *
   * @param args arguments.
   */
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "sample.ppm";
    }

    ToStringImageUtil.readPPM(filename);
  }

  /**
   * gets the luma of the given pixel, as outputted by BufferedImage.getRGB().
   * @param pixelRGB the given pixel.
   * @return the luma of the given pixel.
   */
  public static int getLuma(int pixelRGB) {
    Color color = new Color(pixelRGB);
    return (int) (0.216 * color.getRed() + 0.7152 * color.getGreen() * 0.0722 * color.getBlue());
  }

  /**
   * gets the intensity of the given pixel, as outputted by BufferedImage.getRGB().
   * @param pixelRGB the given pixel.
   * @return the intensity of the given pixel.
   */
  public static int getIntensity(int pixelRGB) {
    Color color = new Color(pixelRGB);
    return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
  }

  /**
   * gets the value of the given pixel, as outputted by BufferedImage.getRGB().
   * @param pixelRGB the given pixel.
   * @return the value of the given pixel.
   */
  public static int getValue(int pixelRGB) {
    Color color = new Color(pixelRGB);
    return Math.max(color.getRed(), Math.max(color.getGreen(), color.getBlue()));
  }

  /**
   * Get the red component of a given pixel, as outputted by BufferedImage.getRGB().
   * @param pixelRGB the given pixel.
   * @return the red component of the given pixel.
   */
  public static int getR(int pixelRGB) {
    Color color = new Color(pixelRGB);
    return color.getRed();
  }

  /**
   * Get the green component of a given pixel, as outputted by BufferedImage.getRGB().
   * @param pixelRGB the given pixel.
   * @return the green component of the given pixel.
   */
  public static int getG(int pixelRGB) {
    Color color = new Color(pixelRGB);
    return color.getGreen();
  }

  /**
   * Get the blue component of a given pixel, as outputted by BufferedImage.getRGB().
   * @param pixelRGB the given pixel.
   * @return the blue component of the given pixel.
   */
  public static int getB(int pixelRGB) {
    Color color = new Color(pixelRGB);
    return color.getBlue();
  }

  /**
   * Convert a pixel int (as outputted by getRGB()) to a Color object.
   * @param pixelRGB a given pixel.
   * @return that pixel as a Color object.
   */
  public static Color getColor(int pixelRGB) {
    return new Color(pixelRGB);
  }
}

