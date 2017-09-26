package cs3500.hw02;

/**
 * Represents a card, which has a value, a suit, and a pile that it's residing in.
 * Ace is a 1, Jack is 11,
 * Queen is 12, King is 13.
 */
public class Card {

  public enum CardSuit { Club, Spade, Heart, Diamond }



  private final int value;
  private final CardSuit suit;


  /**
   * Constructs a card.
   *
   * @param value what the value is of the card ranging from Ace low to King high
   * @param suit  what type of suit the card is (clubs ♣, spades ♠, hearts ♥, diamonds ♦)
   */
  public Card(int value, CardSuit suit) {
    if (!isPossibleCardValue(value)) {
      throw new IllegalArgumentException("Value is invalid");
    }
    else if (suit == null) {
      throw new IllegalArgumentException("Suit is null");
    }
    this.value = value;
    this.suit = suit;

  }

  /**
   * Is this value a possible card value.
   */
  private static boolean isPossibleCardValue(int value) {
    return (value >= 0 && value <= 13);
  }

  @Override
  public String toString() {
    String toReturn = "";
    if (value >= 2 && value <= 10) {
      toReturn += Integer.toString(value) + suitToString();
    }
    else if (value == 1) {
      toReturn += "A" + suitToString();
    }
    else if (value == 11) {
      toReturn += "J" + suitToString();
    }
    else if (value == 12) {
      toReturn += "Q" + suitToString();
    }
    else if (value == 13) {
      toReturn += "K" + suitToString();
    }
    else {
      throw new IllegalArgumentException("Invalid card");
    }
    return toReturn;
  }


  @Override
  public boolean equals(Object other) {
    if (other instanceof Card) {
      return this.suit.equals(((Card) other).suit) && this.value == ((Card) other).value;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return 2 * this.value;
  }

  /**
   * The string version of the suit's color.
   * @return  a string of this suit's color
   */
  public String suitToColor() {
    switch (suit) {
      case Diamond: return "red";
      case Club: return "black";
      case Spade: return "black";
      case Heart: return "red";
      default: throw new IllegalArgumentException("No color available");
    }

  }

  /**
   * The string version of the suit.
   * @return  a string of this suit
   */
  public String suitToString() {
    switch (suit) {
      case Club: return "♣";
      case Diamond: return "♦";
      case Spade: return "♠";
      case Heart: return "♥";
      default: throw new IllegalArgumentException("No string");
    }
  }




  public CardSuit getSuit() {
    return suit;
  }

  public int getValue() {
    return value;
  }
}


