import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import cs3500.pyramidsolitaire.model.hw04.TrypeaksPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the class testing all the Tripeaks possibilities.
 */
public class TrypeaksModelTest {

  private ArrayList<Card> validloc;
  private ArrayList<Card> invalidloc1;
  private ArrayList<Card> invalidloc2;
  private ArrayList<Card> invalidloc3;
  private ArrayList<Card> invalidloc4;

  // Remove test
  private Card card;
  private Card card3r;

  private PyramidSolitaireModel<Card> psg;
  private PyramidSolitaireModel<Card> psg3;
  private Card[][] gameboard1;


  @Before
  public void init() {
    card = new Card(false, Value.ACE, Suit.Club);
    card3r = new Card(true, Value.KING, Suit.Diamond);

    psg = new TrypeaksPyramidSolitaire();
    PyramidSolitaireModel<Card> psg2 = new TrypeaksPyramidSolitaire();
    psg3 = new TrypeaksPyramidSolitaire();
    validloc = (ArrayList<Card>) psg.getDeck();
    invalidloc1 = new ArrayList<>();
    invalidloc2 = new ArrayList<>(validloc);
    invalidloc2.remove(2);
    invalidloc3 = new ArrayList<>(validloc);
    invalidloc3.add(card);
    invalidloc4 = new ArrayList<>(validloc);
    invalidloc4.set(5, card);
    Card cardg0 = new Card(false, Value.ACE, Suit.Club);
    Card cardg1 = new Card(false, Value.TWO, Suit.Club);
    Card cardg2 = new Card(false, Value.THREE, Suit.Club);
    Card cardg3 = new Card(true, Value.FOUR, Suit.Club);
    Card cardg4 = new Card(true, Value.FIVE, Suit.Club);
    Card cardg5 = new Card(true, Value.SIX, Suit.Club);
    Card cardg6 = new Card(true, Value.SEVEN, Suit.Club);

    gameboard1 = new Card[2][4];
    gameboard1[0][0] = cardg0;
    gameboard1[0][1] = cardg1;
    gameboard1[0][2] = cardg2;
    gameboard1[0][3] = null;
    gameboard1[1][0] = cardg3;
    gameboard1[1][1] = cardg4;
    gameboard1[1][2] = cardg5;
    gameboard1[1][3] = cardg6;

  }

  @Test
  public void getDeck() {
    assertEquals(104, validloc.size());
    assertEquals(card, validloc.get(0));
    assertEquals(new Card(false, Value.KING, Suit.Club), validloc.get(12));
    assertEquals(new Card(false, Value.KING, Suit.Club), validloc.get(64));
    assertEquals(new Card(false, Value.ACE, Suit.Diamond), validloc.get(13));
  }

  // STARTGAME TESTS

  @Test
  public void startGameShuffled() {
    boolean shuffled = false;
    psg = new TrypeaksPyramidSolitaire(new Random(4));
    psg3 = new TrypeaksPyramidSolitaire();
    psg.startGame(psg.getDeck(), true, 4, 6);
    psg3.startGame(psg3.getDeck(), false, 4, 6);
    // if one element is not at the same index as the basic genList, the list is shuffled
    for (int row = 0; row < psg.getNumRows(); row++) {
      for (int col = 0; col < psg.getRowWidth(row); col++) {
        if (psg.getCardAt(row, col) != null && psg3.getCardAt(row, col) != null) {
          if (!psg.getCardAt(row, col).equals(psg3.getCardAt(row, col))) {
            shuffled = true;
          }
        }
      }
    }
    assertTrue(shuffled);
  }

  @Test
  public void testRestartTheGame() {
    psg.startGame(psg.getDeck(), false, 5, 4);
    psg.remove(4, 1);
    assertNull(psg.getCardAt(4, 1));
    psg.startGame(psg.getDeck(), false, 5, 4);
    assertEquals(card3r, psg.getCardAt(4, 1));
  }

  @Test
  public void startGameGameboard() {
    psg.startGame(validloc, false, 2, 2);
    assertEquals(gameboard1[0][0], validloc.get(0));
    assertEquals(gameboard1[0][1], validloc.get(1));
    assertEquals(gameboard1[0][2], validloc.get(2));
    assertNull(gameboard1[0][3]);
    assertEquals(gameboard1[1][0], validloc.get(3));
    assertEquals(gameboard1[1][1], validloc.get(4));
    assertEquals(gameboard1[1][2], validloc.get(5));
    assertEquals(gameboard1[1][3], validloc.get(6));

    assertTrue(gameboard1[1][1].isExposed());
    assertFalse(gameboard1[0][1].isExposed());
  }

  // ILLEGAL ARGUMENTS OF STARTGAME

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRow() {
    psg.startGame(validloc, false, -4, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRow2() {
    psg.startGame(validloc, false, 0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDraw() {
    psg.startGame(validloc, false, 6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRow3() {
    psg.startGame(validloc, false, 9, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDrawSize() {
    psg.startGame(validloc, false, 8, 17);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidEmptyDeck() {
    psg.startGame(invalidloc1, false, 6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckSmallerThan104() {
    psg.startGame(invalidloc2, false, 6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckBiggerThan104() {
    psg.startGame(invalidloc3, false, 6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckOneValue3Time() {
    psg.startGame(invalidloc4, false, 6, 2);
  }

  // REMOVE 2 PYRAMID CARDS TESTS

  // Necessary to test because my column width is now bigger
  @Test
  public void remove2PyramidC() {
    Card card32 = new Card(true, Value.SIX, Suit.Diamond);
    Card card33 = new Card(true, Value.SEVEN, Suit.Diamond);
    psg3.startGame(validloc, false, 4, 6);
    assertEquals(card32, psg3.getCardAt(3, 2));
    assertEquals(card33, psg3.getCardAt(3, 3));
    psg3.remove(3, 3, 3, 2);
    assertNull(psg3.getCardAt(3, 2));
    assertNull(psg3.getCardAt(3, 3));

  }

  @Test(expected = IllegalStateException.class)
  public void remove2PyramidCGameNotStarted() {
    psg.remove(1, 1, 2, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove2PyramidCNotAccessible() {
    psg.startGame(validloc, false, 4, 4);
    psg.removeUsingDraw(3, 3, 7);
    psg.remove(3, 6, 2, 6);
  }


  @Test(expected = IllegalArgumentException.class)
  public void remove2PyramidCNegative3() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(3, 0, 4, 4);
  }

  // REMOVE 1 PYRAMID CARD TESTS

  @Test
  public void remove1PyramidC() {
    psg3.startGame(validloc, false, 5, 4);
    assertEquals(psg3.getCardAt(4, 1), new Card(true, Value.KING, Suit.Diamond));
    psg3.remove(4, 1);
    assertEquals(psg3.getCardAt(4, 1), null);
  }

  @Test(expected = IllegalStateException.class)
  public void remove1PyramidCNotStarted() {
    psg.remove(4, 1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCRemoveNonExposedKing() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCNoKing() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove4() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCNull() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove5() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(3, -1);
  }

  // Test REMOVE USING DRAW

  @Test
  public void removeUsingDraw() {
    Card card24 = new Card(true, Value.QUEEN, Suit.Club);
    Card draw1 = new Card(true, Value.ACE, Suit.Diamond);
    Card drawnew = new Card(true, Value.SIX, Suit.Diamond);
    psg3.startGame(validloc, false, 3, 6);
    assertEquals(card24, psg3.getCardAt(2, 4));
    assertEquals(draw1, psg3.getDrawCards().get(1));
    psg3.removeUsingDraw(1, 2, 4);
    assertNull(psg3.getCardAt(2, 4));
    assertEquals(drawnew, psg3.getDrawCards().get(1));

  }

  @Test(expected = IllegalStateException.class)
  public void removeWithDrawNotStarted() {
    psg.removeUsingDraw(1, 5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawIllegalInputs() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(5, 5, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawIllegalInputs2() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(-1, 5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawIllegalInputs3() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(1, -1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawNotExposed() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(1, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawNull() {
    psg.startGame(validloc, false, 8, 3);
    psg.discardDraw(6);
    psg.removeUsingDraw(2, 0, 6);
  }

  // TEST REMOVE A DRAW CARD

  @Test
  public void discardDrawNormal() {
    Card currdraw = new Card(true, Value.ACE, Suit.Club);
    Card nextdraw = new Card(true, Value.SIX, Suit.Club);
    psg3.startGame(validloc, false, 6, 6);
    assertEquals(psg3.getDrawCards().get(1), currdraw);
    psg3.discardDraw(1);
    assertEquals(psg3.getDrawCards().get(1), nextdraw);
  }

  @Test
  public void discardDrawStackEmpty() {
    Card currdraw = new Card(true, Value.QUEEN, Suit.Heart);
    psg3.startGame(validloc, false, 8, 16);
    assertEquals(psg3.getDrawCards().get(1), currdraw);
    psg3.discardDraw(1);
    assertNull(psg3.getDrawCards().get(1));
  }

  @Test(expected = IllegalStateException.class)
  public void discardDrawNotStarted() {
    psg.discardDraw(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardDrawIllegalInput() {
    psg.startGame(validloc, false, 5, 4);
    psg.discardDraw(10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardDrawIllegalInput2() {
    psg.startGame(validloc, false, 5, 4);
    psg.discardDraw(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void discardDrawIllegalInput3() {
    psg.startGame(validloc, false, 8, 16);
    psg.discardDraw(6);
    psg.discardDraw(6);
  }

  // TEST GETTERS

  @Test
  public void getNumRows() {
    assertEquals(-1, psg.getNumRows());
    psg.startGame(validloc, false, 5, 4);
    assertEquals(5, psg.getNumRows());
  }

  @Test
  public void getNumDraw() {
    assertEquals(-1, psg.getNumDraw());
    psg.startGame(validloc, false, 5, 3);
    assertEquals(3, psg.getNumDraw());
  }

  @Test
  public void getRowWidth() {
    psg.startGame(validloc, false, 5, 4);
    assertEquals(5, psg.getRowWidth(0));
    assertEquals(9, psg.getRowWidth(4));
  }

  @Test(expected = IllegalStateException.class)
  public void getRowWidthNotStarted() {
    psg.getRowWidth(3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getRowWidthIllegalMove() {
    psg.startGame(validloc, false, 5, 4);
    psg.getRowWidth(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getRowWidthIllegalMove2() {
    psg.startGame(validloc, false, 5, 4);
    psg.getRowWidth(5);
  }

  // TESTS GAME OVER

  @Test
  public void isGameOverTrue() {
    psg.startGame(validloc, false, 2, 8);
    psg.remove(1, 2, 1, 3);
    psg.removeUsingDraw(0, 1, 1);
    psg.removeUsingDraw(1, 1, 0);
    psg.removeUsingDraw(2, 0, 2);
    psg.removeUsingDraw(3, 0, 1);
    psg.removeUsingDraw(4, 0, 0);
    assertTrue(psg.isGameOver());
  }

  @Test
  public void isGameOverBig() {
    PyramidSolitaireTextualView psv;
    psg.startGame(psg.getDeck(), false, 5, 2);
    psv = new PyramidSolitaireTextualView(psg);
    assertFalse(psg.isGameOver());
    psg.discardDraw(1);
    for (int i = 0; i < 69; i++) {
      psg.discardDraw(0);
    }
    psg.discardDraw(1);
    psg.remove(4, 1);
    psg.remove(4, 0, 4, 2);
    psg.remove(4, 7, 4, 8);
    assertFalse(psg.isGameOver());
    psg.remove(4, 3, 3, 7);

    assertTrue(psg.isGameOver());
  }

  @Test
  public void isGameOverFalse() {
    psg.startGame(validloc, false, 2, 8);
    psg.remove(1, 2, 1, 3);
    psg.removeUsingDraw(0, 1, 1);
    psg.removeUsingDraw(1, 1, 0);
    psg.removeUsingDraw(2, 0, 2);
    psg.removeUsingDraw(3, 0, 1);
    assertFalse(psg.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void isGameOverNotStarted() {
    psg.isGameOver();
  }


  @Test
  public void getScore() {
    psg.startGame(validloc, false, 5, 2);
    assertEquals(210, psg.getScore());
    psg.remove(4, 1);
    assertEquals(197, psg.getScore());
  }

  @Test
  public void getScore0() {
    psg.startGame(validloc, false, 2, 8);
    psg.remove(1, 2, 1, 3);
    psg.removeUsingDraw(0, 1, 1);
    psg.removeUsingDraw(1, 1, 0);
    psg.removeUsingDraw(2, 0, 2);
    psg.removeUsingDraw(3, 0, 1);
    psg.removeUsingDraw(4, 0, 0);
    assertEquals(0, psg.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void getScoreNotStarted() {
    psg.getScore();
  }


  @Test
  public void getCardAt() {
    psg.startGame(validloc, false, 2, 2);
    assertEquals(gameboard1[0][0], psg.getCardAt(0, 0));
    assertEquals(gameboard1[1][3], psg.getCardAt(1, 3));
  }

  @Test(expected = IllegalStateException.class)
  public void getCardAtNotStarted() {
    psg.getCardAt(1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtInvallidPosn() {
    psg.startGame(validloc, false, 4, 2);
    psg.getCardAt(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtInvallidPosn2() {
    psg.startGame(validloc, false, 2, 2);
    psg.getCardAt(1, 4);
  }

  @Test(expected = IllegalStateException.class)
  public void getDrawCardsNotStarted() {
    psg.getDrawCards();
  }

  @Test
  public void testRowWidth() {
    psg.startGame(validloc, false, 4, 3);
    assertEquals(8, psg.getRowWidth(3));
    assertEquals(6, psg.getRowWidth(1));
  }

  @Test(expected = IllegalStateException.class)
  public void testrowWidthNotStarted() {
    psg.getRowWidth(2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testrowWidthIllegalInput() {
    psg.startGame(validloc, false, 4, 3);
    psg.getRowWidth(4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testrowWidthIllegalInput2() {
    psg.startGame(validloc, false, 4, 3);
    psg.getRowWidth(-1);
  }


}
