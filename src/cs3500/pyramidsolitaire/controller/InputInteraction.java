package cs3500.pyramidsolitaire.controller;

/**
 * Represents a user providing the program with an input.
 */
public class InputInteraction implements Interaction {

  String input;

  public InputInteraction(String input) {
    this.input = input;
  }

  public void apply(StringBuilder in, StringBuilder out) {
    in.append(input);
  }
}
