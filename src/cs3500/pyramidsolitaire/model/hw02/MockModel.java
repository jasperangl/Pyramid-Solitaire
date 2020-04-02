package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a mocked model to test the Controller.
 */
public class MockModel implements PyramidSolitaireModel<Card> {
  final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Card> getDeck() {
    return new ArrayList<>(Arrays.asList(new Card(false, Value.KING, Suit.Spade)));
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numRows, int numDraw) {
    log.append("Startgame was called\n");
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    log.append("Accessed remove a two cards\n");
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    log.append("Accessed remove a King\n");
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    log.append("Accessed remove using draw\n");

  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    log.append("Accessed discarding a draw card\n");
  }

  @Override
  public int getNumRows() {
    return -2;
  }

  @Override
  public int getNumDraw() {
    return -2;
  }

  @Override
  public int getRowWidth(int row) {
    return -2;
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    return false;
  }

  @Override
  public int getScore() throws IllegalStateException {
    return 4000;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    return null;
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    return null;
  }
}
