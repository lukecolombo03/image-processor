package view;

import java.io.IOException;

/**
 * Represents the Image View class which implements ImageProcessView interface.
 */
public class ImageView implements ImageProcessView {
  private Appendable appendable;

  /**
   * Public constructor for the ImageView class.
   * @param appendable the Appendable object where output is sent
   */
  public ImageView(Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("model or appendable cannot be null");
    }
    this.appendable = appendable;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    appendable.append(message);
  }
}
