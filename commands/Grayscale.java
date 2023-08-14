package commands;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.ImageProcessModel;


/**
 * a  public class to implement the blur, sharpen and sepia method.
 */
public class Grayscale implements ImageCommand {
  private final String name;
  private final String nameDes;
  private final String wantedColor;
  private final Map<String, String> componentTypes;

  private Color[][] outputcolor;
  private double pixelR;
  private double pixelG;
  private double pixelB;

  /**
   * make function of the Component.
   *
   * @param name        is the name of the processing image.
   * @param nameDes     is the name of the processed image.
   * @param wantedColor is which type of process user want to use.
   */
  public Grayscale(String name, String nameDes, String wantedColor) {
    this.name = name;
    this.nameDes = nameDes;
    this.wantedColor = wantedColor;
    this.componentTypes = new HashMap<>();
    componentTypes.put("blur", "blur-component");
    componentTypes.put("sharpen", "sharpen-component");
    componentTypes.put("sepia", "sepia-component");
  }

  @Override
  public void run(ImageProcessModel model) throws IOException {
    Color[][] outputcolor = model.arrayOfName(name);
    Color[][] outputcolor2 = new Color[model.arrayOfName(name).length]
            [model.arrayOfName(name)[0].length];
    for (int row = 0; row < outputcolor.length; row++) {
      for (int col = 0; col < outputcolor[0].length; col++) {
        Color color = outputcolor[row][col];
        Color newColor;
        switch (wantedColor) {
          case "blur":
            newColor = this.calculateBlur(model, row, col);
            outputcolor2[row][col] = newColor;
            break;

          case "sharpen":
            newColor = this.calculateSharpen(model, row, col);
            outputcolor2[row][col] = newColor;
            break;

          case "sepia":
            double sepiaR = color.getRed() * 0.393 +
                    color.getGreen() * 0.769 + color.getBlue() * 0.189;
            double sepiaG = color.getRed() * 0.349 +
                    color.getGreen() * 0.686 + color.getBlue() * 0.168;
            double sepiaB = color.getRed() * 0.292 +
                    color.getGreen() * 0.534 + color.getBlue() * 0.131;
            if (sepiaR > 255) {
              sepiaR = 255;
            }
            if (sepiaG > 255) {
              sepiaG = 255;
            }
            if (sepiaB > 255) {
              sepiaB = 255;
            }
            newColor = new Color((int) sepiaR, (int) sepiaG, (int) sepiaB);
            outputcolor[row][col] = newColor;
            break;

          default:
            throw new IOException("Error input!");
        }
      }
    }
    if (this.wantedColor.equals("blur") || this.wantedColor.equals("sharpen")) {
      model.addImage(nameDes, outputcolor2);
    } else {
      model.addImage(nameDes, outputcolor);
    }
  }

  @Override
  public String commandType() {
    return componentTypes.getOrDefault(wantedColor, "");
  }

  /**
   * Calculates the color for the blurred value for pixel for given row, col.
   * @param model the model where we receive our 2d array
   * @param row the row of the image to be changed
   * @param col the col of the image to be changed
   * @return the adjusted pixel
   */
  private Color calculateBlur(ImageProcessModel model, int row, int col) {
    outputcolor = model.arrayOfName(this.name);
    Color color = outputcolor[row][col];
    pixelR = color.getRed() * 0.25;
    pixelG = color.getGreen() * 0.25;
    pixelB = color.getBlue() * 0.25;

    double weight;
    for (int counterROW = -1 ; counterROW < 2; counterROW++) {
      for (int counterCOL = -1 ; counterCOL < 2; counterCOL++) {
        if (counterROW == 0 && counterCOL == 0) {
          continue;
        }
        if (counterROW == 0 || counterCOL == 0) {
          weight = 0.125;
        }
        else {
          weight = 0.0625;
        }
        helperRGB(counterROW,counterCOL,weight);
      }
    }

    return new Color((int) pixelR, (int) pixelG, (int) pixelB);
  }

  /**
   * Calculates the color for the blurred value for pixel for given row, col.
   * @param model the model where we receive our 2d array
   * @param row the row of the image to be changed
   * @param col the col of the image to be changed
   * @return the adjusted pixel
   */
  private Color calculateSharpen(ImageProcessModel model, int row, int col) {
    outputcolor = model.arrayOfName(this.name);
    Color color = outputcolor[row][col];

    pixelR = color.getRed();
    pixelG = color.getGreen();
    pixelB = color.getBlue();
    double weight;
    for (int counterROW = -2 ; counterROW < 3; counterROW++) {
      for (int counterCOL = -2 ; counterCOL < 3; counterCOL++) {
        if (counterROW == 0 && counterCOL == 0) {
          continue;
        }
        if (counterROW < 2 && -2 < counterROW && counterCOL < 2 && -2 < counterCOL) {
          weight = 0.25;
        }
        else {
          weight = -0.125;
        }
        helperRGB(counterROW,counterCOL,weight);
      }
    }

    if (pixelR > 255) {
      pixelR = 255;
    }
    if (pixelG > 255) {
      pixelG = 255;
    }
    if (pixelB > 255) {
      pixelB = 255;
    }
    if (pixelR < 0) {
      pixelR = 0;
    }
    if (pixelG < 0) {
      pixelG = 0;
    }
    if (pixelB < 0) {
      pixelB = 0;
    }
    return new Color((int) pixelR, (int) pixelG, (int) pixelB);
  }

  /**
   * Helps build the int components of the RGB.
   * @param row the row to look at
   * @param col the col to look at
   * @param weight the weight of the pixel
   */
  private void helperRGB(int row, int col, double weight) {
    try {
      pixelR = pixelR + outputcolor[row][col].getRed() * weight;
      pixelG = pixelG + outputcolor[row][col].getGreen() * weight;
      pixelB = pixelB + outputcolor[row][col].getBlue() * weight;
    }
    catch (Exception e) {
      // do not nothing when out of boundary.
    }
  }
}
