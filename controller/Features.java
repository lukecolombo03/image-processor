package controller;

import commands.ImageCommand;

/**
 * The interface to represent features of a panel.
 */
public interface Features {
  /**
   * Completes the command by taking in a row and col coordinate.
   * @param command the ImageCommand to be executed
   */
  void completeCommand(ImageCommand command);
}
