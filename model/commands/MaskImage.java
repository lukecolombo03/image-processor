package cs3500.model.commands;

import cs3500.model.IModel;

public class MaskImage implements ICommand {
  private final String fileName;
  private final String filePath;
  private final String command;

  /**
   * Constructor to create an instance of this function object with both fields.
   * @param fileName which file to perform the action on.
   * @param filePath what the new name of the file will be.
   */
  public MaskImage(String fileName, String filePath, String command) {
    this.fileName = fileName;
    this.filePath = filePath;
    this.command = command;
  }

  @Override
  public void applyCommand(IModel m) {
    m.maskImage(this.fileName, this.filePath, this.command);
  }
}
