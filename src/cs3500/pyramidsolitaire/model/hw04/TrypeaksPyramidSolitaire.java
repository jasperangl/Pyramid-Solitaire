package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Represents an instance of a Pyramid Solitaire game. Instead of a normal Pyramid the Game is
 * represented as three overlaying Pyramid Peaks.
 */
public class TrypeaksPyramidSolitaire extends BasicPyramidSolitaire {

  /**
   * Constructs a Trypeaks Pyramid Solitaire Model.
   */
  public TrypeaksPyramidSolitaire() {
    super();
  }

  /**
   * Constructs a {@code BasicPyramidSolitaire} object and takes in a Random for testing purposes.
   *
   * @param rand random number
   */
  public TrypeaksPyramidSolitaire(Random rand) {
    this.numDraw = -1;
    this.numRows = -1;
    this.draw = new ArrayList<>();
    this.rand = rand;
  }


  @Override
  public List<Card> getDeck() {
    ArrayList<Card> normalDeck = new ArrayList<>(super.getDeck());
    normalDeck.addAll(new ArrayList<>(super.getDeck()));
    return normalDeck;
  }

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    //counts the amount of cards that will be handed in the pyramid shape
    this.numRows = numRows;
    this.numDraw = numDraw;

    if (deck == null) {
      throw new IllegalStateException("Game has not been started");
    }

    ArrayList<Card> currdeck = new ArrayList<>(deck);
    // TODO: Currently ends the game one step before it should every draw card needs to be removable

    int cardsInPyramid = this.countCardsInGame(numRows);
    if (numRows < 1 || numRows > 8 || numDraw < 1 || numDraw + cardsInPyramid > 104) {
      throw new IllegalArgumentException("Invalid Board size");
    }
    if (!this.uniqueDeck(currdeck) || currdeck.size() != 104) {
      throw new IllegalArgumentException("Invalid deck of cards");
    }

    this.gameboard = new Card[numRows][this.numCol()];

    if (shouldShuffle) {
      Collections.shuffle(currdeck, rand);
    }

    int count = 0;
    int ceiling = (numRows / 2);

    // Pyramid TriPeak

    if (ceiling == 1) {
      for (int col = 0; col < this.getRowWidth(0); col++) {
        Card temp = currdeck.get(count);
        gameboard[0][col] = temp;
        count++;
      }
    }

    if (ceiling == 2) {
      for (int row = 0; row < ceiling; row++) {
        if (row == 0) {
          for (int col = 0; col < this.getRowWidth(row); col = col + 2) {
            Card temp = currdeck.get(count);
            gameboard[row][col] = temp;
            count++;
          }
        }
        if (row == 1) {
          for (int col = 0; col < this.getRowWidth(row); col++) {

            Card temp = currdeck.get(count);
            gameboard[row][col] = temp;
            count++;

          }
        }
      }
    }

    if (ceiling == 3) {
      for (int row = 0; row < ceiling; row++) {
        if (row == 0) {
          for (int col = 0; col < this.getRowWidth(row); col = col + 3) {
            Card temp = currdeck.get(count);
            gameboard[row][col] = temp;
            count++;
          }
        }
        if (row == 1) {
          for (int col = 0; col < this.getRowWidth(row); col++) {
            if (col == 2 || col == 5) {
              gameboard[row][col] = null;
            } else {
              Card temp = currdeck.get(count);
              gameboard[row][col] = temp;
              count++;
            }
          }
        }
        if (row == 2) {
          for (int col = 0; col < this.getRowWidth(row); col++) {
            Card temp = currdeck.get(count);
            gameboard[row][col] = temp;
            count++;
          }
        }
      }
    }

    if (ceiling == 4) {
      for (int row = 0; row < ceiling; row++) {
        if (row == 0) {
          for (int col = 0; col < this.getRowWidth(row); col = col + 4) {
            Card temp = currdeck.get(count);
            gameboard[row][col] = temp;
            count++;
          }
        }
        if (row == 1) {
          for (int col = 0; col < this.getRowWidth(row); col++) {
            if (col == 2 || col == 3 || col == 6 || col == 7) {
              gameboard[row][col] = null;
            } else {
              Card temp = currdeck.get(count);
              gameboard[row][col] = temp;
              count++;
            }
          }
        }
        if (row == 2) {
          for (int col = 0; col < this.getRowWidth(row); col++) {
            if (col == 3 || col == 7) {
              gameboard[row][col] = null;
            } else {
              Card temp = currdeck.get(count);
              gameboard[row][col] = temp;
              count++;
            }
          }
        }
        if (row == 3) {
          for (int col = 0; col < this.getRowWidth(row); col++) {
            Card temp = currdeck.get(count);
            gameboard[row][col] = temp;
            count++;
          }
        }
      }
    }

    // Pyramid Base
    for (int row = ceiling; row < numRows; row++) {
      for (int col = 0; col < this.getRowWidth(row); col++) {
        if (row == numRows - 1) {
          Card temp = currdeck.get(count);
          temp.changeExposed(true);
          gameboard[row][col] = temp;
          count++;
        } else {
          Card temp = currdeck.get(count);
          gameboard[row][col] = temp;
          count++;
        }
      }
    }
    this.draw.clear();
    for (int ndraw = 0; ndraw < numDraw; ndraw++) {
      Card temp = currdeck.get(count);
      temp.changeExposed(true);
      this.draw.add(temp);
      count++;
    }

    ArrayList<Card> adjdeck = new ArrayList<>();
    //instantiates adjusted deck
    for (int i = currdeck.size() - 1; i >= count; i--) {
      adjdeck.add(currdeck.get(i));
    }
    adjustedDeck = adjdeck;
  }

  @Override
  public Card getCardAt(int row, int col) {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    if (row < 0 || col < 0 || row >= numRows || col >= numRows * 2) {
      throw new IllegalArgumentException("Coordinates are invalid");
    }
    return gameboard[row][col];
  }

  @Override
  public int getRowWidth(int row) {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    if (row < 0 || row >= numRows) {
      throw new IllegalArgumentException("Row invalid");
    }
    if (numRows % 2 == 0) {
      return row + 1 + super.getNumRows();
    } else {
      return row + super.getNumRows();
    }
  }

  @Override
  protected int numCol() {
    int numCol = 0;
    if (numRows % 2 == 0) {
      numCol = numRows * 2;
    } else {
      numCol = numRows * 2 - 1;
    }
    return numCol;
  }


  @Override
  protected int countCardsInGame(int numRows) {
    int cnt = 0;
    // Pyramid TriPeak
    for (int i = 0; i < 3; i++) {
      for (int row = 0; row < numRows / 2; row++) {
        for (int col = 0; col <= row; col++) {
          cnt++;
        }
      }
    }
    // Pyramid Base
    for (int row = numRows / 2; row < numRows; row++) {
      for (int col = 0; col < this.getRowWidth(row); col++) {
        cnt++;
      }
    }
    return cnt;
  }

  @Override
  protected boolean uniqueDeck(List<Card> deck) {
    for (Card c : deck) {
      if (!cardOnlyTwice(deck, c)) {
        return false;
      }
    }
    return deck.size() == 104;
  }

  @Override
  protected void exposedReassign() {
    for (int row = 0; row < numRows - 1; row++) {
      for (int col = 0; col < getRowWidth(row); col++) {
        if (gameboard[row + 1][col] == null
            && gameboard[row + 1][col + 1] == null && gameboard[row][col] != null) {
          gameboard[row][col].changeExposed(true);
        }
      }
    }
  }

  /**
   * Determines whether the given Card appears exactly twice in the given deck.
   *
   * @param deck A deck of Cards
   * @param card A Card for Pyramid Solitaire
   * @return true if the given Card appears exactly twice in the given deck.
   */
  private boolean cardOnlyTwice(List<Card> deck, Card card) {
    int count = 0;
    for (Card c : deck) {
      if (c.equals(card)) {
        count++;
      }
    }
    return count == 2;
  }

}
