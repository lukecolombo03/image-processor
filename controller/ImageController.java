package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import commands.Brighten;
import commands.Component;
import commands.HorizontalFlip;
import commands.ImageCommand;
import commands.Mosaic;
import model.ImageProcessModel;
import commands.Load;
import commands.Save;
import commands.VerticalFlip;
import view.ImageProcessView;

/**
 * The controller for the Image Process program.
 */
public class ImageController implements ImageProcessController {
  private final ImageProcessModel model;
  private final ImageProcessView view;
  private Readable readable;
  private final Map<String, Function<Scanner, ImageCommand>> commands;

  /**
   * Public constructor for the image controller.
   * @param model the image process model
   * @param view the image process view
   */
  public ImageController(ImageProcessModel model, ImageProcessView view, Readable readable,
                         Appendable appendable) {
    if (model == null || view == null || readable == null || appendable == null) {
      throw new IllegalArgumentException("model, view, readable, or appendable cannot be null");
    }
    this.model = model;
    this.view = view;
    this.readable = readable;
    commands = new HashMap<>();
    commands.put("load", s -> {
      try {
        return new Load(s.next(), s.next());
      } catch (IOException e) {
        //e.printStackTrace();
        return null;
      }
    });
    commands.put("save", s -> {
      return new Save(s.next(), s.next());
    });
    commands.put("horizontal-flip", s -> {
      return new HorizontalFlip(s.next());
    });
    commands.put("vertical-flip", s -> {
      return new VerticalFlip(s.next());
    });
    commands.put("brighten", s -> {
      return new Brighten(s.next(), s.nextInt());
    });
    commands.put("component", s -> {
      return new Component(s.next(), s.next(), s.next());
    });
    commands.put("Mosaic", s -> {
      return new Mosaic(s.next(), 500);
    });
  }

  @Override
  public void runProgram(ImageProcessModel model) throws IOException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;

    while (!quit && sc.hasNext()) {
      try {
        ImageCommand c;
        String inst = sc.next();
        if (inst.equalsIgnoreCase("q") || inst.equalsIgnoreCase("quit")) {
          return;
        }
        Function<Scanner, ImageCommand> cmd =
                commands.getOrDefault(inst, null);
        if (cmd == null) {
          view.renderMessage("Wrong command! Please enter again." + System.lineSeparator());
        } else {
          c = cmd.apply(sc);
          c.run(this.model);
          view.renderMessage(inst + " completed." + System.lineSeparator());
        }
      }
      catch (NullPointerException | IOException e) {
        view.renderMessage("Enter a correct command!" + System.lineSeparator());
      }
    }
  }
}
