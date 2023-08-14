package commands;

import java.awt.Color;
import java.io.IOException;

import commands.ImageCommand;
import model.ImageProcessModel;

/**
 * Represents the vertical flip command which with flip an image.
 */
public class VerticalFlip implements ImageCommand {
  private final String name;

  /**
   * Public constructor for the vertical flip, which will flip a file.
   * @param name the name of the file which is to be flipped
   */
  public VerticalFlip(String name) {
    this.name = name;
  }

  @Override
  public void run(ImageProcessModel model) throws IOException {
    Color[][] reference = model.arrayOfName(this.name);
    Color[][] vFlipped = new Color[reference.length][reference[0].length];
    for (int row = 0; row < reference.length; row++) {
      //System.out.println("col: " + col + "complementary: " + (reference.length - col));
      for (int col = 0; col < reference[0].length; col++) {
        vFlipped[reference.length - 1 - row][col] = reference[row][col];
      }
    }
    model.addImage(this.name, vFlipped);
  }

  @Override
  public String commandType() {
    return "vertical-flip";
  }
}
