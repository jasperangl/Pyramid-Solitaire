package cs3500.pyramidsolitaire.controller;

/**
 * An interaction with the user consists of some input to send the program and some output to
 * expect.  We represent it as an object that takes in two StringBuilders and produces the intended
 * effects on them.
 */
public interface Interaction {

  void apply(StringBuilder in, StringBuilder out);

  /**
   * Prints to the output file.
   * @param lines Input lines as Strings
   * @return returns the output Sting
   */
  static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append('\n');
      }
    };
  }

  /**
   * Returns the Input String.
   * @param in The input String
   * @return the input returns the input string
   */
  static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in);
    };
  }
}
