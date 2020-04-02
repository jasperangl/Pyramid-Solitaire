package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

/**
 * Represents a Card for a Pyramid Solitaire Game.
 */
public class Card implements ICard {

  private boolean exposed;
  private final Value cardvalue;
  private Suit cardSuit;

  /**
   * Constructs a Card object.
   *
   * @param exposed represents whether a Card is exposed or not
   * @param value represents the value of a card
   * @param suit represents the suit of a card
   */
  public Card(boolean exposed, Value value, Suit suit) {
    if (value == null || suit == null) {
      throw new IllegalArgumentException("Value and/or suit cannot be null");
    }
    this.exposed = exposed;
    this.cardvalue = value;
    this.cardSuit = suit;
  }


  @Override
  public void changeExposed(boolean exposed) {
    this.exposed = exposed;
  }

  // determines whether a Card is exposed
  @Override
  public boolean isExposed() {
    return this.exposed;
  }

  @Override
  public void setSuit(Suit suit) {
    if (suit == null) {
      throw new IllegalArgumentException("Suit cannot be null");
    }
    this.cardSuit = suit;
  }

  @Override
  public int valueOf() {
    return this.cardvalue.getCardValue();
  }

  @Override
  public String toString() {
    if (this.cardvalue.getCardValue() < 10 && this.cardvalue.getCardValue() > 1) {
      return Integer.toString(cardvalue.getCardValue()) + this.cardSuit;
    }
    if (this.cardvalue.getCardValue() == 10) {
      return "10" + this.cardSuit.toString();
    }
    if (this.cardvalue.getCardValue() == 11) {
      return "J" + this.cardSuit.toString();
    }
    if (this.cardvalue.getCardValue() == 12) {
      return "Q" + this.cardSuit.toString();
    }
    if (this.cardvalue.getCardValue() == 13) {
      return "K" + this.cardSuit.toString();
    } else if (this.cardvalue.getCardValue() == 1) {
      return "A" + this.cardSuit.toString();
    }
    return "invalid";
  }


  // Whether a Card is exposed or not does not change the fact that they could be the same
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!(o instanceof Card)) {
      return false;
    }
    Card card = (Card) o;
    return cardvalue == card.cardvalue
        && cardSuit.equals(card.cardSuit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardvalue, cardSuit);
  }


}
