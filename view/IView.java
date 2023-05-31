package cs3500.view;

import java.awt.*;

import cs3500.controller.GUIFeatures;

/**
 * Represents all the functionality that a view will have.
 * The view holds information about which controls are visible, while the controller holds
 * information about how to respond to certain controls.
 */
public interface IView {

  /**
   * Refresh the screen. This is called when the something on the
   * screen is updated and therefore it must be redrawn.
   */
  void refresh();

  /**
   * Display the image that the user is currently working on.
   * @param image the given image.
   */
  void displayImage(Image image);

  /**
   * Display a message in a suitable area of the GUI.
   * @param message the message to be displayed
   */
  void renderMessage(String message);

  /**
   * Add all features to the view. This means that the view (more specifically, each button .
   * in the view).
   * is now ready and waiting for specific user inputs, and will then send those to the controller's
   * GUIFeature object, which will then manipulate either the model or the view.
   * @param features a GUIFeature object
   */
  void addFeatures(GUIFeatures features);
}
