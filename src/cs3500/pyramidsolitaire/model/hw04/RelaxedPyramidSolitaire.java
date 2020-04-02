package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;

/**
 * Represents a more relaxed version of Pyramid Solitaire where a pair of Cards is removable if one
 * card is above the other and they sum to 13.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire {

  public RelaxedPyramidSolitaire() {
    super();
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }

    if (row1 < 0 || card1 < 0 || row1 >= numRows || card1 >= numCol()
        || row2 < 0 || card2 < 0 || row2 >= numRows || card2 >= numCol()) {
      throw new IllegalArgumentException("Invalid Card Position");
    }
    Card firstcard = gameboard[row1][card1];
    Card secondcard = gameboard[row2][card2];

    if (firstcard == null || secondcard == null) {
      throw new IllegalArgumentException("Can't remove a null");
    }

    else if (Math.abs(row1 - row2) == 1
        || (Math.abs(card1 - card2) == 1 && Math.abs(row1 - row2) == 1)
        && (firstcard.isExposed() || secondcard.isExposed())) {
      if (firstcard.valueOf() + secondcard.valueOf() == 13) {
        gameboard[row1][card1] = null;
        gameboard[row2][card2] = null;
        exposedReassign();
      }
    } else if (!firstcard.isExposed() || !secondcard.isExposed()) {
      throw new IllegalArgumentException("Card not exposed");
    } else if (firstcard.valueOf() + secondcard.valueOf() == 13) {
      gameboard[row1][card1] = null;
      gameboard[row2][card2] = null;
      exposedReassign();
    } else {
      throw new IllegalArgumentException("Cards don't sum to 13");
    }
  }
}


