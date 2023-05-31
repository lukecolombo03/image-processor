package cs3500;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import static cs3500.model.ImplModel.convertToFourChannel;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * A Histogram object that shows a graph of all colors in an image.
 */
public class Histogram extends JPanel {
  private int[] red;
  private int[] green;
  private int[] blue;
  private int[] intensity;
  private final BufferedImage img;
  private BufferedImage transferImage;
  private final int type;

  /**
   * Constructor for A Histogram object that shows a graph of all colors in an image.
   * @param img the given image
   * @param type 0 for red, 1 for green, 2 for blue, 3 for intensity.
   */
  public Histogram(BufferedImage img, int type) {
    this.red = new int[256];
    this.green = new int[256];
    this.blue = new int[256];
    this.intensity = new int[256];
    this.img = img;
    this.type = type;

    this.init();
    getImage();
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBackground(Color.lightGray);
  }

  /**
   * constructor for a placeholder Histogram.
   */
  public Histogram() {
    this.img = null;
    this.red = null;
    this.green = null;
    this.blue = null;
    this.intensity = null;
    this.type = 3;
  }

  /**
   * Initializes the four histogram arrays.
   */
  private void init() {
    for (int col = 0; col < img.getWidth(); col++) {
      for (int row = 0; row < img.getHeight(); row++) {
        Color color = new Color(img.getRGB(col, row));

        this.red[color.getRed()]++;
        this.green[color.getGreen()]++;
        this.blue[color.getBlue()]++;
        this.intensity[(color.getRed() + color.getGreen() + color.getBlue()) / 3]++;
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    g.drawImage(transferImage, 0, 0, this);
  }


  /**
   * gets a histogram.
   */
  public void getImage() {
    int max = Math.max(getMax(this.red), Math.max(getMax(this.green),
            Math.max(getMax(this.blue), getMax(this.intensity)))) + 1;
    BufferedImage img = new BufferedImage(256, max, TYPE_INT_RGB);

    for (int counter = 0; counter < 256; counter++) {
      switch (type) {
        case 0:
          drawLine(img, counter, red[counter], convertToFourChannel(255, 0, 0));
          break;
        case 1:
          drawLine(img, counter, green[counter], convertToFourChannel(0, 255, 0));
          break;
        case 2:
          drawLine(img, counter, blue[counter], convertToFourChannel(0, 0, 255));
          break;
        case 3:
          drawLine(img, counter, intensity[counter], convertToFourChannel(255, 255, 255));
          break;
        default:
          System.out.println("how did we get here?");
          break;
      }
    }

    this.transferImage = img;
  }

  /**
   * draws a colored line on the given image in the given column from 0 to the maximum value.
   * @param img the given image.
   * @param col the given column.
   * @param max the maximum value.
   * @param fourChannel the given color
   */
  private void drawLine(BufferedImage img, int col, int max, int fourChannel) {
    for (int counter = 0; counter < max; counter++) {
      img.setRGB(col, counter, fourChannel);
    }
  }

  /**
   * Gets the max value of the given array.
   * @param array the given array.
   * @return the max value of the given array.
   */
  private int getMax(int[] array) {
    int max = 0;

    for (int counter = 0; counter < array.length; counter++) {
      if (max < array[counter]) {
        max = array[counter];
      }
    }
    return max;
  }

  /**
   * Gets image for testing.
   * @return transferImage.
   */
  public BufferedImage getTestImage() {
    return this.transferImage;
  }
}


