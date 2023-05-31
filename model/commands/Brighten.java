package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * Function object to cs3500.commands.brighten an image.
 * This means each pixel's r, g, and b values are raised by a value.
 */
public class Brighten implements ICommand {
  private final int value;
  private final String fileName;
  private final String filePath;

  /**
   * Constructor to create an instance of this function object with all three fields.
   * @param value desired value to brighten the image by.
   * @param fileName which file to perform the action on.
   * @param filePath what the new name of the file will be.
   */
  public Brighten(int value, String fileName, String filePath) {
    if (value <= 0) {
      throw new IllegalArgumentException("The value is less than or equal to Zero");
    }

    this.value = value;
    this.fileName = fileName;
    this.filePath = filePath;
  }

  @Override
  public void applyCommand(IModel m) {
    m.brighten(this.value, this.fileName, this.filePath);
  }
}
