package controller;

import java.io.IOException;

import model.ImageProcessModel;

/**
 * The controller interface for the image process program.
 */
public interface ImageProcessController {

  /**
   * Runs the program and accordingly sends information to the model.
   * @param model the model to be modified by the controller
   */
  void runProgram(ImageProcessModel model) throws IOException;
}
