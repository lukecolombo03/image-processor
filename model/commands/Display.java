package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * Function object to display a color image.
 */
public class Display implements ICommand {
  private final String fileName;

  /**
   * Constructor to create an instance of this function object.
   * @param fileName which file to display.
   */
  public Display(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void applyCommand(IModel m) {
    m.display(fileName);
  }
}
