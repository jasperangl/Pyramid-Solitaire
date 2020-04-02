package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the suit of a Card for the Pyramid Solitaire Game.
 */
public enum Suit {
  Heart,
  Spade,
  Club,
  Diamond;

  @Override
  public String toString() {
    switch (this) {
      case Club:
        return "♣";
      case Heart:
        return "♥";
      case Spade:
        return "♠";
      case Diamond:
        return "♦";
      default:
        throw new IllegalArgumentException("Invalid suit");
    }
  }
}
