package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * a command object for executing scripts.
 */
public class DashFile implements ICommand {
  String fileName;

  /**
   * a command object for executing scripts.
   * @param fileName the name of the .txt script.
   */
  public DashFile(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void applyCommand(IModel m) {
    m.dashFile(fileName);
  }
}
