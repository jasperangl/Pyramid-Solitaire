package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents the interface of a Card.
 */
public interface ICard {

  /**
   * Determines whether a Card is exposed.
   *
   * @return if Card is exposed or not
   */
  boolean isExposed();

  /**
   * Gives the non-negative value of a card.
   *
   * @return the value of a Card
   */
  int valueOf();

  /**
   * changes the exposedness based on the given boolean.
   *
   * @param exposed describes whether a Card is exposed or not
   */
  void changeExposed(boolean exposed);

  /**
   * Changes the suit of a Card.
   *
   * @param suit Represents a suit of a Card
   */
  void setSuit(Suit suit);

  @Override
  String toString();

  @Override
  boolean equals(Object o);

  @Override
  int hashCode();


}
