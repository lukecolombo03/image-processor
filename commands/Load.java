package commands;

import java.awt.Color;
import java.io.IOException;

import model.ImageProcessModel;

/**
 * Represents the commands.Load command which implements the commands.ImageCommand.
 */
public class Load implements ImageCommand {
  private final String name;
  private final Color[][] image;
  private final String extension;

  /**
   * Public constructor for the load command.
   * @param name the name to be added
   * @param filename the image to be added as a 2d array of Colors
   */
  public Load(String name, String filename) throws IOException {
    this.name = name;
    ImageUtil util = new ImageUtil();
    this.image = util.convertToArray(filename);
    this.extension = util.extOfFile(filename);
  }

  @Override
  public void run(ImageProcessModel model) {
    model.addImage(this.name, this.image);
    model.addExtension(this.name, this.extension);
  }

  @Override
  public String commandType() {
    return "load";
  }
}