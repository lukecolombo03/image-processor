package cs3500.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import javax.imageio.ImageIO;

import cs3500.ToStringImageUtil;
import cs3500.model.commands.Blur;
import cs3500.model.commands.GrayScaleBlue;
import cs3500.model.commands.GrayScaleColorTransformation;
import cs3500.model.commands.GrayScaleGreen;
import cs3500.model.commands.GrayScaleIntensity;
import cs3500.model.commands.GrayScaleLuma;
import cs3500.model.commands.GrayScaleRed;
import cs3500.model.commands.GrayScaleValue;
import cs3500.model.commands.ICommand;
import cs3500.controller.ControllerV1;
import cs3500.controller.IController;
import cs3500.model.commands.SepiaToneColorTransformation;
import cs3500.model.commands.Sharpen;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Implementation of the model. It has
 */
public class ImplModel implements IModel {
  protected Map<String, BufferedImage> images;

  /**
   * Constructor to create a model with no images in it.
   */
  public ImplModel() {
    this.images = new HashMap<>();
  }

  /**
   * Constructor to create a model with one image in it.
   *
   * @param imageName the name of the image.
   * @param image     the image itself.
   */
  public ImplModel(String imageName, BufferedImage image) {
    this.images = new HashMap<>();
    if (imageName == null || image == null) {

      throw new IllegalArgumentException("Name or image is null");
    }
    this.images.put(imageName, image);
  }


  /**
   * Get a copy of this model's Map of String, Image.
   *
   * @return this model's images.
   */
  public Map<String, BufferedImage> getImages() {
    return new HashMap<>(this.images);
  }


  /**
   * Helper method to add an image to this model's list of images. If an entry in images already has
   * that name, then it gets overwritten.
   *
   * @param inProgramName the name of the image.
   * @param image         the image itself.
   */
  private void addImage(String inProgramName, BufferedImage image) {
    if (this.images.containsKey(inProgramName)) { // if the "in-program" name already exists
      this.images.remove(inProgramName);
      this.images.put(inProgramName, image);
      System.out.println("Image has been overwritten");
    } else {                                      // if the in-program name is new
      this.images.put(inProgramName, image);
      System.out.println("Image has been successfully loaded");
    }
  }

  public void applyCommand(ICommand cmd) {
    cmd.applyCommand(this);
  }

  /**
   * Loads an image by adding it to images with it's desired "in-program" name. The height and width
   * are retrieved with the help of ImageUtil. If the in-program name is already taken,
   * overwrite this name.
   *
   * @param fileName      the name of the file, as stored in your computer.
   * @param inProgramName the desired "in-program" name for this file.
   */
  @Override
  public void load(String fileName, String inProgramName) {
    if (fileName.endsWith(".ppm")) {
      BufferedImage image;
      String ppmText = ToStringImageUtil.readPPM(fileName);
      Scanner scanner = new Scanner(ppmText);
      String token;
      token = scanner.next();
      if (!token.equals("P3")) {
        System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      }
      int width = scanner.nextInt();
      int height = scanner.nextInt();
      int maxValue = scanner.nextInt();
      image = new BufferedImage(width, height,
              BufferedImage.TYPE_INT_RGB);
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int r = scanner.nextInt();
          int g = scanner.nextInt();
          int b = scanner.nextInt();
          image.setRGB(col, row, this.convertToFourChannel(r, g, b));
        }
      }
      this.addImage(inProgramName, image);
    } else {
      try { // use imageIO to produce a BufferedImage
        BufferedImage image = ImageIO.read(new File(fileName));
        this.addImage(inProgramName, image);
      } catch (IOException e) {
        System.out.println("There was an error trying to print your image");
      }
    }
  }


  @Override
  public void display(String fileName) {
    BufferedImage image = this.images.get(fileName);
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        int message = this.images.get(fileName).getRGB(col, row);
        System.out.println(message);
      }
    }
  }

  /**
   * Helper for save. It uses ImageIO.write() to save the file to the given file path, and also
   * handles potential IOExceptions.
   *
   * @param inProgramName the "in-program" name of the file
   * @param filePath      the file path that you want to save the image to
   * @param imageType     the type of image file - jpg, ppm, etc. Input this as "ppm", not ".ppm"
   */
  private void saveHelper(String inProgramName, String filePath, String imageType) {
    try {
      ImageIO.write(this.images.get(inProgramName), imageType, new File(filePath));
      System.out.println("File " + inProgramName + "." + imageType + "saved to " + filePath);
    } catch (Exception e) {
      System.out.println("There was an error trying to save your file");
    }
  }

  @Override
  public void save(String inProgramName, String filePath) {
    if (filePath.endsWith(".ppm")) {
      saveHelper(inProgramName, filePath, "ppm");
    } else if (filePath.endsWith(".png")) {
      saveHelper(inProgramName, filePath, "png");
    } else if (filePath.endsWith(".jpg")) {
      saveHelper(inProgramName, filePath, "jpg");
    } else if (filePath.endsWith(".jpeg")) {
      saveHelper(inProgramName, filePath, "jpeg");
    } else if (filePath.endsWith(".bmp")) {
      saveHelper(inProgramName, filePath, "bmp");
    } else {
      System.out.println("That file type is not currently supported.");
    } //maybe make it say something like "currently supported file types are: x, y, z"
    //would have to make a Map or something? like knownCommands
  }

  private void wrongNameCheck(String oldName) {
    if (!images.containsKey(oldName)) {
      System.out.println("There is no file with that name");
    }
  }


  /**
   * Takes in an individual r, g, b, and converts it to the four-channel (32-bit) representation
   * that is outputted when you do getRGB() on a BufferedImage. This makes it possible to add
   * together the value outputted by this method and the value outputted by getRGB().
   *
   * @param r red value, represented as 8 bits.
   * @param g green value, represented as 8 bits.
   * @param b value value, represented as 8 bits.
   * @return a 32-bit integer with 4 8-bit channels representing alpha, red, green, and blue.
   */
  public static int convertToFourChannel(int r, int g, int b) {
    return (r << 16) | (g << 8) | b;
  }


  //try to abstract clamping as well as the setup for every method maybe?
  //for abstraction: the only difference between this and darken are +/- and clamp to 255/0
  @Override
  public void brighten(int value, String oldName, String newName) {
    this.wrongNameCheck(oldName);

    BufferedImage newImage = images.get(oldName);
    int height = newImage.getHeight();
    int width = newImage.getWidth();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        //clamp to 255
        if ((newImage.getRGB(col, row) + this.convertToFourChannel(value, value, value))
                <= this.convertToFourChannel(255, 255, 255)) {
          newImage.setRGB(col, row, this.convertToFourChannel(255, 255, 255));
        } else {
          newImage.setRGB(col, row, newImage.getRGB(col, row)
                  + this.convertToFourChannel(value, value, value));
        }
      }
    }

    this.addImage(newName, newImage);
  }

  @Override
  public void darken(int value, String oldName, String newName) {
    this.wrongNameCheck(oldName);

    BufferedImage newImage = images.get(oldName);
    int height = newImage.getHeight();
    int width = newImage.getWidth();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        //clamp to 255
        if ((newImage.getRGB(col, row) + this.convertToFourChannel(value, value, value))
                <= this.convertToFourChannel(0, 0, 0)) {
          newImage.setRGB(col, row, this.convertToFourChannel(0, 0, 0));
        } else {
          newImage.setRGB(col, row, newImage.getRGB(col, row)
                  - this.convertToFourChannel(value, value, value));
        }
      }
    }

    this.addImage(newName, newImage);
  }

  /**
   * Helper for flipVertically and flipHorizontally that abstracts the code.
   *
   * @param isItHorizontal true if you're doing a horizontal flip, false if you're doing a vertical
   *                       flip.
   * @param oldName        the current name of the file.
   * @param newName        what you want the new name of the file to be.
   */
  private void flip(boolean isItHorizontal, String oldName, String newName) {
    this.wrongNameCheck(oldName);
    BufferedImage newImage = new BufferedImage(images.get(oldName).getWidth(),
            images.get(oldName).getHeight(), TYPE_INT_RGB);

    int width = images.get(oldName).getWidth();
    int height = images.get(oldName).getHeight();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (isItHorizontal) { // if you're flipping horizontally
          newImage.setRGB(col, height - row - 1, images.get(oldName).getRGB(col, row));
        } else {              // if you're flipping vertically
          newImage.setRGB(width - col - 1, row, images.get(oldName).getRGB(col, row));
        }
      }
    }
    this.addImage(newName, newImage);
  }

  @Override
  public void flipHorizontally(String oldName, String newName) {
    this.flip(true, oldName, newName);
  }

  @Override
  public void flipVertically(String oldName, String newName) {
    this.flip(false, oldName, newName);
  }

  /**
   * Helper method for all 6 grayscale methods.
   *
   * @param oldName             old name of the file.
   * @param newName             the new name of the file.
   * @param grayScalingVariable To determine which type of grayscale this will be.
   *                            1 = red component
   *                            2 = green component
   *                            3 = blue component
   *                            4 = value component
   *                            5 = intensity component
   *                            6 = luma component
   */
  private void grayScaleAbstraction(String oldName, String newName, int grayScalingVariable) {
    this.wrongNameCheck(oldName);
    BufferedImage newImage = images.get(oldName);
    int width = images.get(oldName).getWidth();
    int height = images.get(oldName).getHeight();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Color color = new Color(newImage.getRGB(col, row));
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int value = Math.max(r, Math.max(g, b));
        int intensity = (r + g + b) / 3;
        int luma = (int) (0.216 * r + 0.7152 * g * 0.0722 * b);
        switch (grayScalingVariable) {
          case 1:
            newImage.setRGB(col, row, convertToFourChannel(r, r, r));
            break;
          case 2:
            newImage.setRGB(col, row, convertToFourChannel(g, g, g));
            break;
          case 3:
            newImage.setRGB(col, row, convertToFourChannel(b, b, b));
            break;
          case 4:
            newImage.setRGB(col, row, convertToFourChannel(value, value, value));
            break;
          case 5:
            newImage.setRGB(col, row, convertToFourChannel(intensity, intensity, intensity));
            break;
          case 6:
            newImage.setRGB(col, row, convertToFourChannel(luma, luma, luma));
            break;
          default:
            break;
        }
      }
    }

    this.addImage(newName, newImage);
  }

  @Override
  public void grayScaleValue(String oldName, String newName) {
    this.grayScaleAbstraction(oldName, newName, 4);
  }

  @Override
  public void grayScaleIntensity(String oldName, String newName) {
    this.grayScaleAbstraction(oldName, newName, 5);
  }

  @Override
  public void grayScaleLuma(String oldName, String newName) {
    this.grayScaleAbstraction(oldName, newName, 6);
  }

  @Override
  public void grayScaleRed(String oldName, String newName) {
    this.grayScaleAbstraction(oldName, newName, 1);
  }

  @Override
  public void grayScaleGreen(String oldName, String newName) {
    this.grayScaleAbstraction(oldName, newName, 2);
  }

  @Override
  public void grayScaleBlue(String oldName, String newName) {
    this.grayScaleAbstraction(oldName, newName, 3);
  }

  @Override
  public void blur(String oldName, String newName) {
    double[][] kernel = {{.0625, .125, .0625}, {.125, .25, .125}, {.0625, .125, .0625}};

    BufferedImage newImage = images.get(oldName);
    int width = newImage.getWidth();
    int height = newImage.getHeight();

    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {
        this.applyFilterToPixel(kernel, col, row, images.get(oldName));
      }
    }
  }

  @Override
  public void sharpen(String oldName, String newName) {
    double[][] kernel = {{-.125, -.125, -.125, -.125, -.125},
      {-.125, .25, .25, .25, -.125},
      {-.125, .25, 1, .25, -.125},
      {-.125, .25, .25, .25, -.125},
      {-.125, -.125, -.125, -.125, -.125}};

    BufferedImage newImage = images.get(oldName);
    int width = newImage.getWidth();
    int height = newImage.getHeight();

    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {
        this.applyFilterToPixel(kernel, col, row, images.get(oldName));
      }
    }
  }


  /**
   * To read a .txt file.
   *
   * @param fileName the given script file (in a .txt format).
   */
  public void dashFile(String fileName) {
    BufferedReader in;

    try {
      File file = new File(fileName);
      FileWriter fw = new FileWriter(file, true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(" q");
      bw.close();

      in = new BufferedReader(new FileReader(file));
      IModel model = new ImplModel();
      IController newController = new ControllerV1(model, in, System.out);

      newController.runProgram();
    } catch (FileNotFoundException e) {
      System.out.println("No file with that name exits");
    } catch (IOException e) {
      System.out.println("There was an error transmitting");
    }
  }

  @Override
  public void grayScaleColorTransformation(String oldName, String newName) {
    double[][] matrix = {{.2126, .7152, .0722},
      {.2126, .7152, .0722},
      {.2126, .7152, .0722}};

    this.transformColor(oldName, newName, matrix);
  }

  @Override
  public void sepiaToneColorTransformation(String oldName, String newName) {
    double[][] matrix = {{.393, .769, .189},
      {.349, .686, .168},
      {.272, .534, .131}};

    this.transformColor(oldName, newName, matrix);
  }

  /**
   * Is the given pixel in this image black (meaning r,g,b are 0).
   * @param img a buffered image
   * @param col column coordinate
   * @param row row coordinate
   * @return whether the pixel is black.
   */
  private boolean isPixelBlack(BufferedImage img, int col, int row) {
    return (new Color(img.getRGB(col, row)).getRed() == 0
            && new Color(img.getRGB(col, row)).getGreen() == 0
            && new Color(img.getRGB(col, row)).getRed() == 0);
  }

  @Override
  public void downScale(String oldName, String newName, int xVal, int yVal) {
    BufferedImage img = this.getImages().get(oldName);

    double xPercent = xVal / 100.0;
    double yPercent = yVal / 100.0;

    int oldWidth = img.getWidth();
    int oldHeight = img.getHeight();
    int newWidth = (int) (oldWidth * xPercent);
    int newHeight = (int) (oldHeight * yPercent);

    for (int h = 0; h < oldHeight; h++) {

    }


  }
  @Override
  public void maskImage(String oldName, String newName, String command) {
    ArrayList<Posn> points = new ArrayList<Posn>(); //all the black pixels

    new GrayScaleColorTransformation(oldName, "temp").applyCommand(this);
    BufferedImage img = this.getImages().get("temp"); // a temporary black and white img

    for (int row = 0; row < img.getHeight(); row++) { // getting all the posns where it is black and white
      for (int col = 0; col < img.getWidth(); col++) {
        if (isPixelBlack(img, col, row)) {
          points.add(new Posn(col, row));
        }
      }
    }

    if (points.size() > 0) {
      switch(command) { // creating a new temporary image with the given thing
        case "blur":
          new Blur("temp", "temp").applyCommand(this);
          break;
        case "sharpen":
          new Sharpen("temp", "temp").applyCommand(this);
          break;
        case "sepia":
          new SepiaToneColorTransformation("temp", "temp").applyCommand(this);
          break;
        case "GrayScaleBlue":
          new GrayScaleBlue("temp", "temp").applyCommand(this);
          break;
        case "GrayScaleColorTransformation":
          new GrayScaleColorTransformation("temp", "temp").applyCommand(this);
          break;
        case "GrayScaleGreen":
          new GrayScaleGreen("temp", "temp").applyCommand(this);
          break;
        case "GrayScaleIntensity":
          new GrayScaleIntensity("temp", "temp").applyCommand(this);
          break;
        case "GrayScaleLuma":
          new GrayScaleLuma("temp", "temp").applyCommand(this);
          break;
        case "GrayScaleRed":
          new GrayScaleRed("temp", "temp").applyCommand(this);
          break;
        case "GrayScaleValue":
          new GrayScaleValue("temp", "temp").applyCommand(this);
          break;
        default:
          break;
      }

      addImage(newName, getImages().get(oldName)); // getting the final image that is a copy of the original image

      while (points.size() > 0) { // filling in the points
        getImages().get(newName).setRGB(points.get(0).x, points.get(0).y,
                getImages().get("temp").getRGB(points.get(0).x, points.get(0).y));
        points.remove(0);
      }
    }
  }


  /**
   * applies a given color transformation on a matrix.
   *
   * @param oldName the name of the original file.
   * @param newName the name of the new file.
   * @param matrix  the color transformation matrix.
   */
  public void transformColor(String oldName, String newName, double[][] matrix) {
    BufferedImage newImage = images.get(oldName);
    int width = newImage.getWidth();
    int height = newImage.getHeight();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = new Color(newImage.getRGB(col, row)).getRed();
        int g = new Color(newImage.getRGB(col, row)).getBlue();
        int b = new Color(newImage.getRGB(col, row)).getGreen();

        int newR = clamp((int) (r * matrix[0][0] + g * matrix[0][1] + b * matrix[0][2]));
        int newG = clamp((int) (r * matrix[1][0] + g * matrix[1][1] + b * matrix[1][2]));
        int newB = clamp((int) (r * matrix[2][0] + g * matrix[2][1] + b * matrix[2][2]));

        int newColor = (newR << 16) | (newG << 8) | newB;

        newImage.setRGB(col, row, newColor);
      }
    }

    this.addImage(newName, newImage);
  }

  /**
   * applies a given filter onto a given pixel.
   *
   * @param kernel the given filter kernel.
   * @param row    the row of the given pixel.
   * @param col    the column of the given pixel.
   * @param img    the image where the pixel belongs to.
   */
  private void applyFilterToPixel(double[][] kernel, int row, int col, BufferedImage img) {
    int height = img.getHeight();
    int width = img.getWidth();

    int halfLength = kernel.length / 2;     //            8  9  10  11 12
    for (int rowCounter = row - halfLength; rowCounter < row + halfLength; rowCounter++) {
      for (int colCounter = col - halfLength; colCounter < col + halfLength; colCounter++) {
        if (rowCounter < 0) {
          rowCounter = 0;
        }
        else if (colCounter < 0) {
          colCounter = 0;
        }
        else if (rowCounter > height) {
          rowCounter += Integer.MAX_VALUE;
        }
        else if (colCounter > width) {
          colCounter += Integer.MAX_VALUE;
        }
        else {
          int color = getColor(img, col, row,
                  kernel[rowCounter - row - halfLength][colCounter - col - halfLength]);
          img.setRGB(col, row, color);
        }
      }
    }
  }


  private int getColor(BufferedImage img, int row, int col, double multiplier) {
    Color color = new Color(img.getRGB(col, row));
    int r = clamp((int) (color.getRed() * multiplier));
    int g = clamp((int) (color.getGreen() * multiplier));
    int b = clamp((int) (color.getBlue() * multiplier));

    int newColor = (r << 16) | (g << 8) | b; // questionable...
    return newColor;
  }

  private int clamp(int num) {
    if (num < 0) {
      num = 0;
    }
    if (num > 255) {
      num = 255;
    }

    return num;
  }

  /**
   * a position in a plane.
   */
  class Posn {
    public int x;
    public int y;

    /**
     * constructor for Posn.
     * @param x the x position.
     * @param y the y position.
     */
    Posn(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
