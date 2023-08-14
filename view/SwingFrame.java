package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileNameExtensionFilter;

import commands.Brighten;
import commands.Grayscale;
import commands.HorizontalFlip;
import commands.ImageCommand;
import commands.Load;
import commands.Mosaic;
import commands.Save;
import commands.VerticalFlip;
import commands.Component;
import controller.Features;

/**
 * This class represents a GUI view that is implemented using Java
 * Swing.
 */
public class SwingFrame extends JFrame implements ImageProcessGUIView {


  // RIGHT DISPLAY: for graph visual
  private Histograms frequencyGraph;


  private ActionListener listenLoad;
  private ActionListener listenEdit;
  private ActionListener listenSave;

  private ItemListener graphView;
  private ActionListener editExecute;

  private boolean intensityView;
  private boolean redView;
  private boolean greenView;
  private boolean blueView;

  private ImageWindow imageWindow;
  private ImagePanel panel;
  private Features controller;

  private Color[][] histogram;

  private ImageCommand editCommand;

  /**
   * Public constructor for the swing frame class.
   * Create helper methods to assist the constructor.
   */
  public SwingFrame() {
    super();
    JPanel leftDisplay;
    JPanel histogramButtons;
    JPanel imageButtons;

    setTitle("Image Process");
    setSize(900, 500);
    setLocation(300, 300);

    this.panel = new ImagePanel();
    imageWindow = new ImageWindow(this.panel);

    this.redView = false;
    this.greenView = false;
    this.blueView = false;
    this.intensityView = false;

    // initialize buttonlisteners
    try {
      this.initButtonListeners();
    }
    catch (IOException e) {
      System.out.println("Unsuccessful initialization.");
    }


    leftDisplay = new JPanel(new FlowLayout(FlowLayout.LEFT));
    leftDisplay.setPreferredSize(new Dimension(200, 650));
    leftDisplay.setBorder(BorderFactory.createTitledBorder("User Input"));
    histogramButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    histogramButtons.add(new JLabel("Histogram"));
    histogramButtons.setPreferredSize(new Dimension(200, 170));


    JPanel checkBoxPanel = new JPanel();
    checkBoxPanel.setPreferredSize(new Dimension(180, 130));
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("View options"));
    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

    JCheckBox[] pixelComponent = new JCheckBox[4];
    for (int component = 0; component < pixelComponent.length; component++) {
      pixelComponent[component] = new JCheckBox(this.intToGraphType(component));
      pixelComponent[component].setSelected(false);
      pixelComponent[component].setActionCommand(this.intToGraphType(component));
      pixelComponent[component].addItemListener(this.graphView);
      checkBoxPanel.add(pixelComponent[component]);
    }
    pixelComponent[0].doClick();
    histogramButtons.add(checkBoxPanel);

    imageButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
    imageButtons.setPreferredSize(new Dimension(200, 600));
    imageButtons.add(new JLabel("Image"));


    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Edit options"));
    radioPanel.setPreferredSize(new Dimension(180, 300));
    radioPanel.setLayout(new GridLayout(13, 0));

    JRadioButton[] radioButtons = new JRadioButton[14];

    ButtonGroup buttonGroup = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(this.intToCommandType(i));
      radioButtons[i].setActionCommand(this.intToCommandType(i));
      radioButtons[i].addActionListener(listenEdit);
      buttonGroup.add(radioButtons[i]);
      radioPanel.add(radioButtons[i]);
    }

    JButton editButton = new JButton("Edit Image");
    JButton loadButton = new JButton("Load Image");
    JButton saveButton = new JButton("Save Image");

    editButton.addActionListener(editExecute);
    loadButton.addActionListener(listenLoad);
    saveButton.addActionListener(listenSave);

    imageButtons.add(radioPanel);
    imageButtons.add(editButton);
    imageButtons.add(loadButton);
    imageButtons.add(saveButton);


    leftDisplay.add(histogramButtons, BorderLayout.SOUTH);
    leftDisplay.add(imageButtons, BorderLayout.SOUTH);
    JScrollPane leftScroll = new JScrollPane(leftDisplay);
    this.add(leftScroll);

    // right side panel for display

    this.frequencyGraph = new Histograms();
    this.frequencyGraph.setLayout(new BoxLayout(frequencyGraph, BoxLayout.Y_AXIS));
    this.frequencyGraph.setPreferredSize(new Dimension(680, 1000));
    this.frequencyGraph.setBorder(BorderFactory.createTitledBorder("Graph"));
    JScrollPane graphScroll = new JScrollPane(frequencyGraph);
    this.add(graphScroll, BorderLayout.EAST);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  /**
   * Set up button listeners.
   * Create other classes that implements actionlistener.
   */
  private void initButtonListeners() throws IOException {
    listenLoad = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final JFileChooser fchooser = new JFileChooser(".");
        String absolutePath = "";
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg",
                "jpeg", "png", "ppm", "bmp");
        fchooser.setFileFilter(filter);
        int retValue = fchooser.showOpenDialog(SwingFrame.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          absolutePath = f.getAbsolutePath();
          System.out.println("File selected at: " + absolutePath);
        }
        try {
          ImageCommand load = new Load("currentImage", absolutePath);
          controller.completeCommand(load);
          imageWindow.setVisible(true);
          imageWindow.pack();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
        updateHistogram();
        SwingFrame.this.refresh();
      }
    };

    graphView = new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        String viewOption = ((JCheckBox) e.getItemSelectable()).getActionCommand();
        System.out.println("OOO: " + viewOption);
        switch (viewOption) {
          case "Red":
            if (e.getStateChange() == ItemEvent.SELECTED) {
              redView = true;
              System.out.println(viewOption + " " + redView + " view was selected.");
            } else {
              redView = false;
              System.out.println(viewOption + " " + redView + " view was deselected.");
            }
            break;
          case "Green":
            if (e.getStateChange() == ItemEvent.SELECTED) {
              greenView = true;
              System.out.println(viewOption + " " + greenView + " view was selected.");
            } else {
              greenView = false;
              System.out.println(viewOption + " " + greenView + " view was deselected.");
            }
            break;
          case "Blue":
            if (e.getStateChange() == ItemEvent.SELECTED) {
              blueView = true;
              System.out.println(viewOption + " " + blueView + " view was selected.");
            } else {
              blueView = false;
              System.out.println(viewOption + " " + blueView + " view was deselected.");
            }
            break;
          case "Intensity":
            if (e.getStateChange() == ItemEvent.SELECTED) {
              intensityView = true;
              System.out.println(viewOption + " " + intensityView + " view was selected.");
            } else {
              intensityView = false;
              System.out.println(viewOption + " " + intensityView + " view was deselected.");
            }
            break;
          default:
            break;
        }
        if (histogram != null) {
          updateHistogram();
        }
        refresh();
      }
    };
    listenEdit = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent a) {
        System.out.println("XXX: " + a.getActionCommand());
        switch (a.getActionCommand()) {
          case "brighten":
            editCommand = new Brighten("currentImage", 10);
            break;
          case "darken":
            editCommand = new Brighten("currentImage", -10);
            break;
          case "horizontal-flip":
            editCommand = new HorizontalFlip("currentImage");
            break;
          case "vertical-flip":
            editCommand = new VerticalFlip("currentImage");
            break;
          case "red-component":
            editCommand = new Component("currentImage","currentImage", "red");
            break;
          case "green-component":
            editCommand = new Component("currentImage","currentImage",
                    "green");
            break;
          case "blue-component":
            editCommand = new Component("currentImage","currentImage",
                    "blue");
            break;
          case "value":     //value, intensity, luma, blur, sharpen, sepia
            editCommand = new Component("currentImage","currentImage",
                    "value");
            break;
          case "intensity":
            editCommand = new Component("currentImage","currentImage",
                    "intensity");
            break;
          case "luma":
            editCommand = new Component("currentImage","currentImage",
                    "luma");
            break;
          case "blur":
            editCommand = new Grayscale("currentImage","currentImage",
                    "blur");
            break;
          case "sharpen":
            editCommand = new Grayscale("currentImage","currentImage",
                    "sharpen");
            break;
          case "Mosaic":
            editCommand = new Mosaic("currentImage", 1000);
            break;
          default:
            editCommand = new Grayscale("currentImage","currentImage",
                    "sepia");
            break;
        }
      }
    };

    editExecute = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.completeCommand(editCommand);
        System.out.println("Image edited.");
        frequencyGraph.setColorArray(histogram);
        SwingFrame.this.refresh();
      }
    };

    listenSave = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent a) {
        final JFileChooser fchooser = new JFileChooser(".");
        String savePath = "";
        int retValue = fchooser.showSaveDialog(SwingFrame.this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          savePath = f.getAbsolutePath();
          System.out.println("File save at: " + savePath);
        }
        ImageCommand save = new Save("currentImage", savePath);
        controller.completeCommand(save);
        System.out.println("Image saved.");
        SwingFrame.this.refresh();
      }
    };
  }

  /**
   * Helper to choose string based on integer.
   * @param choose the number associated with graph viewing type
   * @return the component of the graph to be viewed
   */
  private String intToGraphType(int choose) {
    switch (choose) {
      case 0:
        return "Intensity";
      case 1:
        return "Red";
      case 2:
        return "Green";
      default:
        return "Blue";
    }
  }

  /**
   * Helper to return the name of the command chosen based on integer.
   * @param choose the number associated with the command
   * @return the name of the type of image command
   */
  private String intToCommandType(int choose) {
    switch (choose) {
      case 0:
        return "brighten";
      case 1:
        return "darken";
      case 2:
        return "horizontal-flip";
      case 3:
        return "vertical-flip";
      case 4:
        return "red-component";
      case 5:
        return "green-component";
      case 6:
        return "blue-component";
      case 7:
        return "value";
      case 8:
        return "intensity";
      case 9:
        return "luma";
      case 10:
        return "blur";
      case 11:
        return "sharpen";
      case 12:
        return "sepia";
      default:
        return "Mosaic";
    }
  }

  /**
   * Method to refresh the panel.
   */
  public void refresh() {
    System.out.println("Refreshed JFrame.");
    this.imageWindow.repaint();
    if (frequencyGraph != null && frequencyGraph.containsImageReference()) {
      this.frequencyGraph.analysis(this.redView, this.greenView, this.blueView, this.intensityView);
      this.frequencyGraph.repaint();
    }
    this.repaint();
  }

  @Override
  public void addFeature(Features controller) {
    this.controller = controller;
  }

  @Override
  public void updateImageWindow(BufferedImage buffered) {
    this.imageWindow.addImage(buffered);
    System.out.println(buffered.getWidth());
    this.panel.setPreferredSize(new Dimension(buffered.getWidth(), buffered.getHeight()));
    this.refresh();
    System.out.println("update picture");
  }

  /**
   * Sets up the histogram with the appropriate 2d array of Colors.
   * @param image the 2d array image to be assigned to the histogram
   */
  public void setupHistogram(Color[][] image) {
    this.histogram = image;
  }

  /**
   * Updates the current histogram graph displayed by updating its color
   * content and looking over what component of the histogram to view.
   */
  private void updateHistogram() {
    this.frequencyGraph.setColorArray(this.histogram);
    this.frequencyGraph.analysis(this.redView, this.greenView, this.blueView, this.intensityView);
  }
}