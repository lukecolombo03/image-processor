package cs3500.controller;

/**
 * represents all of the controllers that take inputs and run programs.
 */
public interface IController {

  /**
   * Takes user input and runs the program. Continues waiting for user input until "quit" or "q" is
   * pressed. To see a list of available commands, type "help".
   */
  void runProgram();
}
