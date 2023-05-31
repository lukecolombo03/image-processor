package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * Function object to flip an image horizontally.
 * This means each pixel is reflected across the y-axis.
 */
public class FlipHorizontally implements ICommand {
  private final String fileName;
  private final String filePath;

  /**
   * Constructor to create an instance of this function object with both fields.
   * @param fileName which file to perform the action on.
   * @param filePath what the new name of the file will be.
   */
  public FlipHorizontally(String fileName, String filePath) {
    this.fileName = fileName;
    this.filePath = filePath;
  }

  @Override
  public void applyCommand(IModel m) {
    m.flipHorizontally(this.fileName, this.filePath);
  }
}