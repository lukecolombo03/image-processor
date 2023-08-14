package commands;

import java.awt.Color;
import java.io.IOException;

import model.ImageProcessModel;

/**
 * Represents the vertical flip command which with flip an image.
 */
public class HorizontalFlip implements ImageCommand {
  private final String name;

  /**
   * Public constructor for the vertical flip, which will flip a file.
   * @param name the name of the file which is to be flipped
   */
  public HorizontalFlip(String name) {
    this.name = name;
  }

  @Override
  public void run(ImageProcessModel model) throws IOException {
    Color[][] reference = model.arrayOfName(this.name);
    Color[][] hFlipped = new Color[reference.length][reference[0].length];
    for (int row = 0; row < reference.length; row++) {
      for (int col = 0; col < reference[0].length; col++) {
        hFlipped[row][reference[0].length - 1 - col] = reference[row][col];
      }
    }
    model.addImage(this.name, hFlipped);
  }

  @Override
  public String commandType() {
    return "horizontal-flip";
  }
}
