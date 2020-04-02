import static junit.framework.TestCase.assertEquals;

import cs3500.pyramidsolitaire.controller.InputInteraction;
import cs3500.pyramidsolitaire.controller.Interaction;
import cs3500.pyramidsolitaire.controller.MockAppendable;
import cs3500.pyramidsolitaire.controller.MockController;
import cs3500.pyramidsolitaire.controller.PrintInteraction;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.MockModel;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.TrypeaksPyramidSolitaire;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the testing class for every cases of the controller.
 */
public class SolitaireControllerTest {

  BasicPyramidSolitaire psg;
  TrypeaksPyramidSolitaire tpg;
  PyramidSolitaireTextualController psc;
  Readable in;
  Appendable out;

  @Before
  public void init() {
    psg = new BasicPyramidSolitaire();
    tpg = new TrypeaksPyramidSolitaire();

  }

  //  ----- MOCK TESTS -----

  void testRun(StringBuilder builder, PyramidSolitaireModel model, int numRow, int numDraw,
      Interaction... interactions) throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    PyramidSolitaireController controller =
        new PyramidSolitaireTextualController(input, new StringBuilder());
    controller.playGame(new MockModel(actualOutput), new MockModel(actualOutput).getDeck(),
        false, numRow, numDraw);

    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  @Test
  public void testMockDiscardDraw() throws IOException {
    StringBuilder log = new StringBuilder();
    PyramidSolitaireModel model = new MockModel(log);
    testRun(log, model, 9, 7, Interaction.inputs("dd 6"),
        Interaction.prints("Startgame was called\n"
            + "Accessed discarding a draw card"));
  }

  @Test
  public void testMocksRemove() throws IOException {
    StringBuilder log = new StringBuilder();
    PyramidSolitaireModel model = new MockModel(log);
    testRun(log, model, 9, 7, Interaction.inputs("rmwd 1 9 9\n rm1 9 3 \n"
            + "rm2 9 2 9 4 "),
        Interaction.prints("Startgame was called\n"
            + "Accessed remove using draw\n"
            + "Accessed remove a King\n"
            + "Accessed remove a two cards"));
  }

  void testInputs() throws IOException {
    Reader in = new StringReader("rm1 5 3");
    StringBuilder dontCareOutput = new StringBuilder();
    PyramidSolitaireTextualController controller = new PyramidSolitaireTextualController(in,
        dontCareOutput);

    StringBuilder log = new StringBuilder();
    PyramidSolitaireController mock = new MockController(log);

    mock.playGame(psg, psg.getDeck(), false, 5, 6);
    assertEquals("numRows = 5, numDraw = 6\n", log.toString());
  }

  @Test
  public void testMockControllerCorrect() {
    StringBuilder log = new StringBuilder();
    PyramidSolitaireController mockController = new MockController(log);
    mockController.playGame(psg, psg.getDeck(), false, 5, 7);
    assertEquals(new StringBuilder("numRows = 5, numDraw = 7"), log);
  }

  @Test
  public void testMockControllerNullModel() {
    StringBuilder log = new StringBuilder();
    PyramidSolitaireController mockController = new MockController(log);
    mockController.playGame(null, psg.getDeck(), false, 5, 7);
    assertEquals(new StringBuilder("Model should not be null\n"
        + "Starting the game failed due to bad inputs\n"
        + "numRows = 5, numDraw = 7"), log);
  }

  //  -----   INTERACTION TESTS   -----

  private void testRun9row7draw(Interaction... interactions) throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }
    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(input, actualOutput);
    controller.playGame(psg, psg.getDeck(), false, 9, 7);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  private void testRun3row7draw(Interaction... interactions) throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }
    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(input, actualOutput);
    controller.playGame(psg, psg.getDeck(), false, 3, 7);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  private void testRun5row6draw(Interaction... interactions) throws IOException {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }
    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();
    PyramidSolitaireTextualController controller =
        new PyramidSolitaireTextualController(input, actualOutput);
    controller.playGame(psg, psg.getDeck(), false, 5, 6);
    assertEquals(expectedOutput.toString(), actualOutput.toString());
  }

  // COMMANDS TESTS
  @Test
  public void testIllegalCommand() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm 2 2 rtqb5 "),
        new PrintInteraction("Invalid Command. Try again.\n"
            + "Invalid Command. Try again.\n"
            + "Invalid Command. Try again.\n"
            + "Invalid Command. Try again."));
  }

  @Test
  public void testIllegalCommandThenGameQuit() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rmw d dd q b3"),
        new PrintInteraction(""
            + "Invalid Command. Try again.\n"
            + "Invalid Command. Try again.\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"));
  }

  @Test
  public void testGameQuit() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm Q"),
        new PrintInteraction(""
            + "Invalid Command. Try again.\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"));
  }

  // TEST REMOVE KING

  @Test
  public void testRM1() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm1 werg 5 rewf3 3 g"),
        new PrintInteraction("Invalid input. Has to be a Int or q\n"
            + "Invalid input. Has to be a Int or q\n"
            + "        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣      A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 81\n"
            + "Invalid Command. Try again."));
  }

  @Test
  public void testRM1NoKing() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm1 5 2"),
        new PrintInteraction("Invalid move. Play again. Wrong or invalid"
            + " position to remove a King"));
  }

  @Test
  public void testRM1BadInputs() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm1 7 0"),
        new PrintInteraction("Invalid move. Play again. Wrong or invalid"
            + " position to remove a King"));
  }

  // TEST REMOVE 2 CARDS

  @Test
  public void testRM2() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm2 5 1 5 5ergfe 5"),
        new PrintInteraction("Invalid input. Has to be a Int or q\n"
            + "        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "    Q♣  K♣  A♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 81"));
  }

  @Test
  public void testRM2DontSum13() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm2 5 1 5 4"),
        new PrintInteraction("Invalid move. Play again. Cards don't sum to 13"));
  }

  @Test
  public void testRM2InvalidInputs() throws IOException {
    this.testRun5row6draw(new PrintInteraction("        A♣\n"
            + "      2♣  3♣\n"
            + "    4♣  5♣  6♣\n"
            + "  7♣  8♣  9♣  10♣\n"
            + "J♣  Q♣  K♣  A♦  2♦\n"
            + "Draw: 3♦, 4♦, 5♦, 6♦, 7♦, 8♦\n"
            + "Score: 94"), new InputInteraction("rm2 4 0 4 4"),
        new PrintInteraction("Invalid move. Play again. Cards don't sum to 13"));
  }

  // TEST REMOVE WITH DRAW

  @Test
  public void testRMWD() throws IOException {
    this.testRun9row7draw(new PrintInteraction(""
        + "                A♣\n"
        + "              2♣  3♣\n"
        + "            4♣  5♣  6♣\n"
        + "          7♣  8♣  9♣  10♣\n"
        + "        J♣  Q♣  K♣  A♦  2♦\n"
        + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
        + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "Score: 294"), new InputInteraction("rmwd %6, 4 9 6"), new PrintInteraction(""
        + "Invalid input. Has to be a Int or q\n"
        + "                A♣\n"
        + "              2♣  3♣\n"
        + "            4♣  5♣  6♣\n"
        + "          7♣  8♣  9♣  10♣\n"
        + "        J♣  Q♣  K♣  A♦  2♦\n"
        + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♠  2♠      4♠  5♠  6♠\n"
        + "Draw: 7♠, 8♠, 9♠,   , J♠, Q♠, K♠\n"
        + "Score: 291"));
  }

  @Test
  public void testRMWDDontSum13() throws IOException {
    this.testRun9row7draw(new PrintInteraction(""
        + "                A♣\n"
        + "              2♣  3♣\n"
        + "            4♣  5♣  6♣\n"
        + "          7♣  8♣  9♣  10♣\n"
        + "        J♣  Q♣  K♣  A♦  2♦\n"
        + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
        + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "Score: 294"), new InputInteraction("rmwd %6, 5 9 6"), new PrintInteraction(""
        + "Invalid input. Has to be a Int or q\n"
        + "Invalid move. Play again. Pyramid Card and Draw Card don't add to 13"));
  }

  @Test
  public void testRMWDInvalidInputs() throws IOException {
    this.testRun9row7draw(new PrintInteraction(""
        + "                A♣\n"
        + "              2♣  3♣\n"
        + "            4♣  5♣  6♣\n"
        + "          7♣  8♣  9♣  10♣\n"
        + "        J♣  Q♣  K♣  A♦  2♦\n"
        + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
        + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
        + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
        + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
        + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "Score: 294"), new InputInteraction("rmwd 0  -8 8"), new PrintInteraction(""
        + "Invalid move. Play again. Pyramid Card and Draw Card don't add to 13"));
  }

  // TEST DISCARD DRAW


  @Test
  public void testDiscardDrawEmpty() throws IOException {

    this.testRun9row7draw(new PrintInteraction(""
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "Score: 294"), new InputInteraction("dd 7 3\n"),
        new PrintInteraction("                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠,   \n"
            + "Score: 294\n"
            + "Invalid Command. Try again."));
  }

  @Test
  public void testDiscardDrawInvalid() throws IOException {

    this.testRun9row7draw(new PrintInteraction("                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "Score: 294"), new InputInteraction("dd 7 dd 7 dd 0"),
        new PrintInteraction("                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠,   \n"
            + "Score: 294\n"
            + "Invalid move. Play again. Draw Position is invalid\n"
            + "Invalid move. Play again. Draw Position is invalid"));
  }

  // TEST GAME STATES

  @Test

  public void testGameWon() throws IOException {
    // Test fails because PrintInteraction always adds a new Line at the end
    // otherwise correct
    this.testRun3row7draw(new InputInteraction("rmwd 1 3 3 rmwd 2 3 2 rmwd 3 3 1 "
            + "rmwd 4 2 2 rmwd 5 2 1 rmwd 6 1 1"),
        new PrintInteraction("    A♣\n"
            + "  2♣  3♣\n"
            + "4♣  5♣  6♣\n"
            + "Draw: 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "Score: 21\n"
            + "    A♣\n"
            + "  2♣  3♣\n"
            + "4♣  5♣\n"
            + "Draw: A♦, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "Score: 15\n"
            + "    A♣\n"
            + "  2♣  3♣\n"
            + "4♣\n"
            + "Draw: A♦, 2♦, 9♣, 10♣, J♣, Q♣, K♣\n"
            + "Score: 10\n"
            + "    A♣\n"
            + "  2♣  3♣\n"
            + "\n"
            + "Draw: A♦, 2♦, 3♦, 10♣, J♣, Q♣, K♣\n"
            + "Score: 6\n"
            + "    A♣\n"
            + "  2♣\n"
            + "\n"
            + "Draw: A♦, 2♦, 3♦, 4♦, J♣, Q♣, K♣\n"
            + "Score: 3\n"
            + "    A♣\n"
            + "\n"
            + "\n"
            + "Draw: A♦, 2♦, 3♦, 4♦, 5♦, Q♣, K♣\n"
            + "Score: 1\n"
            + "You win!"));
  }

  @Test
  public void testGameLost() throws IOException {
    // Test fails because PrintInteraction always adds a new Line at the end
    // otherwise correct
    this.testRun9row7draw(new InputInteraction("dd 7 dd 6 dd 5 dd 4 dd 3 dd 2 dd 1 "
            + "rm1 9 3 rm2 9 1 9 5 rm2 9 2 9 4 "),
        new PrintInteraction("                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠, J♠, Q♠,   \n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠, J♠,   ,   \n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠, 10♠,   ,   ,   \n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠, 9♠,   ,   ,   ,   \n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠, 8♠,   ,   ,   ,   ,   \n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw: 7♠,   ,   ,   ,   ,   ,   \n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥  K♥  A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw:   ,   ,   ,   ,   ,   ,   \n"
            + "Score: 294\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "J♥  Q♥      A♠  2♠  3♠  4♠  5♠  6♠\n"
            + "Draw:   ,   ,   ,   ,   ,   ,   \n"
            + "Score: 281\n"
            + "                A♣\n"
            + "              2♣  3♣\n"
            + "            4♣  5♣  6♣\n"
            + "          7♣  8♣  9♣  10♣\n"
            + "        J♣  Q♣  K♣  A♦  2♦\n"
            + "      3♦  4♦  5♦  6♦  7♦  8♦\n"
            + "    9♦  10♦ J♦  Q♦  K♦  A♥  2♥\n"
            + "  3♥  4♥  5♥  6♥  7♥  8♥  9♥  10♥\n"
            + "    Q♥      A♠      3♠  4♠  5♠  6♠\n"
            + "Draw:   ,   ,   ,   ,   ,   ,   \n"
            + "Score: 268"
            + "\nGame over. Score: 255"));
  }

  // TESTING EXCEPTIONS

  @Test(expected = IllegalStateException.class)
  public void stateExpect() {
    psc = new PyramidSolitaireTextualController(new StringReader(""), new MockAppendable());
    psc.playGame(psg, psg.getDeck(), false, 1, 5);
  }

  @Test(expected = IllegalStateException.class)
  public void stateExpect2() {
    psc = new PyramidSolitaireTextualController(new StringReader(""), new MockAppendable());
    psc.playGame(psg, psg.getDeck(), false, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelNull() {
    psc = new PyramidSolitaireTextualController(new StringReader(""), new MockAppendable());
    psc.playGame(null, psg.getDeck(), false, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalControllerInputs() {
    psc = new PyramidSolitaireTextualController(null, new MockAppendable());
    psc.playGame(psg, psg.getDeck(), false, 4, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalControllerInputs2() {
    psc = new PyramidSolitaireTextualController(new StringReader(""), null);
    psc.playGame(psg, psg.getDeck(), false, 4, 4);
  }

  @Test(expected = IllegalStateException.class)
  public void ioExpect() {
    psc = new PyramidSolitaireTextualController(new StringReader("rm1"), new MockAppendable());
    psc.playGame(psg, psg.getDeck(), false, 5, 5);
  }

  // Test exceptions for TriPeaks

  @Test(expected = IllegalStateException.class)
  public void stateExpectT() {
    psc = new PyramidSolitaireTextualController(new StringReader(""), new MockAppendable());
    psc.playGame(tpg, tpg.getDeck(), false, 0, 5);
  }

  @Test(expected = IllegalStateException.class)
  public void stateExpectT2() {
    psc = new PyramidSolitaireTextualController(new StringReader(""), new MockAppendable());
    psc.playGame(tpg, tpg.getDeck(), false, 9, 2);
  }
}
