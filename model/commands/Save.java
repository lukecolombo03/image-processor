package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * Function object to grayscale an image using its value.
 * This means each pixel's r, g, and b values are set to the value.
 */
public class Save implements ICommand {
  private final String fileName;
  private final String filePath;

  /**
   * Constructor to create an instance of this function object with both fields.
   * @param fileName which file to perform the action on.
   * @param filePath what the new name of the file will be.
   */
  public Save(String fileName, String filePath) {
    this.fileName = fileName;
    this.filePath = filePath;
  }


  @Override
  public void applyCommand(IModel m) {
    m.save(fileName, filePath);
  }
}
