package commands;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import model.ImageProcessModel;

/**
 * Represents the Mosaic Command, which Mosaics an image.
 */
public class Mosaic implements ImageCommand {
  private final String name; //the name of the image we're mosaicking
  private Posn[] seeds;
  private boolean test;

  /**
   * Public constructor for the Mosaic.
   * @param name the name of the file which is to be Mosaic.
   */
  public Mosaic(String name, int numSeeds) {
    this.name = name;
    this.seeds = new Posn[numSeeds];
  }

  /**
   * test constructor for the Mosaic.
   * @param name the name of the file which is to be Mosaic.
   * @param seeds the given seeds.
   */
  public Mosaic(String name, int numSeeds, Posn[] seeds) {
    this.name = name;
    this.seeds = seeds;
    this.test = true;
  }

  @Override
  public void run(ImageProcessModel model) throws IOException {
    Color[][] reference = model.arrayOfName(this.name);

    if (seeds.length > reference.length * reference[0].length) {
      throw new IllegalArgumentException("The number of seeds is greater than number of pixels");
    }

    Color[][] output = new Color[reference.length][reference[0].length];

    if (!test) {
      getSeeds(reference[0].length, reference.length); //randomizes the seeds
    }

    for (int counter = 0; counter < seeds.length; counter++) {
      output = colorSeed(model, seeds[counter]); //for each pixel, change its color appropriately
    }

    model.addImage(this.name, output);
  }

  @Override
  public String commandType() {
    return "Mosaic";
  }

  /**
   * gets the average color of a seed and sets the cluster to that Color.
   * @param model the given model.
   * @param seed the given seed.
   * @return the new changed image.
   */
  private Color[][] colorSeed(ImageProcessModel model, Posn seed) {
    Color[][] reference = model.arrayOfName(this.name);
    Color average = reference[seed.y][seed.x];
    ArrayList<Posn> cluster = new ArrayList<>(); //a list of all the pixels in the current cluster

    for (int row = 0; row < reference.length; row++) {
      for (int col = 0; col < reference[0].length; col++) {
        if (distanceOf(getClosest(new Posn(col, row)),
                new Posn(col, row)) >= distanceOf(seed, new Posn(col, row))) {
          average = new Color((average.getRed() + reference[row][col].getRed()) / 2,
                  (average.getGreen() + reference[row][col].getGreen()) / 2,
                  (average.getBlue() + reference[row][col].getBlue()) / 2);
          cluster.add(new Posn(col, row));
        }
      }
    }

    return setColorToAverage(cluster, reference, average);
  }


  /**
   * For each pixel in the cluster, set its value to the average.
   * @param cluster a list of pixels that are closest to a certain seed.
   * @param reference the reference image.
   * @param average the average color of the cluster, calculated in colorSeed.
   * @return the reference image, with all the pixels in the cluster set to a new value
   */
  private Color[][] setColorToAverage(ArrayList<Posn> cluster, Color[][] reference, Color average) {
    for (Posn entry : cluster) {
      reference[entry.y][entry.x] = average;
    }
    return reference;
  }

  /**
   * returns the distance between 2 Posns.
   * @param p1 the first posn.
   * @param p2 the second posn.
   * @return the distance between the two posns.
   */
  private double distanceOf(Posn p1, Posn p2) {
    return Math.abs(Math.sqrt((p2.y - p1.y) * (p2.y - p1.y)
            + (p2.x - p1.x) * (p2.x - p1.x)));
  }

  /**
   * gets the closest seed (from this.seeds) to the given posn.
   * @param posn the given posn.
   * @return the closest seed (as a posn) to the given posn.
   */
  private Posn getClosest(Posn posn) {
    Posn closest = new Posn(Integer.MAX_VALUE, Integer.MAX_VALUE); //alternative to making it null

    for (int counter = 0; counter < seeds.length; counter++) {
      if (seeds[counter] != posn && distanceOf(seeds[counter], posn) < distanceOf(closest, posn)) {
        closest = seeds[counter];
      }
    }

    return closest;
  }


  /**
   * Sets all the seeds to random Posns.
   * @param width the width of the image.
   * @param height the height of the image.
   */
  private void getSeeds(int width, int height) {
    for (int counter = 0; counter < seeds.length; counter++) {
      seeds[counter] = new Posn((int) (Math.random() * width),
              (int) (Math.random() * height));
    }
  }



  /**
   * a position for a seed.
   */
  public static class Posn {
    public final int x;
    public final int y;

    /**
     * the cosntructor for a posn.
     * @param x the x position.
     * @param y the y position.
     */
    public Posn(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
}
