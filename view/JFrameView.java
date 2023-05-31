package cs3500.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

import cs3500.Histogram;
import cs3500.controller.GUIFeatures;
import cs3500.model.IModel;
import cs3500.model.ImplModel;

/**
 * the view for the program, contains all images and things that are shown.
 */
public class JFrameView extends JFrame implements IView {
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton exitButton;
  private JButton blur;
  private JButton flipHorizontally;
  private JButton flipVertically;
  private JButton grayScaleBlue;
  private JButton grayScaleColorTransformation;
  private JButton grayScaleGreen;
  private JButton grayScaleIntensity;
  private JButton grayScaleLuma;
  private JButton grayScaleRed;
  private JButton grayScaleValue;
  private JButton sepiaToneColorTransformation;
  private JButton sharpen;
  private JLabel imageLabel;
  private final JLabel defaultFile = new JLabel();
  //this can be used to display error messages
  private JScrollPane scrollPane;


  /**
   * Main constructor to set up the view.
   * @param caption the name of the window.
   */
  public JFrameView(String caption) {
    super(caption);
    IModel model = new ImplModel();
    setSize(1000, 750);
    setDefaultCloseOperation(JFrameView.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(1000, 500));
    this.setLayout(new BorderLayout());

    //west panel
    JPanel westPanel = new JPanel();
    westPanel.setLayout(new BorderLayout());
    westPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.add(westPanel, BorderLayout.WEST);

    //save and load panel
    JPanel saveLoadPanel = new JPanel();
    saveLoadPanel.setLayout(new BorderLayout());
    fileOpenButton = new JButton("Open a file");
    saveLoadPanel.add(fileOpenButton, BorderLayout.NORTH);
    fileSaveButton = new JButton("Save current file");
    saveLoadPanel.add(fileSaveButton, BorderLayout.CENTER);
    exitButton = new JButton("Quit");
    saveLoadPanel.add(exitButton, BorderLayout.SOUTH);
    westPanel.add(saveLoadPanel, BorderLayout.NORTH);

    //options panel
    JPanel optionsPanel = new JPanel();
    optionsPanel.setLayout(new GridLayout(10, 0, 0, 5));
    flipHorizontally = new JButton("Horizontal flip");
    optionsPanel.add(flipHorizontally);
    flipVertically = new JButton("Vertical flip");
    optionsPanel.add(flipVertically);
    grayScaleRed = new JButton("Grayscale red component");
    optionsPanel.add(grayScaleRed);
    grayScaleBlue = new JButton("Grayscale blue component");
    optionsPanel.add(grayScaleBlue);
    grayScaleGreen = new JButton("Grayscale green component");
    optionsPanel.add(grayScaleGreen);
    grayScaleIntensity = new JButton("Grayscale intensity component");
    optionsPanel.add(grayScaleIntensity);
    grayScaleValue = new JButton("Grayscale value component");
    optionsPanel.add(grayScaleValue);
    grayScaleLuma = new JButton("Grayscale luma component");
    optionsPanel.add(grayScaleLuma);
    sepiaToneColorTransformation = new JButton("Sepia filter");
    optionsPanel.add(sepiaToneColorTransformation);
    grayScaleColorTransformation = new JButton("Grayscale filter");
    optionsPanel.add(grayScaleColorTransformation);
    blur = new JButton("Blur");
    optionsPanel.add(blur);
    sharpen = new JButton("Sharpen");
    optionsPanel.add(sharpen);
    westPanel.add(optionsPanel, BorderLayout.SOUTH);

    //image panel
    imageLabel = new JLabel();
    this.add(imageLabel, BorderLayout.CENTER);

    this.pack();
    this.setVisible(true);
  }

  //maybe try to think of a way to avoid adding a method to IView
  //because that means that any other implementation of IView would need
  @Override
  public void displayImage(Image image) {
    this.imageLabel.setIcon(new ImageIcon(image));

    JPanel redHistogram = new Histogram((BufferedImage) image, 0);
    JPanel greenHistogram = new Histogram((BufferedImage) image, 1);
    JPanel blueHistogram = new Histogram((BufferedImage) image, 2);
    JPanel intensityHistogram = new Histogram((BufferedImage) image, 3);
    JPanel eastPanel = new JPanel();
    eastPanel.setLayout(new GridLayout(0, 4, 10, 1));
    eastPanel.add(redHistogram, 0);
    eastPanel.add(greenHistogram, 1);
    eastPanel.add(blueHistogram, 2);
    eastPanel.add(intensityHistogram, 3);
    eastPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
    eastPanel.setVisible(true);
    this.add(eastPanel, BorderLayout.SOUTH);
  }

  private void setImageLabel() {
    imageLabel = new JLabel();
    imageLabel.setBounds(0, 50, this.getWidth(), this.getHeight());
    imageLabel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imageLabel.setLayout(new GridLayout(1, 0, 10, 10));
    this.add(imageLabel);
  }

  private void setScrollPane() {
    JScrollPane scrollPane1 = new JScrollPane(imageLabel);
    scrollPane1.getVerticalScrollBar().setUnitIncrement(15);
    scrollPane1.getHorizontalScrollBar().setUnitIncrement(15);
    scrollPane1.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
    this.add(scrollPane1, BorderLayout.WEST);
  }

  @Override
  public void refresh() {
    this.pack();
    this.repaint();
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }


  @Override
  public void addFeatures(GUIFeatures features) {
    //which button we want to activate        this has to be a method in GUIFeatures
    this.exitButton.addActionListener(evt -> features.exitProgram());

    this.fileOpenButton.addActionListener(evt -> features.load());
    this.fileSaveButton.addActionListener(evt -> features.save());
    this.blur.addActionListener(evt -> features.blur());
    this.flipHorizontally.addActionListener(evt -> features.flipHorizontally());
    this.flipVertically.addActionListener(evt -> features.flipVertically());
    this.grayScaleBlue.addActionListener(evt -> features.grayScaleBlue());
    this.grayScaleColorTransformation.addActionListener(
            evt -> features.grayScaleColorTransformation());
    this.grayScaleGreen.addActionListener(evt -> features.grayScaleGreen());
    this.grayScaleIntensity.addActionListener(evt -> features.grayScaleIntensity());
    this.grayScaleLuma.addActionListener(evt -> features.grayScaleLuma());
    this.grayScaleRed.addActionListener(evt -> features.grayScaleRed());
    this.grayScaleValue.addActionListener(evt -> features.grayScaleValue());
    this.sepiaToneColorTransformation.addActionListener(
            evt -> features.sepiaToneColorTransformation());
    this.sharpen.addActionListener(evt -> features.sharpen());

    //if we wanted there to be a hotkey or shortcut we would use this
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
      }

      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
      }
    });
  }
}
