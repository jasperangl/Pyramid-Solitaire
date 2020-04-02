package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;
import java.util.List;

/**
 * Is a Pyramid Solitaire Games visual representation.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private Appendable ap;
  private final PyramidSolitaireModel<?> model;

  /**
   * Constructs a Pyramid Solitaire View.
   *
   * @param model The model of a Pyramid solitaire game
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    this.ap = null;
  }

  /**
   * Constructs a Pyramid Solitaire View.
   *
   * @param model The model of a Pyramid solitaire game
   * @param ap Appendable used to append strings
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Model and appendable cannot be null");
    }
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    try {
      if (model.getNumDraw() == -1) {
        return "";
      }
      int nrow = model.getNumRows();
      List<?> draw = model.getDrawCards();
      String finalstr = "";
      int score = model.getScore();
      int mtspaces = (nrow) * 2 - 2;

      if (model.isGameOver() && model.getScore() == 0) {
        return "You win!";
      }
      else if (model.isGameOver()) {
        return "Game over. Score: " + Integer.toString(score);
      }

      //TODO: Have to make toString work more generally
      for (int row = 0; row < nrow; row++) {
        finalstr += this.repeat(mtspaces, " ");
        mtspaces = mtspaces - 2;
        for (int col = 0; col < model.getRowWidth(row); col++) {
          Object card = model.getCardAt(row, col);
          if (card == null && col == 0 && row == 0) {
            finalstr += "  ";
          }
          else if (card == null && col == 0) {
            finalstr += "   ";
          }
          else if (card == null) {
            finalstr += "    ";
          }
          else if (col == 0) {
            String temp = card.toString() + " ";
            String temp2 = temp.substring(0, 3);
            finalstr += temp2;
          } else {
            try {
              String temp = " " + card.toString() + " ";
              String temp2 = temp.substring(0, 4);
              finalstr += temp2;
            } catch (Exception e) {
              finalstr += "    ";
            }
          }
        }
        finalstr = finalstr.stripTrailing();
        finalstr += "\n";

      }
      // creates the draw
      finalstr += "Draw: ";
      for (int i = 0; i < draw.size(); i++) {
        if (i == 0 && draw.get(i) != null) {
          finalstr += draw.get(i).toString();
        } else if (draw.get(i) != null) {
          String temp = ", " + draw.get(i).toString() + " ";
          String temp3 = temp.substring(4, 5).replace(" ", "");
          String temp2 = temp.substring(0, 4) + temp3;
          finalstr += temp2;
        } else if (draw.get(i) == null && i > 0) {
          finalstr += ",   ";
        } else if (draw.get(i) == null && i == 0) {
          finalstr += "  ";
        }
      }

      return finalstr;
    } catch (Exception e) {
      return "";
    }
  }

  /**
   * Returns the String multiplied by the given count.
   *
   * @param count # of times given String should be repeated
   * @param str String that should be repeated
   * @return repeated string by the amount of the given count
   */
  private String repeat(int count, String str) {
    return new String(new char[count]).replace("\0", str);
  }

  @Override
  public void render() {
    try {
      this.ap.append(this.toString());
    } catch (IOException e) {
      throw new IllegalStateException("IO Exception");
    }

  }
}
