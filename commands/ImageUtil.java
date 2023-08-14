package commands;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.awt.Color;

import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Converts the given image file and creates a 2d array of Colors.
   * @param filename the name of the file to be converted into a 2d array of Colors
   * @return the converted 2d array of Colors
   * @throws IOException when the file cannot be read
   */
  public Color[][] convertToArray(String filename) throws IOException {
    String ext = extOfFile(filename);
    if (ext.equals("ppm")) {
      return ppmToArray(filename);
    }
    else {
      return convertFromOther(filename);
    }
  }

  /**
   * Reads the given file and converts it into a 2d array with Colors.
   * @param filename the name of the file to be converted
   * @return the 2d array of Colors which represents an image
   */
  private Color[][] ppmToArray(String filename) throws FileNotFoundException {
    Scanner sc;
    sc = new Scanner(new FileInputStream(filename));
    StringBuilder builder = new StringBuilder();

    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      builder.append(s + System.lineSeparator());
    }

    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    Color[][] image = new Color[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        image[row][col] = new Color(r, g, b);
      }
    }
    return image;
  }

  /**
   * Converts the given file into a 2d array of Color.
   * @param file file to be converted into a 2d array
   * @return a 2d array of Colors to represent the given image file
   * @throws IOException when the given file is not able to be read
   */
  private Color[][] convertFromOther(String file) throws IOException {
    BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    Color[][] image = new Color[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        image[row][col] = new Color(bufferedImage.getRGB(col, row));
      }
    }
    return image;
  }

  /**
   * Returns the buffered image of a 2d array of Colors.
   */
  public BufferedImage arrayToBufferedImage(Color[][] arrayImage) {
    int height = arrayImage.length;
    int width = arrayImage[0].length;
    BufferedImage bufferedImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Color c = arrayImage[row][col];
        bufferedImage.setRGB(col, row, c.getRGB());
      }
    }
    return bufferedImage;
  }

  /**
   * Returns the extension of the given file if it exists.
   * @param file the file to be checked for its extension
   * @return the name of the extension of the file
   */
  public String extOfFile(String file) {
    int len = file.length();
    String extension = "";
    for (int i = 0; i < len; i++) {
      char c = file.charAt(len - 1 - i);
      if (c == '.') {
        return extension;
      }
      extension = c + extension;
    }
    return extension;
  }
}

