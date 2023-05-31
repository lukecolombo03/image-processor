package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * a command object for grayscale color transformation.
 */
public class GrayScaleColorTransformation implements ICommand {
  private String fileName;
  private String filePath;

  /**
   * a command object for grayscale color transformation.
   * @param fileName the name of the previous file.
   * @param filePath the name of the new file.
   */
  public GrayScaleColorTransformation(String fileName, String filePath) {
    this.fileName = fileName;
    this.filePath = filePath;
  }

  @Override
  public void applyCommand(IModel m) {
    m.grayScaleColorTransformation(fileName, filePath);
  }
}
