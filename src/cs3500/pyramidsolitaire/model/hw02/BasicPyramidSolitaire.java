package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Represents the model of a simple pyramid solitaire game. It directly manages the data, logic and
 * rules of the game.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  protected Card[][] gameboard;
  protected ArrayList<Card> draw;
  protected int numDraw;
  protected int numRows;
  protected ArrayList<Card> adjustedDeck;
  protected Random rand;
  private int count;

  /**
   * Constructs a {@code BasicPyramidSolitaire} object and takes in a Random for testing purposes.
   * @param rand random number
   */
  public BasicPyramidSolitaire(Random rand) {
    this.numDraw = -1;
    this.numRows = -1;
    this.draw = new ArrayList<>();
    this.rand = rand;
  }


  /**
   * Constructs a {@code BasicPyramidSolitaire} object.
   */
  public BasicPyramidSolitaire() {
    this.numDraw = -1;
    this.numRows = -1;
    this.draw = new ArrayList<>();
    this.rand = new Random();
  }

  @Override
  public List<Card> getDeck() {
    Card[] doc = new Card[52];
    for (int i = 1; i < 5; i++) {
      for (int b = 0; b < 13; b++) {
        if (i == 1) {
          Card card = new Card(false, Value.values()[b], Suit.Club);
          doc[b] = card;
        }
        if (i == 2) {
          Card card = new Card(false, Value.values()[b], Suit.Diamond);
          doc[b + 13] = card;
        }
        if (i == 3) {
          Card card = new Card(false, Value.values()[b], Suit.Heart);
          doc[b + 26] = card;
        }
        if (i == 4) {
          Card card = new Card(false, Value.values()[b], Suit.Spade);
          doc[b + 39] = card;
        }
      }
    }
    ArrayList<Card> arrayListOfCards = new ArrayList<>(Arrays.asList(doc));
    return arrayListOfCards;
  }


  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    this.count = 0;
    //counts the amount of cards that will be handed in the pyramid shape
    // TODO: Currently ends the game one step before it should every draw card needs to be removable
    int cardsInPyramid = this.countCardsInGame(numRows);
    if (deck == null) {
      throw new IllegalStateException("Game has not been started");
    }
    ArrayList<Card> currdeck = new ArrayList<>(deck);
    if (numRows < 2 || numRows > 9 || numDraw < 1 || numDraw + cardsInPyramid > 52) {
      throw new IllegalArgumentException("Invalid Board size");
    }
    if (this.uniqueDeck(currdeck)) {
      throw new IllegalArgumentException("Invalid deck of cards");
    }

    this.numRows = numRows;
    this.numDraw = numDraw;
    this.gameboard = new Card[numRows][numCol()];

    if (shouldShuffle) {
      Collections.shuffle(currdeck, this.rand);
    }

    this.genGridAndDraw(currdeck);

    ArrayList<Card> adjdeck = new ArrayList<>();
    //instantiates adjusted deck
    for (int i = currdeck.size() - 1; i >= count; i--) {
      adjdeck.add(currdeck.get(i));
    }
    adjustedDeck = adjdeck;
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

    else if (!firstcard.isExposed() || !secondcard.isExposed()) {
      throw new IllegalArgumentException("Card not exposed");
    }
    else if (firstcard.valueOf() + secondcard.valueOf() == 13) {
      gameboard[row1][card1] = null;
      gameboard[row2][card2] = null;
      exposedReassign();
    } else {
      throw new IllegalArgumentException("Cards don't sum to 13");
    }

  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    if (row < 0 || card < 0 || row >= numRows || card >= this.numCol()) {
      throw new IllegalArgumentException("Invalid Card Position");
    }

    Card firstcard = gameboard[row][card];

    if (firstcard == null) {
      throw new IllegalArgumentException("King cannot be null");
    }
    else if (!firstcard.isExposed()) {
      throw new IllegalArgumentException("Card not removable");
    }
    else if (firstcard.valueOf() == 13) {
      gameboard[row][card] = null;
      exposedReassign();
    } else {
      throw new IllegalArgumentException("Card is not a King");
    }
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    if (row < 0 || row >= numRows || card < 0
        || card >= this.numCol() || drawIndex < 0 || drawIndex >= draw.size()) {
      throw new IllegalArgumentException("Card Position trying to be accessed no possible");
    }

    Card firstcard = gameboard[row][card];
    Card secondcard = draw.get(drawIndex);

    if (firstcard == null || secondcard == null) {
      throw new IllegalArgumentException("Can't remove on a non existing card");
    }

    if (!firstcard.isExposed() || !secondcard.isExposed()) {
      throw new IllegalArgumentException("Pyramid Card not exposed");
    }

    if (firstcard.valueOf() + secondcard.valueOf() == 13 && adjustedDeck.size() > 0) {
      gameboard[row][card] = null;
      Card temp = this.adjustedDeck.get(adjustedDeck.size() - 1);
      draw.remove(secondcard);
      this.adjustedDeck.remove(temp);
      temp.changeExposed(true);
      draw.add(drawIndex, temp);
      this.exposedReassign();
    } else if (firstcard.valueOf() + secondcard.valueOf() == 13 && adjustedDeck.size() == 0) {
      firstcard = null;
      gameboard[row][card] = firstcard;
      draw.remove(secondcard);
      draw.add(drawIndex, null);
      this.exposedReassign();
    } else {
      throw new IllegalArgumentException("Card values don't sum to 13");
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    } else if (drawIndex >= draw.size() || drawIndex < 0) {
      throw new IllegalArgumentException("Index out of bounce");
    }
    Card firstcard = draw.get(drawIndex);
    if (firstcard == null) {
      throw new IllegalArgumentException("Can't access a spot that doesn't exist");
    }
    else if (!firstcard.isExposed()) {
      throw new IllegalArgumentException("Draw Card not exposed, something went wrong");
    }
    else if (adjustedDeck.size() > 0) {
      Card temp = this.adjustedDeck.get(adjustedDeck.size() - 1);
      draw.remove(firstcard);
      this.adjustedDeck.remove(temp);
      temp.changeExposed(true);
      draw.add(drawIndex, temp);
    } else {
      adjustedDeck.size();
      draw.remove(firstcard);
      draw.add(drawIndex, null);
    }
  }

  @Override
  public int getNumRows() {
    return this.numRows;
  }

  @Override
  public int getNumDraw() {
    return this.numDraw;
  }


  @Override
  public int getRowWidth(int row) {
    return this.basicRowWidth(row);
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }

    if (this.getScore() == 0) {
      return true;
    }

    boolean gameover = true;
    ArrayList<Integer> exposedPCards = new ArrayList<>();
    ArrayList<Integer> leftCards = new ArrayList<>();
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < this.getRowWidth(row); col++) {
        if (gameboard[row][col] != null && gameboard[row][col].isExposed()) {
          exposedPCards.add(gameboard[row][col].valueOf());
        }

      }
    }
    // If draw is 0
    if (adjustedDeck.size() == 0) {
      for (Card c : draw) {
        if (c != null) {
          leftCards.add(c.valueOf());
        }
      }
      for (int i : exposedPCards) {
        if (i == 13) {
          gameover = false;
        }
        int missingVal = 13 - i;
        if (exposedPCards.contains(missingVal) || leftCards.contains(missingVal)) {
          gameover = false;
        }
      }
    }

    if (adjustedDeck.size() > 0) {
      return false;
    }
    if (!this.drawFullyEmpty()) {
      return false;
    }
    return gameover;
  }

  @Override
  public int getScore() throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    int score = 0;
    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numCol(); col++) {
        if (gameboard[row][col] != null) {
          score += gameboard[row][col].valueOf();
        }
      }
    }
    return score;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    if (row < 0 || card < 0 || row >= numRows || card >= numCol()) {
      throw new IllegalArgumentException("Coordinates are invalid");
    }
    return gameboard[row][card];
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    return this.draw;
  }

  /**
   * Gives the total number of cards that are represented in the pyramid shape.
   *
   * @param numRows represents the amount of rows
   * @return the amount Cards in the Pyramid shape
   */
  protected int countCardsInGame(int numRows) {
    int num = numRows;
    int count = 0;
    for (int i = 0; i < numRows; i++) {
      count += num;
      num--;
    }
    return count;
  }

  /**
   * Determines whether a deck has only unique cards and the size of 52.
   *
   * @return whether a deck has only unique cards
   */
  protected boolean uniqueDeck(List<Card> deck) {
    ArrayList<Card> newList = new ArrayList<>();

    for (Card c : deck) {
      if (!(newList.contains(c))) {
        newList.add(c);
      }
    }
    return newList.size() != 52;
  }

  /**
   * Determines after a remove was successful whether new Cards are now exposed and changes their
   * exposedness.
   */
  protected void exposedReassign() {
    basicExposedReassign();
  }

  private void basicExposedReassign() {
    for (int row = 0; row < numRows - 1; row++) {
      for (int col = 0; col < row + 1; col++) {
        if (gameboard[row + 1][col] == null
            && gameboard[row + 1][col + 1] == null && gameboard[row][col] != null) {
          gameboard[row][col].changeExposed(true);
        }
      }
    }
  }

  private boolean drawFullyEmpty() {
    for (Card c : this.draw) {
      if (c != null) {
        return false;
      }
    }
    return true;
  }

  private void genGridAndDraw(List<Card> currdeck) {
    for (int row = 0; row < numRows; row++) {
      // empty cases
      for (int ecol = row; ecol < numRows; ecol++) {
        gameboard[row][ecol] = null;
      }
      // unexposed cases
      if (row < numRows - 1) {
        for (int col = 0; col <= row; col++) {
          Card temp = currdeck.get(count);
          gameboard[row][col] = temp;
          count++;
        }
      }  // exposed cases
      else if (row == numRows - 1) {
        for (int col = 0; col <= row; col++) {
          Card temp = currdeck.get(count);
          temp.changeExposed(true);
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
  }

  protected int numCol() {
    return this.basicNumCol();
  }

  private int basicNumCol() {
    return this.numRows;
  }

  private int basicRowWidth(int row) {
    if (numDraw == -1) {
      throw new IllegalStateException("Game has not been started yet");
    }
    if (row < 0 || row >= numRows) {
      throw new IllegalArgumentException("Row invalid");
    }
    return row + 1;
  }

}
