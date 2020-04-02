package cs3500.pyramidsolitaire.model.hw02;


/**
 * Represents the Value of a Card for the Pyramid Solitaire Game.
 */
public enum Value {
  ACE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  JACK(11),
  QUEEN(12),
  KING(13);

  private int cardValue;

  Value(int value) {
    this.cardValue = value;
  }

  public int getCardValue() {
    return cardValue;
  }
}
