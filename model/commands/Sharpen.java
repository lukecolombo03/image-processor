package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * a command object for sharpening an image.
 */
public class Sharpen implements ICommand {
  private String fileName;
  private String filePath;

  /**
   * a command object sharpening an image.
   * @param fileName the name of the old file.
   * @param filePath the name of the new file.
   */
  public Sharpen(String fileName, String filePath) {
    this.fileName = fileName;
    this.filePath = filePath;
  }

  @Override
  public void applyCommand(IModel m) {
    m.blur(fileName, filePath);
  }
}
