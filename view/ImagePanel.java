package view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * The ImagePanel class which extends JPanel and will paint the image it stores.
 */
public class ImagePanel extends JPanel {
  private BufferedImage image;

  /**
   * The default public constructor for ImagePanel.
   */
  public ImagePanel() {
    super();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(this.image, 0, 0, null);
    this.repaint();
  }

  /**
   * Sets the panel's image to the provided BufferedImage.
   * @param image the BufferedImage to be assigned to this panel
   */
  public void setImage(BufferedImage image) {
    this.image = image;
  }
}