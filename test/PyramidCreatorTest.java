import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw04.TrypeaksPyramidSolitaire;
import org.junit.Test;

/**
 * Represents the testing class for the Pyramid Creator.
 */
public class PyramidCreatorTest {

  @Test
  public void createTest() {

    assertTrue(PyramidSolitaireCreator.create(GameType.BASIC) instanceof BasicPyramidSolitaire);
    assertTrue(PyramidSolitaireCreator.create(GameType.RELAXED) instanceof RelaxedPyramidSolitaire);
    assertTrue(PyramidSolitaireCreator.create(GameType.TRIPEAKS)
        instanceof TrypeaksPyramidSolitaire);
    assertFalse(PyramidSolitaireCreator.create(GameType.BASIC) instanceof RelaxedPyramidSolitaire);

  }

}
