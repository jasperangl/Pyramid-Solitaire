package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mocked Controller made to test the exceptions that the playgame method throws.
 */
public class MockController implements PyramidSolitaireController {

  final StringBuilder log;

  /**
   * Constructs the mocked controller.
   *
   * @param log a StringBuilder of given commands.
   */
  public MockController(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      log.append("Model should not be null\n");
    }
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (Exception e) {
      log.append("Starting the game failed due to bad inputs\n");
    }
    log.append(String.format("numRows = %d, numDraw = %d", numRows, numDraw));
  }
}
