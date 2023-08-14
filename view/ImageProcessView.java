package view;

import java.io.IOException;

/**
 * Represents the interface for viewing component of the Image Process program.
 */
public interface ImageProcessView {

  /**
   * Displays the given message to the appendable.
   * @param message the string to be appended
   */
  void renderMessage(String message) throws IOException;
}
