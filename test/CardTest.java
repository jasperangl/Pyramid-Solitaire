import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw02.Value;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the Test class for a Card.
 */
public class CardTest {


  private Card c1;
  private Card c2;
  private Card c3;
  private Card c4;
  private Card c5;
  private Card c6;
  private Card c7;
  private Card c1b;
  private Card c1c;
  private Card c1d;
  private BasicPyramidSolitaire bps;

  @Before
  public void init() {
    c1 = new Card(false, Value.ACE, Suit.Club);
    c1b = new Card(true, Value.ACE, Suit.Club); //changed exposed
    c1c = new Card(false, Value.ACE, Suit.Diamond); //changed suit
    c1d = new Card(false, Value.THREE, Suit.Club); //changed value
    c2 = new Card(true, Value.TWO, Suit.Diamond);
    c3 = new Card(false, Value.NINE, Suit.Heart);
    c4 = new Card(true, Value.TEN, Suit.Spade);
    c5 = new Card(false, Value.JACK, Suit.Heart);
    c6 = new Card(true, Value.QUEEN, Suit.Spade);
    c7 = new Card(false, Value.KING, Suit.Heart);
    bps = new BasicPyramidSolitaire();

    ArrayList<Card> deck = (ArrayList<Card>) bps.getDeck();


  }

  @Test
  public void testChangeExposed1() {
    assertFalse(c1.isExposed());
    c1.changeExposed(true);
    assertTrue(c1.isExposed());
  }

  @Test
  public void testChangeExposed2() {
    assertTrue(c2.isExposed());
    c2.changeExposed(false);
    assertFalse(c2.isExposed());
  }

  @Test
  public void testChangeExposed3() {
    assertTrue(c2.isExposed());
    c2.changeExposed(true);
    assertTrue(c2.isExposed());
  }

  @Test
  public void isExposed() {
    assertFalse(c1.isExposed());
    assertTrue(c2.isExposed());

  }

  @Test
  public void valueOf() {
    assertEquals(1, c1.valueOf());
    assertEquals(2, c2.valueOf());
  }

  @Test
  public void setSuit() {
    assertEquals("A♣", c1.toString());
    c1.setSuit(Suit.Diamond);
    assertEquals("A♦", c1.toString());
  }

  @Test
  public void toString1() {
    assertEquals("A♣", c1.toString());
    assertEquals("2♦", c2.toString());
    assertEquals("9♥", c3.toString());
    assertEquals("10♠", c4.toString());
    assertEquals("J♥", c5.toString());
    assertEquals("Q♠", c6.toString());
    assertEquals("K♥", c7.toString());
  }

  @Test
  public void testEquals() {
    assertEquals(false, c1 == null);
    assertEquals(false, c1.equals(new BasicPyramidSolitaire()));
    assertEquals(true, c1.equals(c1));
    assertEquals(false, c1.equals(c2));
    assertEquals(false, c1.equals(bps));
    assertEquals(true, c1.equals(c1b));
    assertEquals(false, c1.equals(c1c));
    assertEquals(false, c1.equals(c1d));
  }

  @Test
  public void testHashCode() {
    assertEquals(c1.hashCode(), c1.hashCode());
    assertEquals(c1.hashCode(), c1b.hashCode());
    assertNotEquals(c1.hashCode(), c1c.hashCode());
    assertNotEquals(c1.hashCode(), c1d.hashCode());
    assertNotEquals(c1.hashCode(), bps.hashCode());
  }

  @Test(expected = IllegalArgumentException.class)
  public void cardValueInvalid() {
    Card cf1 = new Card(false, Value.ACE, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cardSuitInvalid() {
    Card cf3 = new Card(false, null, Suit.Spade);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setSuitInvalid() {
    this.c1.setSuit(null);
  }

}