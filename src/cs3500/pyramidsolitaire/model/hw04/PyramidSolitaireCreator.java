package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represents the Creator of a Pyramid Solitaire Game. More specifically allows us to choose between
 * the three version of the game.
 */
public class PyramidSolitaireCreator {

  /**
   * An enumeration representing the three different types of games playable.
   */
  public enum GameType {
    BASIC, RELAXED, TRIPEAKS;
  }

  /**
   * Returns the specific Pyramid Solitaire Model determined by the given game type. Used to let the
   * user decide which game type he wants to play.
   *
   * @param type Enumeration representing one of the three different game types.
   * @return The specific Pyramid Solitaire Model determined by the given game type.
   */
  public static PyramidSolitaireModel create(GameType type) {
    if (type.equals(GameType.BASIC)) {
      return new BasicPyramidSolitaire();
    }
    if (type.equals(GameType.RELAXED)) {
      return new RelaxedPyramidSolitaire();
    } else {
      return new TrypeaksPyramidSolitaire();
    }
  }

}


