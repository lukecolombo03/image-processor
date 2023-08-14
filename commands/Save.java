package commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ImageProcessModel;

/**
 * Represents the commands.Load command which implements the commands.ImageCommand.
 */
public class Save implements ImageCommand {
  private final String name;
  private final String path;

  /**
   * Public constructor for the load command.
   * @param name the name to be added
   * @param path the image to be added
   */
  public Save(String name, String path) {
    this.name = name;
    this.path = path;
  }

  @Override
  public void run(ImageProcessModel model) throws IOException {
    switch (new ImageUtil().extOfFile(this.path)) {
      case "ppm":
        this.savePPM(model);
        break;
      case "jpeg":
      case "jpg":
      case "png":
      case "bmp":
        this.saveOtherTypes(model);
        break;
      default:
        throw new IOException("unsupported extension.");
    }
  }

  @Override
  public String commandType() {
    return "save";
  }

  /**
   * Saves given PPM as a 2d color array.
   * @param model the model to read the 2d color from
   * @throws IOException when there is no valid file to be created or written to
   */
  public void savePPM(ImageProcessModel model) throws IOException {
    StringBuilder builder = model.convertNameToStringBuilder(this.name);
    StringBuilder builderOut = new StringBuilder();

    File file2 = new File(this.path);
    file2.createNewFile();

    Scanner sc;
    sc = new Scanner(builder.toString());

    Color[][] image = model.arrayOfName(this.name);

    builderOut.append("P3" + System.lineSeparator());
    builderOut.append(image[0].length + System.lineSeparator());
    builderOut.append(image.length + System.lineSeparator());
    builderOut.append(255 + System.lineSeparator());

    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      builderOut.append(s + System.lineSeparator());
    }
    byte[] b = builderOut.toString().getBytes();
    FileOutputStream output = new FileOutputStream(file2, true);
    output.write(b);
    output.close();
  }

  /**
   * Saves given JPEG, PNG, BMP as a 2d color array.
   * @param model the model to read the 2d color from
   * @throws IOException when there is no valid file to be created or written to
   */
  public void saveOtherTypes(ImageProcessModel model) throws IOException {
    Color[][] image = model.arrayOfName(this.name);
    BufferedImage bufferedImage = new ImageUtil().arrayToBufferedImage(image);

    File file2 = new File(this.path);
    file2.createNewFile();
    ImageIO.write(bufferedImage, new ImageUtil().extOfFile(this.path), file2);
  }
}