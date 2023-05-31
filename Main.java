package cs3500;

import cs3500.controller.ControllerGUI;
import cs3500.model.IModel;
import cs3500.model.ImplModel;
import cs3500.view.IView;
import cs3500.view.JFrameView;

/**
 * A main class, to act as the entry point for this program.
 */
public final class Main {

  /**
   * A main function, to act as the entry point for this program.
   */
  public static void main(String... args) {
    IModel model = new ImplModel();
    IView view = new JFrameView("Luke & Nick's Image Processing Program");
    ControllerGUI controller = new ControllerGUI(model, view);
    controller.setView();
  }
}
