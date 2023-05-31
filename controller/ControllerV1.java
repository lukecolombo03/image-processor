package cs3500.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import cs3500.model.commands.Blur;
import cs3500.model.commands.DashFile;
import cs3500.model.commands.Display;
import cs3500.model.commands.FlipHorizontally;
import cs3500.model.commands.FlipVertically;
import cs3500.model.commands.GrayScaleColorTransformation;
import cs3500.model.commands.ICommand;
import cs3500.model.commands.MaskImage;
import cs3500.model.commands.Save;
import cs3500.model.commands.SepiaToneColorTransformation;
import cs3500.model.commands.Sharpen;
import cs3500.model.IModel;
import cs3500.view.IView;
import cs3500.model.commands.Brighten;
import cs3500.model.commands.Darken;
import cs3500.model.commands.GrayScaleBlue;
import cs3500.model.commands.GrayScaleGreen;
import cs3500.model.commands.GrayScaleIntensity;
import cs3500.model.commands.GrayScaleLuma;
import cs3500.model.commands.GrayScaleRed;
import cs3500.model.commands.GrayScaleValue;
import cs3500.model.commands.Load;

//Questions:
// 1. Should we make a new controller implementation?
// 2. Should we move save/load to the view since they handle input/output stuff? (someone on piazza
//    said this)


/**
 * Implementation of the controller. Named V1 in case we have to make a new controller in the
 * future, for whatever reason.
 */
public class ControllerV1 implements IController {
  private final IModel model;
  private IView view;
  private final Readable input;
  private final Appendable output;
  private final Map<String, Function<Scanner, ICommand>> knownCommands = new HashMap<>();
  //not sure if this is right, but I know the controller should have a features field
  private GUIFeatures features;


  /**
   * Constructor that takes in a model, input, and output, none of which can be null.
   *
   * @param model  a model.
   * @param input  a readable input.
   * @param output an appendable output.
   */
  public ControllerV1(IModel model, Readable input, Appendable output) {
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    } else {
      this.model = model;
    }
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    } else {
      this.input = input;
    }
    if (output == null) {
      throw new IllegalArgumentException("output can't be null");
    } else {
      this.output = output;
    }
  }

  /**
   * Constructor that takes in a model, view, input, and output, none of which can be null.
   *
   * @param model  a model.
   * @param view   a view.
   * @param input  a readable input.
   * @param output an appendable output.
   */
  public ControllerV1(IModel model, IView view, Readable input, Appendable output) {
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    } else {
      this.model = model;
    }
    if (view == null) {
      throw new IllegalArgumentException("view can't be null");
    } else {
      this.view = view;
    }
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    } else {
      this.input = input;
    }
    if (output == null) {
      throw new IllegalArgumentException("output can't be null");
    } else {
      this.output = output;
    }
  }


  /**
   * Runs the controller and program.
   */
  public void runProgram() {
    Scanner scanner = new Scanner(input);
    this.addCommands();
    this.welcomeMessage();

    while (scanner.hasNext()) {
      ICommand c;
      String in = scanner.next();
      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        return;
      }
      if (in.equalsIgnoreCase("help")) {
        this.helpMenu();
      }
      Function<Scanner, ICommand> cmd = knownCommands.getOrDefault(in, null);
      if (cmd != null) {
        c = cmd.apply(scanner);
        c.applyCommand(model);
      } else {
        writeMessage("That command was not recognized. Commands are all lowercase. If it is " +
                "multiple words, put hyphens between every word.");
      }
    }
  }

  private void welcomeMessage() {
    String message = "Welcome to the image processing program. Type help to see a list of " +
            "supported commands. At any time, type q or quit to exit the program.\n";
    this.writeMessage(message);
  }

  private void helpMenu() {
    StringBuilder message = new StringBuilder("Commands you can input are:\n");
    for (Map.Entry<String, Function<Scanner, ICommand>> entry : knownCommands.entrySet()) {
      message.append(entry.getKey() + "\n");
    }
    this.writeMessage(message.toString());
  }

  private void writeMessage(String message) throws IllegalStateException {
    try {
      output.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  private void addCommands() {
    knownCommands.put("load", (Scanner s) -> {
      return new Load(s.next(), s.next());
    });
    knownCommands.put("save", (Scanner s) -> {
      return new Save(s.next(), s.next());
    });
    knownCommands.put("display", (Scanner s) -> {
      return new Display(s.next());
    });
    knownCommands.put("brighten", (Scanner s) -> {
      return new Brighten(s.nextInt(), s.next(), s.next());
    });
    knownCommands.put("darken", (Scanner s) -> {
      return new Darken(s.nextInt(), s.next(), s.next());
    });
    knownCommands.put("vertical-flip", (Scanner s) -> {
      return new FlipVertically(s.next(), s.next());
    });
    knownCommands.put("horizontal-flip", (Scanner s) -> {
      return new FlipHorizontally(s.next(), s.next());
    });
    knownCommands.put("blue-component", (Scanner s) -> {
      return new GrayScaleBlue(s.next(), s.next());
    });
    knownCommands.put("green-component", (Scanner s) -> {
      return new GrayScaleGreen(s.next(), s.next());
    });
    knownCommands.put("red-component", (Scanner s) -> {
      return new GrayScaleRed(s.next(), s.next());
    });
    knownCommands.put("intensity-component", (Scanner s) -> {
      return new GrayScaleIntensity(s.next(), s.next());
    });
    knownCommands.put("luma-component", (Scanner s) -> {
      return new GrayScaleLuma(s.next(), s.next());
    });
    knownCommands.put("value-component", (Scanner s) -> {
      return new GrayScaleValue(s.next(), s.next());
    });
    knownCommands.put("blur", (Scanner s) -> {
      return new Blur(s.next(), s.next());
    });
    knownCommands.put("sharpen", (Scanner s) -> {
      return new Sharpen(s.next(), s.next());
    });
    knownCommands.put("grayScale", (Scanner s) -> {
      return new GrayScaleColorTransformation(s.next(), s.next());
    });
    knownCommands.put("sepiaTone", (Scanner s) -> {
      return new SepiaToneColorTransformation(s.next(), s.next());
    });
    knownCommands.put("-file", (Scanner s) -> {
      return new DashFile(s.next());
    });
    knownCommands.put("mask", (Scanner s) -> {
      return new MaskImage(s.next(), s.next(), s.next());
    });
  }
}


