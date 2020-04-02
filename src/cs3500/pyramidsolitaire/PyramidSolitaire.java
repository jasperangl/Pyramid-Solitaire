package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import java.io.InputStreamReader;

/**
 * Represents the class that makes a Pyramid Solitaire game playable containing the main method.
 */
public final class PyramidSolitaire {

  /**
   * Makes a Pyramid Solitaire Game interactive with the user.
   * @param args Represents the arguments from the user.
   */
  public static void main(String[] args) {
    PyramidSolitaireModel model;
    int numRows = 7;
    int numDraw = 3;
    try {
      switch (args[0]) {
        case "basic":
          model = PyramidSolitaireCreator.create(GameType.BASIC);
          break;
        case "relaxed":
          model = PyramidSolitaireCreator.create(GameType.RELAXED);
          break;
        case "tripeaks":
          model = PyramidSolitaireCreator.create(GameType.TRIPEAKS);
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + args[1]);
      }
    } catch (Exception e) {
      throw new IllegalStateException("Invalid inputs");
    }
    try {
      numRows = Integer.parseInt(args[1]);
      numDraw = Integer.parseInt(args[2]);
    } catch (Exception ignored) {
    }
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(new InputStreamReader(System.in), System.out);
    controller.playGame(model, model.getDeck(), true, numRows, numDraw);
  }
}
// TODO:
// 1. Has to be 3 or 1 args
// 2. Try catch on the whole thing
