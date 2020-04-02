package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the controller of a Pyramid Solitaire Game, who enables that the game is playable on
 * certain commands. It accepts inputs and converts it to commands for the model or view.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private Readable in;
  private Appendable out;
  private PyramidSolitaireView view;
  private boolean gameQuit;


  /**
   * Constructs a Pyramid Solitaire Game Controller.
   *
   * @param rd The Readable/ input for the game
   * @param ap The Appendable/output for the game
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null");
    }
    this.in = rd;
    this.out = ap;
    this.gameQuit = false;
  }


  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (Exception e) {
      throw new IllegalStateException("Wrong game");
    }

    try {
      this.out.append("\nHello this is my Pyramid Solitaire Game! \n");
      this.out.append("The goal is to get rid of all the cards in the Pyramid by combining "
          + "card values that add up to 13\n");
      this.out.append("One example would be J♣ and 2♣ which add up to 13 and are therefore removable (A♣ is worth 1 point) \n");
      this.out.append("The rules:\n "
          + "- Only 'visible' cards are allowed to be removed (a card is visible if it is not covered but either card above it) \n"
          + " - You can only go through the pile once (so choose wisely which cards you discard) \n\n");

      this.out.append("The commands to play the game are as follows:\n");
      this.out.append(" - rm1 : To remove a King (since his value is 13) \n");
      this.out.append(" - rm2 : To remove two cards that are in the game \n");
      this.out.append(" - rmwd : To remove two cards one in the game and one on the draw pile\n");
      this.out.append(" - dd : To remove a card from the draw and pull a new one from the pile\n");
      this.out.append(" - q or Q : To quit the game\n\n");
      this.out.append("To pick the cards you want to remove simply enter their coordinates\n");
      this.out.append("Some Examples:\n - rm1 4 2 (x1,y1) \n - rm2 3 1 4 4 (x1,y1,x2,y2) \n "
          + "- rmwd 2 4 1 (d1,x1,y1) \n - dd 5 (d1) \n \n");
      this.out.append("Let's see if you have what it takes to win the game!\n\n");
    } catch (IOException e) {
      throw new IllegalStateException("IO Exception");
    }


    Scanner scan = new Scanner(this.in);
    view = new PyramidSolitaireTextualView(model, this.out);
    this.renderGame(model);

    while (scan.hasNext() && !model.isGameOver() && !this.gameQuit) {
      String curr = scan.next();
      this.gameQuit(curr, model);

      if (this.gameQuit) {
        return;
      }



      if (curr.equals("rm1") || curr.equals("rm2") || curr.equals("rmwd") || curr.equals("dd")) {
        try {
          this.handleMoves(curr, model, scan);
        } catch (IOException e) {
          throw new IllegalStateException("IO Exception");
        }
      } else {
        try {
          this.out.append("Invalid Command. Try again.\n");
        } catch (IOException e) {
          throw new IllegalStateException("IO Exception");
        }
      }
    }
  }

  private <K> void handleMoves(String command, PyramidSolitaireModel<K> model, Scanner scan)
      throws IOException {
    ArrayList<Integer> loi = new ArrayList<>();
    boolean moveExecuted = false;

    while (scan.hasNext() && !moveExecuted && !model.isGameOver()) {
      String curr = scan.next();
      this.gameQuit(curr, model);
      if (gameQuit) {
        return;
      }
      Integer move = this.currValidInput(curr);
      if (move != null) {
        switch (command) {
          case "rm1":
            loi.add(move - 1);
            if (loi.size() == 2) {
              moveExecuted = true;
              try {
                model.remove(loi.get(0), loi.get(1));
                this.renderGame(model);
              } catch (IllegalArgumentException e) {
                this.out.append("Invalid move. Try again. No King or invalid position"
                    + " to remove a King\n");
              }
              loi.clear();
            }
            break;
          case "rm2":
            loi.add(move - 1);
            if (loi.size() == 4) {
              moveExecuted = true;
              try {
                model.remove(loi.get(0), loi.get(1), loi.get(2), loi.get(3));
                this.renderGame(model);
              } catch (IllegalArgumentException e) {
                this.out.append("Invalid move. Try again. Cards don't sum to 13\n");
              }

            }
            break;
          case "rmwd":
            loi.add(move - 1);
            if (loi.size() == 3) {
              moveExecuted = true;
              try {
                model.removeUsingDraw(loi.get(0), loi.get(1), loi.get(2));
                this.renderGame(model);
              } catch (IllegalArgumentException e) {
                this.out.append("Invalid move. Try again. "
                    + "Pyramid Card and Draw Card don't add to 13\n");
              }
              loi.clear();
            }
            break;
          case "dd":
            loi.add(move - 1);
            if (loi.size() == 1) {
              moveExecuted = true;
              try {
                model.discardDraw(loi.get(0));
                this.renderGame(model);
              } catch (IllegalArgumentException e) {
                this.out.append("Invalid move. Try again. Draw Position is invalid\n");
              }
              loi.clear();
            }
            break;
          default:
            throw new IllegalArgumentException("Should not happen. Invalid Command got through");
        }
      }
    }
  }

  private Integer currValidInput(String curr) {
    try {
      return Integer.parseInt(curr);
    } catch (Exception e) {
      try {
        this.out.append("Invalid input. Has to be a Int or q\n");
        return null;
      } catch (IOException ex) {
        return null;
      }
    }
  }

  private void renderGame(PyramidSolitaireModel model) {
    if (model.isGameOver()) {
      try {
        view.render();
      } catch (Exception e) {
        throw new IllegalStateException("IO Exception");
      }
    } else {
      try {
        view.render();
        this.out.append("\nScore: ").append(String.valueOf(model.getScore())).append("\n");
      } catch (Exception e) {
        throw new IllegalStateException("IO Exception");
      }
    }
  }

  private <K> void gameQuit(String curr, PyramidSolitaireModel<K> model) {
    if (curr.equals("q") || curr.equals("Q")) {
      try {
        gameQuit = true;
        this.out.append("Game quit!\nState of game when quit:\n");
        this.renderGame(model);
      } catch (IOException e) {
        throw new IllegalStateException("IO Exception");
      }
    }
  }

}
