package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * The class holding the image window displaying current image.
 */
public class ImageWindow extends JFrame implements ImageFrame {
  private ImagePanel panel;

  /**
   * The public constructor for creating an ImageWindow.
   * @param panel the ImagePanel to be assigned to this ImageWindow
   */
  public ImageWindow(ImagePanel panel) {
    super("Image Window");
    this.panel = panel;
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    this.setPreferredSize(new Dimension(800, 500));
    this.panel.setPreferredSize(new Dimension(800, 500));
    this.setLocation(800, 50);
    this.add(new JScrollPane(panel));
  }

  @Override
  public void addImage(BufferedImage image) {
    this.panel.setImage(image);
  }
}
