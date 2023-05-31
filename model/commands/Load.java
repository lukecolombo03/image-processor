package cs3500.model.commands;

import cs3500.model.IModel;

/**
 * Function object to grayscale an image using its value.
 * This means each pixel's r, g, and b values are set to the value.
 */
public class Load implements ICommand {
  private final String fileName;
  private final String filePath;

  /**
   * Constructor to create an instance of this function object with both fields.
   * @param fileName which file to perform the action on.
   * @param inProgramFileName what the new name of the file will be.
   */
  public Load(String fileName, String inProgramFileName) {
    this.fileName = fileName;
    this.filePath = inProgramFileName;
  }


  @Override
  public void applyCommand(IModel m) {
    m.load(fileName, filePath);
  }
}

//package cs3500.commands;
//
//        import cs3500.model.IModel;

