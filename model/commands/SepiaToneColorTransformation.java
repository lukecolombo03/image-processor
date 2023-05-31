package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * a command object for a sepia tone color tranformation.
 */
public class SepiaToneColorTransformation implements ICommand {
  private String fileName;
  private String filePath;

  /**
   * a command object for a sepia tone color transformation.
   * @param fileName the name of the old file.
   * @param filePath the name of the new file.
   */
  public SepiaToneColorTransformation(String fileName, String filePath) {
    this.fileName = fileName;
    this.filePath = filePath;
  }

  @Override
  public void applyCommand(IModel m) {
    m.sepiaToneColorTransformation(fileName, filePath);
  }
}
