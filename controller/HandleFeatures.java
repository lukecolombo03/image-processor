package cs3500.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;

import cs3500.model.IModel;
import cs3500.model.commands.Blur;
import cs3500.model.commands.FlipHorizontally;
import cs3500.model.commands.FlipVertically;
import cs3500.model.commands.GrayScaleBlue;
import cs3500.model.commands.GrayScaleColorTransformation;
import cs3500.model.commands.GrayScaleGreen;
import cs3500.model.commands.GrayScaleIntensity;
import cs3500.model.commands.GrayScaleLuma;
import cs3500.model.commands.GrayScaleRed;
import cs3500.model.commands.GrayScaleValue;
import cs3500.model.commands.SepiaToneColorTransformation;
import cs3500.model.commands.Sharpen;
import cs3500.view.IView;

/**
 * This class has all the code for every "feature". I think it's sort of a replacement for the
 * command pattern; all the commands are now here.
 */
public class HandleFeatures implements GUIFeatures {
  private IModel model;
  private IView view;

  public HandleFeatures(IModel model, IView view) {
    this.model = model;
    this.view = view;
  }


  @Override
  public void save() {
    final JFileChooser fileChooser = new JFileChooser();
    int returnVal = fileChooser.showSaveDialog((Component) this.view);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      try {
        model.save("image", f.getAbsolutePath() + ".jpg");
        this.view.renderMessage("Image saved");
      } catch (Exception e) {
        this.view.renderMessage("Error saving file");
      }
    }
    this.view.refresh();
  }


  @Override
  public void load() {
    final JFileChooser fileChooser = new JFileChooser();
    int returnVal = fileChooser.showOpenDialog((Component) this.view);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      //this loads it into the model
      try {
        model.load(f.getAbsolutePath(), "image");
      } catch (Exception e) {
        this.view.renderMessage("Error loading image");
      }
      //this displays it in the view
      Image img = model.getImages().get("image");
      this.view.displayImage(img);
    }
    this.view.refresh();
  }

  @Override
  public void showImage() {
    //
  }

  @Override
  public void scroll(int vertical, int horizontal) {
    //
  }

  @Override
  public void updateHistogram(BufferedImage image) {
    //
  }

  @Override
  public void exitProgram() {
    JOptionPane.showMessageDialog((Component) this.view, "Are you sure you want to quit?",
            "Warning", JOptionPane.WARNING_MESSAGE);
    System.exit(0);
  }

  private void errorMessage(String operationName) {
    this.view.renderMessage("Error with operation " + operationName);
  }

  @Override
  public void blur() {
    try {
      new Blur("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("blur");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void flipHorizontally() {
    try {
      new FlipHorizontally("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("horizontal flip");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void flipVertically() {
    try {
      new FlipVertically("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("vertical flip");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void grayScaleBlue() {
    try {
      new GrayScaleBlue("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("grayscale blue");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void grayScaleColorTransformation() {
    try {
      new GrayScaleColorTransformation("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("grayscale color transformation");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void grayScaleGreen() {
    try {
      new GrayScaleGreen("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("grayscale green");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void grayScaleIntensity() {
    try {
      new GrayScaleIntensity("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("grayscale intensity");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void grayScaleLuma() {
    try {
      new GrayScaleLuma("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("grayscale luma");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void grayScaleRed() {
    try {
      new GrayScaleRed("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("grayscale red");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void grayScaleValue() {
    try {
      new GrayScaleValue("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("grayscale value");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void sepiaToneColorTransformation() {
    try {
      new SepiaToneColorTransformation("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("sepia");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }

  @Override
  public void sharpen() {
    try {
      new Sharpen("image", "image").applyCommand(this.model);
    } catch (Exception e) {
      errorMessage("sharpen");
    }
    Image img = model.getImages().get("image");
    this.view.displayImage(img);
  }
}
