import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import controller.ImageProcessController;
import controller.ImageController;
import controller.Features;
import controller.GUIController;
import model.ImageProcessModel;
import model.ImageModel;
import view.ImageProcessGUIView;
import view.ImageProcessView;
import view.ImageView;
import view.SwingFrame;

/**
 * The class that will run the Image Process program.
 */
public class ImageProcess {
  /**
   * Runs the main method with the given array of strings.
   *
   * @param args the arguments to be accepted by the main method.
   * @throws IOException if the program runs into any rendering issues.
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("GUI!");
      ImageProcessModel model = new ImageModel();
      ImageProcessGUIView guiView = new SwingFrame();
      Features gui = new GUIController(model, guiView);
      guiView.addFeature(gui);
      return;
    }
    if (args[0].equals("-text") && args.length == 1) {
      ImageProcessModel model = new ImageModel();
      ImageProcessView view = new ImageView(System.out);
      ImageProcessController controller = new ImageController(model, view,
              new InputStreamReader(System.in), System.out);
      controller.runProgram(model);
      return;
    }
    if (args[0].equals("-file") && args.length == 2) {
      ImageProcessModel model = new ImageModel();
      ImageProcessView view = new ImageView(System.out);
      ImageProcessController controller = new ImageController(model, view,
              new InputStreamReader(new FileInputStream(args[1])), System.out);
      System.out.println(args[1]);
      controller.runProgram(model);
    } else {
      System.out.println("NOT A VALID COMMAND!");
    }
  }
}
