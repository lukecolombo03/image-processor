package commands;

import java.awt.Color;
import java.io.IOException;

import model.ImageProcessModel;

/**
 * The brighten class command which darkens or brightens an image.
 */
public class Brighten implements ImageCommand {
  private final String name;
  private final int increment;

  /**
   * The public constructor for the brighten command.
   * @param increment the value to adjust the brightness of the image by
   */
  public Brighten(String name, int increment) {
    this.name = name;
    this.increment = increment;
  }

  @Override
  public void run(ImageProcessModel model) throws IOException {
    Color[][] reference = model.arrayOfName(this.name);
    Color[][] brightenedImage = new Color[reference.length][reference[0].length];
    for (int row = 0; row < reference.length; row++) {
      for (int col = 0; col < reference[0].length; col++) {
        brightenedImage[row][col] = this.adjustColors(reference[row][col], this.increment);
      }
    }
    model.addImage(this.name, brightenedImage);
  }

  @Override
  public String commandType() {
    return "brighten";
  }

  /**
   * Changes the RGB values of the given color by the given increment value.
   * @param color the color to be modified
   * @param increment the value by which to change the RGB values of the given color
   * @return the adjusted given color by the increment
   */
  private Color adjustColors(Color color, int increment) {
    if (color == null) {
      throw new IllegalArgumentException();
    }
    return new Color(this.validateValues(color.getRed() + increment),
            this.validateValues(color.getGreen() + increment),
            this.validateValues(color.getBlue() + increment));
  }

  /**
   * Ensures that the given integer value is within 0-255 which is the value that each RGB values
   * can be.
   * @param total the total value after adding the increment with the current value of any
   *              component of RGB
   * @return the adjusted value if the total value is outside the value of 0-255
   */
  private int validateValues(int total) {
    if (total < 0) {
      return 0;
    }
    else if (total > 255) {
      return 255;
    }
    else {
      return total;
    }
  }
}
