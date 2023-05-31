package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * Function object to flip an image vertically.
 * This means each pixel is reflected across the x-axis.
 */
public class FlipVertically implements ICommand {
  private final String fileName;
  private final String filePath;

  /**
   * Constructor to create an instance of this function object with all three fields.
   * @param fileName which file to perform the action on.
   * @param filePath what the new name of the file will be.
   */
  public FlipVertically(String fileName, String filePath) {
    this.fileName = fileName;
    this.filePath = filePath;
  }

  @Override
  public void applyCommand(IModel m) {


    m.flipVertically(this.fileName, this.filePath);
  }
}