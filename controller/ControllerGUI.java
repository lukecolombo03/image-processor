package cs3500.controller;

import cs3500.model.IModel;
import cs3500.view.IView;

/**
 * I think "asynchronous" controller means that it really doesn't do much. It has a object that
 * implements GUIFeatures and this handles all the code.
 */
public class ControllerGUI {
  private IView view;
  //this has the code for what to do in case of each event—this is what makes it asynchronous
  private GUIFeatures features;

  /**
   * the constructor for controllerGUI.
   * @param model the given model.
   * @param view the given view.
   */
  public ControllerGUI(IModel model, IView view) {
    this.view = view;
    this.features = new HandleFeatures(model, view);
  }

  /**
   * Provides the view with all the features. See addFeatures documentation.
   */
  public void setView() {
    this.view.addFeatures(this.features);
  }
}
