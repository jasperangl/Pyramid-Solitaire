import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents my testing class for all relaxed pyramid cases.
 */
public class RelaxedPyramidTest {

  private ArrayList<Card> validloc;

  //rm2 relaxed
  private Card card50;
  private Card card61;
  private Card card60;
  private Card card70;
  private Card card71;

  private Card cardr;
  private Card card2r;

  private PyramidSolitaireModel<Card> rsg;

  @Before
  public void init() {
    // Remove test
    card50 = new Card(false, Value.THREE, Suit.Diamond);
    card60 = new Card(false, Value.NINE, Suit.Diamond);
    card61 = new Card(false, Value.TEN, Suit.Diamond);

    card70 = new Card(true, Value.THREE, Suit.Heart);
    card71 = new Card(true, Value.FOUR, Suit.Heart);

    cardr = new Card(true, Value.JACK, Suit.Club);
    card2r = new Card(true, Value.TWO, Suit.Diamond);

    rsg = new RelaxedPyramidSolitaire();
    validloc = (ArrayList<Card>) rsg.getDeck();

  }


  // REMOVE 2 PYRAMID CARDS TESTS

  @Test
  public void remove2PyramidCNormal() {
    rsg.startGame(validloc, false, 5, 4);
    assertEquals(cardr, rsg.getCardAt(4, 0));
    assertEquals(card2r, rsg.getCardAt(4, 4));
    assertEquals(null, rsg.getCardAt(1, 3));
    rsg.remove(4, 0, 4, 4);
    assertEquals(null, rsg.getCardAt(4, 0));
    assertEquals(null, rsg.getCardAt(4, 4));
    assertEquals(null, rsg.getCardAt(1, 3));
  }

  @Test
  public void remove2Relaxed() {
    rsg.startGame(validloc, false, 7, 3);
    assertEquals(card50, rsg.getCardAt(5, 0));
    assertEquals(card60, rsg.getCardAt(6, 0));
    assertEquals(card61, rsg.getCardAt(6, 1));
    rsg.removeUsingDraw(1, 6, 0);
    assertEquals(card50, rsg.getCardAt(5, 0));
    assertNull(rsg.getCardAt(6, 0));
    assertEquals(card61, rsg.getCardAt(6, 1));
    rsg.remove(6, 1, 5 , 0);
    assertNull(rsg.getCardAt(5, 0));
    assertNull(rsg.getCardAt(6, 0));
    assertNull(rsg.getCardAt(6, 1));
  }

  @Test
  public void remove2RelaxedBig() {
    rsg.startGame(validloc, false, 8, 3);
    assertEquals(card50, rsg.getCardAt(5, 0));
    assertEquals(card60, rsg.getCardAt(6, 0));
    assertEquals(card61, rsg.getCardAt(6, 1));
    assertEquals(card70, rsg.getCardAt(7, 0));
    assertEquals(card71, rsg.getCardAt(7, 1));
    rsg.remove(7,0,7,7);
    // relaxed remove
    rsg.remove(6,0,7,1);
    rsg.remove(7,2,7,5);
    rsg.remove(5,0,6,1);
    assertEquals(null, rsg.getCardAt(5, 0));
    assertEquals(null, rsg.getCardAt(6, 0));
    assertEquals(null, rsg.getCardAt(6, 1));
    assertEquals(null, rsg.getCardAt(7, 0));
    assertEquals(null, rsg.getCardAt(7, 1));
  }


  @Test(expected = IllegalStateException.class)
  public void remove2PyramidCNegative1() {
    rsg.remove(1, 1, 2, 1);
  }

  @Test(expected = IllegalStateException.class)
  public void remove2PyramidCGameNotStarted() {
    rsg.remove(1, 1, 2, 1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void remove2NegativeNormalIllagal() {
    rsg.startGame(validloc, false, 5, 4);
    rsg.remove(4, 2, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove2RelaxedIllegal() {
    rsg.startGame(validloc, false, 8, 3);
    rsg.remove(7,0,7,7);
    rsg.remove(6,0,7,1);
    rsg.remove(5,0,6,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove2RelaxedIllegalRemove() {
    // Tests trying to remove pair with different card at different position
    rsg.startGame(validloc, false, 9, 6);
    rsg.remove(8,0,8,4);
    rsg.remove(8,1,8,2);
    rsg.remove(8,2);
    rsg.removeUsingDraw(0, 8, 8);
    rsg.removeUsingDraw(1, 8, 7);
    rsg.removeUsingDraw(3, 7,0);
    rsg.remove(6,0, 7, 1);
    rsg.remove(5, 0, 7, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove2RelaxedIllegal2() {
    rsg.startGame(validloc, false, 8, 3);
    rsg.remove(6,0,7,1);
  }

  // REMOVE 1 PYRAMID CARD TESTS

  @Test(expected = IllegalStateException.class)
  public void remove1PyramidCNotStarted() {
    rsg.remove(4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove2() {
    rsg.startGame(validloc, false, 6, 4);
    rsg.remove(4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove3() {
    rsg.startGame(validloc, false, 5, 4);
    rsg.remove(4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove4() {
    rsg.startGame(validloc, false, 5, 4);
    rsg.remove(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCNull() {
    rsg.startGame(validloc, false, 5, 4);
    rsg.remove(2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void remove1PyramidCIllegalMove5() {
    rsg.startGame(validloc, false, 5, 4);
    rsg.remove(3, -1);
  }

}
