import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;


/**
 * Represents the Test class for a basic pyramid solitaire model.
 */
public class BasicPyramidSolitaireTest {

  private ArrayList<Card> validloc;
  private ArrayList<Card> invalidloc1;
  private ArrayList<Card> invalidloc2;
  private Card card4;

  // Remove test
  private Card card;
  private Card cardr;
  private Card card2r;
  private Card card3r;
  private Card card4r;
  private Card card6r;
  private Card card8r;

  private Card card3d;
  private Card card7d;

  private PyramidSolitaireModel<Card> psg;
  private PyramidSolitaireModel<Card> psg2;
  private PyramidSolitaireModel<Card> psg3;
  private ArrayList<Card> draw1;
  private ArrayList<Card> draw2;
  private ArrayList<Card> draw3;
  private ArrayList<Card> draw4;
  private Card[][] gameboard1;


  @Before
  public void init() {
    card = new Card(false, Value.ACE, Suit.Club);
    Card card2 = new Card(false, Value.TWO, Suit.Club);
    Card card3 = new Card(false, Value.THREE, Suit.Club);
    this.card4 = new Card(false, Value.FOUR, Suit.Club);
    Card card5 = new Card(false, Value.FIVE, Suit.Club);
    Card card6 = new Card(true, Value.SIX, Suit.Club);
    Card card7 = new Card(true, Value.SEVEN, Suit.Club);
    Card card8 = new Card(true, Value.EIGHT, Suit.Spade);
    Card cardK = new Card(true, Value.KING, Suit.Spade);
    cardr = new Card(true, Value.JACK, Suit.Club);
    Card card1r = new Card(true, Value.THREE, Suit.Spade);
    card2r = new Card(true, Value.TWO, Suit.Diamond);
    card3r = new Card(true, Value.KING, Suit.Club);
    card4r = new Card(true, Value.THREE, Suit.Diamond);
    Card card5r = new Card(true, Value.TEN, Suit.Heart);
    card6r = new Card(true, Value.TEN, Suit.Diamond);
    Card card7r = new Card(true, Value.SEVEN, Suit.Heart);
    card8r = new Card(true, Value.SIX, Suit.Diamond);

    // Draw Cards
    Card card1d = new Card(true, Value.SEVEN, Suit.Spade);
    Card card2d = new Card(true, Value.EIGHT, Suit.Spade);
    card3d = new Card(true, Value.NINE, Suit.Spade);
    Card card4d = new Card(true, Value.TEN, Suit.Spade);
    Card card5d = new Card(true, Value.JACK, Suit.Spade);
    Card card6d = new Card(true, Value.QUEEN, Suit.Spade);
    card7d = new Card(true, Value.KING, Suit.Spade);

    psg = new BasicPyramidSolitaire();
    psg2 = new BasicPyramidSolitaire();
    psg3 = new BasicPyramidSolitaire();
    validloc = (ArrayList<Card>) psg.getDeck();
    invalidloc1 = new ArrayList<>();
    invalidloc2 = new ArrayList<>(Arrays.asList(card3));
    ArrayList<Card> invalidloc3 = (ArrayList<Card>) psg.getDeck();
    draw1 = new ArrayList<>(Arrays.asList(this.card4, card5, card6));
    draw2 = new ArrayList<>(Arrays.asList(this.card4, card5, card6, card7));
    draw3 = new ArrayList<>(Arrays.asList(card1d, card2d, card3d, card4d,
        card5d, card6d, card7d));
    draw4 = new ArrayList<>(Arrays.asList(card1d, card2d, card3d, card4d,
        card5d, card6d, null));
    gameboard1 = new Card[3][3];
    Card[][] gameboard2 = new Card[8][8];
    gameboard1[0][0] = card;
    gameboard1[0][1] = null;
    gameboard1[0][2] = null;
    gameboard1[1][0] = card2;
    gameboard1[1][1] = card3;
    gameboard1[1][2] = null;
    gameboard1[2][0] = this.card4;
    gameboard1[2][1] = card5;
    gameboard1[2][2] = card6;

  }

  @Test
  public void getDeck() {
    assertEquals(52, validloc.size());
    assertEquals(card, validloc.get(0));
    assertEquals(new Card(false, Value.KING, Suit.Club), validloc.get(12));
    assertEquals(new Card(false, Value.ACE, Suit.Diamond), validloc.get(13));
    assertEquals(new Card(false, Value.EIGHT, Suit.Diamond), validloc.get(20));
    assertEquals(new Card(false, Value.KING, Suit.Spade), validloc.get(51));
  }

  // STARTGAME TESTS

  @Test
  public void startGameShuffled() {
    boolean shuffled = false;
    psg = new BasicPyramidSolitaire(new Random(4));
    psg2 = new BasicPyramidSolitaire();
    psg.startGame(validloc, true, 4, 6);
    psg2.startGame(validloc, false, 4, 6);
    // if one element is not at the same index as the basic genList, the list is shuffled
    for (int row = 0; row < psg.getNumRows(); row++) {
      for (int col = 0; col < psg.getRowWidth(row); col++) {
        if (!psg.getCardAt(row, col).equals(psg2.getCardAt(row, col))) {
          shuffled = true;
        }
      }
    }
    assertTrue(shuffled);
  }

  @Test
  public void testRestartTheGame() {
    psg.startGame(psg.getDeck(), false, 5, 4);
    psg.remove(4, 2);
    assertEquals(null, psg.getCardAt(4, 2));
    psg.startGame(psg.getDeck(), false, 5, 4);
    assertEquals(card3r, psg.getCardAt(4, 2));
  }

  //TODO: Is draw allowed to be 0?
  @Test
  public void startGameDraw() {
    psg.startGame(validloc, false, 2, 3);
    assertEquals(draw1, psg.getDrawCards());
  }

  @Test
  public void startGameDraw2() {
    psg2.startGame(validloc, false, 2, 4);
    assertEquals(draw2, psg2.getDrawCards());
  }

  @Test
  public void startGameGameboard1() {
    psg.startGame(validloc, false, 3, 3);
    List<Card> deck = psg.getDeck();
    assertEquals(gameboard1[0][0], deck.get(0));
    assertEquals(gameboard1[1][0], deck.get(1));
    assertEquals(gameboard1[1][1], deck.get(2));
    assertEquals(gameboard1[2][0], deck.get(3));
    assertEquals(gameboard1[2][1], deck.get(4));
    assertEquals(gameboard1[2][2], deck.get(5));
    assertEquals(null, gameboard1[0][2]);
  }

  // ILLEGAL ARGUMENTS OF STARTGAME

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRow() {
    psg.startGame(validloc, false, -4, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidRow2() {
    psg.startGame(validloc, false, 1, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDraw() {
    psg.startGame(validloc, false, 6, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDrawAndRow3() {
    psg.startGame(validloc, false, 10, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDrawSize() {
    psg.startGame(validloc, false, 9, 15);
  }


  @Test(expected = IllegalArgumentException.class)
  public void startGameInvalidDeck() {
    psg.startGame(invalidloc1, false, 6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckSmallerThan52() {
    psg.startGame(invalidloc2, false, 6, 2);
  }

  @Test(expected = IllegalStateException.class)
  public void startGameNullDeck() {
    psg.startGame(null, false, 6, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckBiggerThan52() {
    validloc.remove(47);
    validloc.add(47, card4);
    psg.startGame(validloc, false, 6, 2);
  }

  // REMOVE 2 PYRAMID CARDS TESTS

  @Test
  public void remove2PyramidC() {
    psg3.startGame(validloc, false, 5, 4);
    assertEquals(cardr, psg3.getCardAt(4, 0));
    assertEquals(card2r, psg3.getCardAt(4, 4));
    assertEquals(null, psg3.getCardAt(1, 3));
    psg3.remove(4, 0, 4, 4);
    assertEquals(null, psg3.getCardAt(4, 0));
    assertEquals(null, psg3.getCardAt(4, 4));
    assertEquals(null, psg3.getCardAt(1, 3));

  }

  @Test(expected = IllegalStateException.class)
  public void remove2PyramidCNegative1() {
    psg.remove(1, 1, 2, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void remove2PyramidCGameNotStarted() {
    psg.remove(1, 1, 2, 1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void remove2PyramidCNegative2() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(4, 2, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove2PyramidCNull() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(2, 4, 0, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove2PyramidCNotExposed() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(3, 3, 3, 2);
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
    assertEquals(psg3.getCardAt(4, 0), cardr);
    assertEquals(psg3.getCardAt(4, 2), card3r);
    assertEquals(psg3.getCardAt(1, 3), null);
    psg3.remove(4, 2);
    assertEquals(psg3.getCardAt(4, 0), cardr);
    assertEquals(psg3.getCardAt(4, 2), null);
    assertEquals(psg3.getCardAt(1, 3), null);
  }

  @Test(expected = IllegalStateException.class)
  public void remove1PyramidCNotStarted() {
    psg.remove(4, 2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove2() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove3() {
    psg.startGame(validloc, false, 5, 4);
    psg.remove(4, 3);
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
  public void removeWithDraw() {
    Card cardreplace = new Card(false, Value.QUEEN, Suit.Diamond);
    psg3.startGame(validloc, false, 6, 3);
    assertEquals(psg3.getCardAt(5, 0), card4r);
    assertEquals(psg3.getDrawCards().get(1), card6r);
    psg3.removeUsingDraw(1, 5, 0);
    assertEquals(psg3.getCardAt(5, 0), null);
    assertEquals(psg3.getDrawCards().get(1), cardreplace);
  }

  @Test(expected = IllegalStateException.class)
  public void removeWithDrawNotStarted() {
    psg.removeUsingDraw(1, 5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawIllegalInputs() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(3, 5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawIllegalInputs2() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(-1, 5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawIllegalInputs3() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(1, 5, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawIllegalInputs4() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(1, 4, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawNotExposed() {
    psg.startGame(validloc, false, 6, 3);
    psg.removeUsingDraw(1, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeWithDrawNull() {
    psg.startGame(validloc, false, 9, 7);
    psg.discardDraw(6);
    psg.removeUsingDraw(6, 8, 6);
  }

  // TEST REMOVE A DRAW CARD

  @Test
  public void discardDraw() {
    psg3.startGame(validloc, false, 4, 8);
    assertEquals(psg3.getDrawCards().get(2), card3r);
    psg3.discardDraw(2);
    assertEquals(psg3.getDrawCards().get(2), card8r);
  }

  @Test
  public void discardDraw2() {
    psg3.startGame(validloc, false, 9, 7);
    assertEquals(psg3.getDrawCards().get(2), card3d);
    psg3.discardDraw(2);
    assertEquals(null, psg3.getDrawCards().get(2));
  }

  @Test
  public void discardDraw3() {
    psg3.startGame(validloc, false, 9, 7);
    assertEquals(psg3.getDrawCards().get(6), card7d);
    psg3.discardDraw(6);
    assertEquals(null, psg3.getDrawCards().get(6));
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
    psg.startGame(validloc, false, 9, 7);
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
    assertEquals(1, psg.getRowWidth(0));
    assertEquals(5, psg.getRowWidth(4));
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
    psg.startGame(validloc, false, 3, 4);
    psg.removeUsingDraw(0, 2, 2);
    psg.removeUsingDraw(1, 2, 1);
    psg.removeUsingDraw(2, 2, 0);
    psg.removeUsingDraw(3, 1, 1);
    psg.removeUsingDraw(0, 1, 0);
    psg.removeUsingDraw(1, 0, 0);
    assertTrue(psg.isGameOver());
  }

  @Test
  public void isGameOverBig() {
    psg.startGame(psg.getDeck(), false, 9, 4);
    assertEquals(false, psg.isGameOver());
    psg.removeUsingDraw(1, 8, 7);
    psg.removeUsingDraw(1, 8, 4);
    psg.removeUsingDraw(1, 8, 3);
    psg.removeUsingDraw(0, 7, 3);
    psg.discardDraw(1);
    psg.discardDraw(3);
    psg.remove(8, 2);
    psg.removeUsingDraw(2, 8, 6);
    assertTrue(psg.isGameOver());
  }

  @Test
  public void isGameOverFalse() {
    psg.startGame(validloc, false, 3, 4);
    psg.removeUsingDraw(0, 2, 2);
    psg.removeUsingDraw(1, 2, 1);
    psg.removeUsingDraw(2, 2, 0);
    psg.removeUsingDraw(3, 1, 1);
    psg.removeUsingDraw(0, 1, 0);
    assertFalse(psg.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void isGameOverNotStarted() {
    psg.isGameOver();
  }


  @Test
  public void getScore() {
    psg.startGame(validloc, false, 5, 2);
    assertEquals(94, psg.getScore());
    psg.remove(4, 2);
    assertEquals(81, psg.getScore());
  }

  @Test
  public void getScore0() {
    psg.startGame(validloc, false, 2, 1);
    assertEquals(6, psg.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void getScoreNotStarted() {
    psg.getScore();
  }


  @Test
  public void getCardAt() {
    psg.startGame(validloc, false, 3, 2);
    assertEquals(gameboard1[0][0], psg.getCardAt(0, 0));
    assertEquals(gameboard1[2][2], psg.getCardAt(2, 2));
  }

  @Test(expected = IllegalStateException.class)
  public void getCardAtNotStarted() {
    psg.getCardAt(2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtInvallidPosn() {
    psg.startGame(validloc, false, 4, 2);
    psg.getCardAt(4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardAtInvallidPosn2() {
    psg.startGame(validloc, false, 4, 2);
    psg.getCardAt(-1, 4);
  }


  @Test
  public void getDrawCards() {
    psg.startGame(validloc, false, 2, 3);
    assertEquals(draw1, psg.getDrawCards());
  }

  @Test
  public void getDrawCards2() {
    psg.startGame(validloc, false, 2, 4);
    assertEquals(draw2, psg.getDrawCards());
  }

  @Test
  public void getDrawCards3() {
    psg.startGame(validloc, false, 9, 7);
    assertEquals(draw3, psg.getDrawCards());
    psg.discardDraw(6);
    assertEquals(draw4, psg.getDrawCards());
  }

  @Test(expected = IllegalStateException.class)
  public void getDrawCardsNotStarted() {
    psg.getDrawCards();
  }

  @Test
  public void testRowWidth() {
    psg.startGame(validloc, false, 4, 3);
    assertEquals(4, psg.getRowWidth(3));
    assertEquals(1, psg.getRowWidth(0));
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