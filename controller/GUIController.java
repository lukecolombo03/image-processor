package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import commands.ImageCommand;
import model.ImageProcessModel;
import view.ImageProcessGUIView;

/**
 * An asynchronous GUIController that will appropriately communicate between
 * its model and view to run the ImageProcess program graphically.
 */
public class GUIController implements Features {
  private ImageProcessModel model;
  private ImageProcessGUIView view;

  /**
   * The public constructor for the GUIController.
   * @param model the ImageProcessModel to be assigned to the GUIController
   * @param view the ImageProcessGUIView to be assigned to the GUIController
   */
  public GUIController(ImageProcessModel model, ImageProcessGUIView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void completeCommand(ImageCommand command) {
    switch (command.commandType()) {
      case "load":
        try {
          this.model.apply("currentImage", command);
          BufferedImage image = this.model.imageContents();
          this.view.updateImageWindow(image);
          System.out.println("Controller executed.");
          this.sendArrayToView("currentImage");
        }
        catch (IOException e) {
          System.out.println("Model could not load.");
        }
        break;
      case "save":
        try {
          this.model.apply("currentImage", command);
          System.out.println("Controller executed.");
        }
        catch (IOException | IllegalArgumentException er) {
          System.out.println("Cannot save.");
        }
        break;
      default:
        try {
          this.model.apply("currentImage", command);
          BufferedImage image = this.model.imageContents();
          this.view.updateImageWindow(image);
          System.out.println("Controller executed.");
          this.sendArrayToView("currentImage");
        }
        catch (IOException e) {
          System.out.println("Model could not save.");
        }
    }
  }

  /**
   * Sends the image contents in a 2d array of Colors to the view component.
   * @param name the name of the contents of an image to be sent to the view
   */
  private void sendArrayToView(String name) {
    this.view.setupHistogram(this.model.arrayOfName(name));
  }

}
