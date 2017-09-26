import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cs3500.hw02.Card;

/**
 * Tests all methods within Card class.
 * (clubs ♣, spades ♠, hearts ♥, diamonds ♦)
 */
public class CardTest {
  Card clubThree = new Card(3, Card.CardSuit.Club);
  Card aceSpade = new Card(1, Card.CardSuit.Spade);
  Card tenHeart = new Card(10, Card.CardSuit.Heart);
  Card kingDiamond = new Card(13, Card.CardSuit.Diamond);


  @Test
  public void threeClubsStringTest() {
    assertEquals("3♣",
            clubThree.toString());
  }

  @Test
  public void aceSpadeStringTest() {
    assertEquals("A♠",
            aceSpade.toString());
  }

  @Test
  public void tenHeartStringTest() {
    assertEquals("10♥",
            tenHeart.toString());
  }

  @Test
  public void kingDiamondStringTest() {
    assertEquals("K♦",
            kingDiamond.toString());
  }

  @Test
  public void nonEqualEqualsTest() {
    assertEquals(false,
            clubThree.equals(aceSpade));
  }

  @Test
  public void isEqualEqualsTest() {
    assertEquals(true,
            aceSpade.equals(aceSpade));
  }

  @Test
  public void notCardEqualsEqualsTest() {
    assertEquals(false,
            kingDiamond.equals("not card"));
  }

  @Test
  public void clubSuitToColor() {
    assertEquals("black",
            clubThree.suitToColor());
  }

  @Test
  public void spadeSuitToColor() {
    assertEquals("black",
            aceSpade.suitToColor());
  }

  @Test
  public void heartSuitToColor() {
    assertEquals("red",
            tenHeart.suitToColor());
  }

  @Test
  public void diamondSuitToColor() {
    assertEquals("red",
            kingDiamond.suitToColor());
  }

  @Test
  public void clubSuitToString() {
    assertEquals("♣",
            clubThree.suitToString());
  }

  @Test
  public void heartSuitToString() {
    assertEquals("♥",
            tenHeart.suitToString());
  }

  @Test
  public void diamondSuitToString() {
    assertEquals("♦",
            kingDiamond.suitToString());
  }

  @Test
  public void spadeSuitToString() {
    assertEquals("♠",
            aceSpade.suitToString());
  }


}
