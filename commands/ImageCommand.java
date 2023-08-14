package commands;

import java.io.IOException;

import model.ImageProcessModel;

/**
 * This represents the commands.ImageCommand interface which will outline the run method.
 */
public interface ImageCommand {

  /**
   * Executes the command onto a given image.
   * @param model the model that is being modified by this command.
   * @throws IOException when
   */
  void run(ImageProcessModel model) throws IOException;

  /**
   * Determines which type of command this is.
   */
  String commandType();
}
