package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * Function object to grayscale an image using its green value.
 * This means each pixel's r, g, and b values are set to the g value.
 */
public class GrayScaleGreen implements ICommand {
  private final String fileName;
  private final String filePath;

  /**
   * Constructor to create an instance of this function object with both fields.
   * @param fileName which file to perform the action on.
   * @param filePath what the new name of the file will be.
   */
  public GrayScaleGreen(String fileName, String filePath) {
    this.fileName = fileName;
    this.filePath = filePath;
  }


  @Override
  public void applyCommand(IModel m) {


    m.grayScaleGreen(this.fileName, this.filePath);
  }
}
