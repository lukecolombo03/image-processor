package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * The class representing a panel for displaying histograph graphs.
 */
public class Histograms extends JPanel {
  private int[] colorRed;
  private int[] colorGreen;
  private int[] colorBlue;
  private int[] colorI;
  private Color[][] outputColor;
  private int maxHeight;

  /**
   * The public constructor for creating the Histograms graph.
   */
  public Histograms() {
    super();
    this.colorRed = new int[256];
    this.colorGreen = new int[256];
    this.colorBlue = new int[256];
    this.colorI = new int[256];
    this.maxHeight = 0;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int originX = 100;
    int originY = 420;
    for (int counter = 0; counter < 256; counter++) {
      if (colorRed[counter] != -1) {
        g.setColor(Color.RED);
        g.fillRect(originX,  originY - colorRed[counter],2,colorRed[counter]);
      }
      if (colorGreen[counter] != -1) {
        g.setColor(Color.green);
        g.fillRect(originX, originY - colorGreen[counter] ,2,colorGreen[counter]);
      }
      if (colorBlue[counter] != -1) {
        g.setColor(Color.blue);
        g.fillRect(originX, originY - colorBlue[counter],2,colorBlue[counter]);
      }
      if (colorI[counter] != -1) {
        g.setColor(Color.gray);
        g.fillRect(originX, originY - colorI[counter],2,colorI[counter]);
      }
      if (originX < 612) {
        originX += 2;
      }
    }
    this.repaint();
  }

  /**
   * Analyzes the 2d array of Colors and computes the frequency of each Color.
   * @param red true when only calculating the red component of a 2d array
   * @param green true when only calculating the green component of a 2d array
   * @param blue true when only calculating the blue component of a 2d array
   * @param intensity true when only calculating the intensity component of a 2d array
   */
  public void analysis(boolean red, boolean green, boolean blue,
                boolean intensity) {
    for (int counter = 0; counter < 256; counter++) {
      colorRed[counter] = -1;
      colorGreen[counter] = -1;
      colorBlue[counter] = -1;
      colorI[counter] = -1;
    }
    int num;
    for (int row = 0; row < outputColor.length; row++) {
      for (int col = 0; col < outputColor[0].length; col++) {
        if (red) {
          num = outputColor[row][col].getRed();
          colorRed[num]++;
        }
        if (green) {
          num = outputColor[row][col].getGreen();
          colorGreen[num]++;
        }
        if (blue) {
          num = outputColor[row][col].getBlue();
          colorBlue[num]++;
        }
        if (intensity) {
          int average = (outputColor[row][col].getGreen() + outputColor[row][col].getRed() +
                  outputColor[row][col].getBlue()) / 3;
          colorI[average]++;
        }
      }
    }
    this.findMaxHeight();
    this.scaleFrequency();
  }

  /**
   * Assigns the histogram's 2d array of Color to the given one.
   * @param image the 2d array of color to be assigned
   */
  public void setColorArray(Color[][] image) {
    int maxPixels;

    this.outputColor = image;
    maxPixels = image.length * image[0].length;
    System.out.println(image.length + " " + image[0].length);
  }

  /**
   * Determines whether the histogram can display its contents by checking
   * if it has an image contained.
   * @return true when the graph can be displayed
   */
  public boolean containsImageReference() {
    return this.outputColor != null;
  }

  /**
   * Finds the maximum frequency(aka height here) of a Color in the image stored.
   */
  private void findMaxHeight() {
    for (int width = 0; width < 256; width++) {
      if (this.maxHeight < this.colorRed[width]) {
        this.maxHeight = this.colorRed[width];
      }
      if (this.maxHeight < this.colorGreen[width]) {
        this.maxHeight = this.colorGreen[width];
      }
      if (this.maxHeight < this.colorBlue[width]) {
        this.maxHeight = this.colorBlue[width];
      }
      if (this.maxHeight < this.colorI[width]) {
        this.maxHeight = this.colorI[width];
      }
    }
  }

  /**
   * Scales down the height so that it can appropriately be displayed in the JFrame
   * that this histogram is displayed on.
   */
  private void scaleFrequency() {
    for (int width = 0; width < 256; width++) {
      this.colorRed[width] = this.calculateNewFrequency(this.colorRed[width]);
      this.colorGreen[width] = this.calculateNewFrequency(this.colorGreen[width]);
      this.colorBlue[width] = this.calculateNewFrequency(this.colorBlue[width]);
      this.colorI[width] = this.calculateNewFrequency(this.colorI[width]);
    }
  }

  /**
   * Calculates the adjusted frequency for optimal display.
   * @param frequency the number of times a color appears
   * @return adjusted number that is proportional to the maximum frequency and size
   */
  private int calculateNewFrequency(int frequency) {
    return (int) Math.floor(((double) frequency / (double) this.maxHeight) * 400);
  }
}
