import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.TrypeaksPyramidSolitaire;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;


/**
 * Represents the Test class for a visualisation of a basic pyramid solitaire.
 */

public class PyramidSolitaireTextualViewTest {

  private BasicPyramidSolitaire psb;
  private PyramidSolitaireTextualView psv;
  private TrypeaksPyramidSolitaire tpg;

  @Before
  public void init() {
    psb = new BasicPyramidSolitaire();
    tpg = new TrypeaksPyramidSolitaire();
    psv = new PyramidSolitaireTextualView(psb);

  }

  // Basic Pyramid Tests

  @Test
  public void viewGameNotStarted() {
    psv = new PyramidSolitaireTextualView(psb);
    assertEquals("", psv.toString());
  }

  @Test
  public void viewToStringBigPyramid() {
    psb.startGame(psb.getDeck(), false, 5, 4);
    psb.remove(4, 2);
    psb.startGame(psb.getDeck(), false, 5, 4);
    psv = new PyramidSolitaireTextualView(psb);
    assertEquals("        A♣\n"
        + "      2♣  3♣\n"
        + "    4♣  5♣  6♣\n"
        + "  7♣  8♣  9♣  10♣\n"
        + "J♣  Q♣  K♣  A♦  2♦\n"
        + "Draw: 3♦, 4♦, 5♦, 6♦", psv.toString());
  }

  @Test
  public void viewToStringBigPyramid2() {
    psb.startGame(psb.getDeck(), false, 5, 4);
    psv = new PyramidSolitaireTextualView(psb);
    assertEquals(
        "        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦", psv.toString());
  }

  @Test
  public void viewToStringSmallPyramid() {
    psb.startGame(psb.getDeck(), false, 2, 1);
    psv = new PyramidSolitaireTextualView(psb);
    assertEquals("  A♣\n"
        + "2♣  3♣\n"
        + "Draw: 4♣", psv.toString());
  }

  @Test
  public void viewToStringBigDraw() {
    psb.startGame(psb.getDeck(), false, 3, 6);
    psv = new PyramidSolitaireTextualView(psb);
    assertEquals("    A♣\n"
        + "  2♣  3♣\n"
        + "4♣  5♣  6♣\n"
        + "Draw: 7♣, 8♣, 9♣, 10♣, J♣, Q♣", psv.toString());
  }

  @Test
  public void viewToStringGameWon() {
    psb.startGame(psb.getDeck(), false, 2, 4);
    psb.discardDraw(0);
    psb.discardDraw(1);
    psb.discardDraw(2);
    psb.discardDraw(3);
    psb.removeUsingDraw(2, 1, 1);
    psb.removeUsingDraw(3, 1, 0);
    psb.removeUsingDraw(2, 0, 0);
    psv = new PyramidSolitaireTextualView(psb);
    assertEquals("You win!", psv.toString());
  }

  @Test
  public void viewToStringGameLost() {
    psb.startGame(psb.getDeck(), false, 9, 4);
    psb.removeUsingDraw(1, 8, 7);
    psb.removeUsingDraw(1, 8, 4);
    psb.removeUsingDraw(1, 8, 3);
    psb.removeUsingDraw(0, 7, 3);
    psb.discardDraw(1);
    psb.discardDraw(3);
    psb.remove(8, 2);
    psb.removeUsingDraw(2, 8, 6);
    psv = new PyramidSolitaireTextualView(psb);
    assertEquals("Game over. Score: 263", psv.toString());
  }

  @Test
  public void testRender() {
    // Cannot access my appendable field in the view class because the field is private and
    // my Interface for view is given and not changeable -> Therefore testing difficult
    psb.startGame(psb.getDeck(), false, 9, 7);
    psv = new PyramidSolitaireTextualView(psb, new StringBuilder(""));
    psv.render();
    psb.discardDraw(3);
    psv.render();
    assertEquals(""
        + "                A♣\n"
        + "              2♣  3♣\n"
        + "            4♣  5♣  6♣\n"
        + "          7♣  8♣  9♣  10♣\n"
        + "        J♣  Q♣  K♣  A♦  2♦\n"
        + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
        + "Draw: 7♠, 8♠, 9♠,   , J♠, Q♠, K♠", psv.toString());
  }

  @Test
  public void testShuffleBasic() {
    psb = new BasicPyramidSolitaire(new Random(4));
    psb.startGame(psb.getDeck(), true, 9, 7);
    psv = new PyramidSolitaireTextualView(psb, new StringBuilder(""));
    assertEquals(""
        + "                3♦\n"
        + "              K♦  6♦\n"
        + "            9♠  3♥  Q♠\n"
        + "          6♥  J♣  7♣  7♦\n"
        + "        10♠ 10♥ 6♠  A♣  4♥\n"
        + "      5♣  6♣  8♦  Q♣  2♦  K♠\n"
        + "    3♣  4♦  5♦  2♥  8♣  9♦  J♦\n"
        + "  8♥  5♠  10♦ Q♦  7♠  J♥  A♥  A♦\n"
        + "K♣  K♥  9♣  2♠  3♠  7♥  8♠  5♥  2♣\n"
        + "Draw: 10♣, J♠, A♠, Q♥, 4♣, 9♥, 4♠", psv.toString());
  }

  // Trypeak pyramid tests

  @Test
  public void viewToStringTryPeak() {
    tpg.startGame(tpg.getDeck(), false, 8, 6);
    psv = new PyramidSolitaireTextualView(tpg, new StringBuilder(""));
    assertEquals(
        "              A♣              2♣              3♣\n"
            + "            4♣  5♣          6♣  7♣          8♣  9♣\n"
            + "          10♣ J♣  Q♣      K♣  A♦  2♦      3♦  4♦  5♦\n"
            + "        6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥  3♥  4♥\n"
            + "      5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥  A♠  2♠  3♠  4♠\n"
            + "    5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠  K♠  A♣  2♣  3♣  4♣  5♣\n"
            + "  6♣  7♣  8♣  9♣  10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦  6♦  7♦\n"
            + "8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "Draw: J♥, Q♥, K♥, A♠, 2♠, 3♠", psv.toString());
  }

  @Test
  public void viewToStringTryPeakMoves() {
    tpg.startGame(tpg.getDeck(), false, 8, 6);
    psv = new PyramidSolitaireTextualView(tpg, new StringBuilder(""));
    tpg.remove(7, 5);
    tpg.remove(7, 0, 7, 10);
    assertEquals(
        ""
            + "              A♣              2♣              3♣\n"
            + "            4♣  5♣          6♣  7♣          8♣  9♣\n"
            + "          10♣ J♣  Q♣      K♣  A♦  2♦      3♦  4♦  5♦\n"
            + "        6♦  7♦  8♦  9♦  10♦ J♦  Q♦  K♦  A♥  2♥  3♥  4♥\n"
            + "      5♥  6♥  7♥  8♥  9♥  10♥ J♥  Q♥  K♥  A♠  2♠  3♠  4♠\n"
            + "    5♠  6♠  7♠  8♠  9♠  10♠ J♠  Q♠  K♠  A♣  2♣  3♣  4♣  5♣\n"
            + "  6♣  7♣  8♣  9♣  10♣ J♣  Q♣  K♣  A♦  2♦  3♦  4♦  5♦  6♦  7♦\n"
            + "    9♦  10♦ J♦  Q♦      A♥  2♥  3♥  4♥      6♥  7♥  8♥  9♥  10♥\n"
            + "Draw: J♥, Q♥, K♥, A♠, 2♠, 3♠", psv.toString());
  }

  @Test
  public void viewToStringShuffleTryPeak() {
    tpg = new TrypeaksPyramidSolitaire(new Random(4));
    tpg.startGame(tpg.getDeck(), true, 8, 6);
    psv = new PyramidSolitaireTextualView(tpg, new StringBuilder(""));
    assertEquals(
        "              10♥             6♣              5♣\n"
            + "            10♥ 4♠          10♦ 2♣          7♣  A♣\n"
            + "          2♠  A♠  3♣      2♥  4♣  2♥      6♥  A♥  4♣\n"
            + "        2♦  4♦  8♦  K♦  3♥  6♥  7♥  6♦  8♠  Q♥  Q♣  6♠\n"
            + "      3♦  2♠  J♣  Q♠  6♠  K♥  8♣  8♣  K♣  4♥  7♦  Q♠  K♣\n"
            + "    9♠  5♠  A♣  A♦  6♣  5♥  J♦  7♣  10♦ 9♣  Q♥  5♠  Q♦  10♣\n"
            + "  3♥  5♣  9♦  J♥  7♠  4♦  8♦  2♣  Q♦  9♦  J♣  J♠  K♦  7♠  3♠\n"
            + "K♠  10♠ J♦  3♠  7♥  J♥  9♥  7♦  K♠  8♥  8♠  9♠  A♠  6♦  J♠  5♦\n"
            + "Draw: K♥, 3♣, 9♥, 9♣, A♦, 2♦", psv.toString());
  }

  @Test
  public void viewToStringTryPeak1() {
    tpg.startGame(tpg.getDeck(), false, 1, 6);
    psv = new PyramidSolitaireTextualView(tpg, new StringBuilder(""));
    assertEquals("A♣\n"
        + "Draw: 2♣, 3♣, 4♣, 5♣, 6♣, 7♣", psv.toString());
  }
}