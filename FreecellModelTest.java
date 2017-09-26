import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import cs3500.hw02.Card;
import cs3500.hw02.FreecellModel;
import cs3500.hw02.PileType;

public class FreecellModelTest {
  FreecellModel model1;
  FreecellModel model2;
  FreecellModel model3;
  FreecellModel model5;
  FreecellModel model6;

  /**
   * Setup for the test methods.
   */
  @Before
  public void setUp() {
    model1 = new FreecellModel(4, 2);
    model1.startGame(model1.getDeck(), 4, 2, false);
    model2 = new FreecellModel(8, 4);
    model2.startGame(model2.getDeck(), 8, 4, true);
    model3 = new FreecellModel(6, 3);
    model3.startGame(model3.getDeck(), 6, 3, true);
    model5 = new FreecellModel(52, 52);
    model5.startGame(model5.getDeck(), 52, 52, false);
    model6 = new FreecellModel();
  }

  @Test
  // confirming card duplicates works
  public void testCardDuplicates1() {
    assertEquals(false,
            model1.cardDuplicates(model1.getDeck()));
  }

  @Test
  // confirming is duplicate is caught
  public void testCardDuplicates2() {
    List<Card> deck = model1.getDeck();
    deck.add(new Card(3, Card.CardSuit.Spade));
    assertEquals(true,
            model1.cardDuplicates(deck));
  }


  @Test
  // confirming that getDeck returns a unique deck
  public void testGetDeckDupes() {
    List<Card> deck  = model1.getDeck();
    assertEquals(false,
            model1.cardDuplicates(deck));
  }

  @Test
  // confirming that adding a card to what's returned form getDeck would cause duplicates
  public void testGetDeck2Dupes() {
    List<Card> deck = model1.getDeck();
    deck.add(new Card(4, Card.CardSuit.Club));
    assertEquals(true,
            model1.cardDuplicates(deck));
  }

  @Test
  // confirming that adding and removing a card to what's returned from getDeck doesn't cause
  // issues
  public void testGetDeck3Dupes() {
    List<Card> deck = model2.getDeck();
    deck.remove(new Card(1, Card.CardSuit.Spade));
    deck.add(new Card(1, Card.CardSuit.Spade));
    assertEquals(false,
            model2.cardDuplicates(deck));
  }


  @Test (expected = IllegalArgumentException.class)
  // confirming invalid pile numbers for cascade throws an error
  public void testStartGameExceptionForInvalidPiles() {
    List<Card> deck = model2.getDeck();
    model2.startGame(deck, 2, 4, false);
  }

  @Test (expected = IllegalArgumentException.class)
  // confirming invalid pile numbers for open throws an error
  public void testStartGame2ExceptionForInvalidPiles() {
    List<Card> deck = model2.getDeck();
    model2.startGame(deck, 4, 0, false);
  }

  @Test (expected = IllegalArgumentException.class)
  // confirming that invalid deck throws an error in startGame
  public void testStartGameInvalidDeck() {
    List<Card> deck = new ArrayList<Card>();
    model2.startGame(deck, 4, 5, false);
  }

  @Test
  // Testing that the startGame method does indeed not shuffle when asked based on output
  // from getGameState
  public void testStartGameNoShuffle() {
    assertEquals("F1:\n"
                    + "F2:\n"
                    + "F3:\n"
                    + "F4:\n"
                    + "O1:\n"
                    + "O2:\n"
                    + "C1: A♣, 5♣, 9♣, K♣, 4♠, 8♠, Q♠, 3♥, 7♥, J♥, 2♦, 6♦, 10♦\n"
                    + "C2: 2♣, 6♣, 10♣, A♠, 5♠, 9♠, K♠, 4♥, 8♥, Q♥, 3♦, 7♦, J♦\n"
                    + "C3: 3♣, 7♣, J♣, 2♠, 6♠, 10♠, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦\n"
                    + "C4: 4♣, 8♣, Q♣, 3♠, 7♠, J♠, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦",
            model1.getGameState());
  }

  @Test
  // Testing that a startGame with shuffle set to true isn't the same as one set to false
  public void testStartGameShuffleEquality() {
    String shuffle = model2.getGameState();
    String noShuffle = model1.getGameState();
    assertEquals(false,
            shuffle.equals(noShuffle));
  }

  @Test
  // Testing moving from a cascade pile to Open
  public void testCascadetoOpen() {
    String beforeMove = model2.getGameState();
    model2.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    String afterMove = model2.getGameState();
    assertEquals(false,
            beforeMove.equals(afterMove));
  }

  @Test
  // Testing Cascade to Foundation
  public void testCascadetoFoundation() {
    String beforeMove = model5.getGameState();
    model5.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 1);
    String afterMove = model5.getGameState();
    assertEquals(false,
            beforeMove.equals(afterMove));

  }

  @Test
  // Testing Cascade to Cascade
  public void testCascadetoCascade() {
    String beforeMove = model5.getGameState();
    model5.move(PileType.CASCADE, 27, 0, PileType.CASCADE, 2);
    String afterMove = model5.getGameState();
    assertEquals(false,
            beforeMove.equals(afterMove));
  }

  @Test
  // Testing Open to Open
  public void testOpentoOpen() {
    model5.move(PileType.CASCADE, 3, 0, PileType.OPEN, 4);
    String beforeMove = model5.getGameState();
    model5.move(PileType.OPEN, 4, 0, PileType.OPEN, 2);
    String afterMove = model5.getGameState();
    assertEquals(false,
            beforeMove.equals(afterMove));
  }

  @Test
  // Testing Open to Foundation
  public void testOpentoFoundation() {
    model5.move(PileType.CASCADE, 0, 0, PileType.OPEN, 2);
    String beforeMove = model5.getGameState();
    model5.move(PileType.OPEN, 2, 0, PileType.FOUNDATION, 2);
    String afterMove = model5.getGameState();
    assertEquals(false,
            beforeMove.equals(afterMove));
  }

  @Test
  // Testing Open to Cascade
  public void testOpentoCascade() {
    model5.move(PileType.CASCADE, 27, 0, PileType.OPEN, 5);
    String beforeMove = model5.getGameState();
    model5.move(PileType.OPEN, 5, 0, PileType.CASCADE, 2);
    String afterMove = model5.getGameState();
    assertEquals(false,
            beforeMove.equals(afterMove));
  }

  @Test (expected = IllegalArgumentException.class)
  // invalid card index throws error
  public void testCascInvalidIndex() {
    model3.move(PileType.CASCADE, 2, 1, PileType.FOUNDATION, 2);
  }


  @Test (expected =  IllegalArgumentException.class)
  // invalid move due to nothing in pile to move
  public void testEmptyOpenMove() {
    model2.move(PileType.OPEN, 2, 0, PileType.CASCADE, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  // invalid move due to incompatible cards
  public void testInvalidMoveCascade() {
    model5.move(PileType.CASCADE, 3, 0, PileType.CASCADE, 4);
  }

  @Test (expected = IllegalArgumentException.class)
  // invalid move to empty Foundation
  public void testInvalidMoveToFound() {
    model5.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 2);
  }

  @Test (expected = IllegalArgumentException.class)
  // invalid move to non-empty Foundation
  public void testNonEmptyFoundation() {
    model5.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 3);
    model5.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 3);
  }

  @Test
  // compatible move to non-empty Foundation
  public void testValidNonEmptyFound() {
    String beforeMove = model5.getGameState();
    model5.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 3);

    model5.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 3);
    String afterMove = model5.getGameState();
    assertEquals(false,
            beforeMove.equals(afterMove));
  }

  @Test (expected = IllegalArgumentException.class)
  // invalid move to full Open
  public void testFullOpenMove() {
    model5.move(PileType.CASCADE, 3, 0, PileType.OPEN, 4);
    model5.move(PileType.CASCADE, 20, 0, PileType.OPEN, 4);
  }

  @Test
  // tests getGameState of game with no shuffle
  public void testGetGameStateNoShuffle() {
    String state = model1.getGameState();
    assertEquals("F1:\n" +
                    "F2:\n" +
                    "F3:\n" +
                    "F4:\n" +
                    "O1:\n" +
                    "O2:\n" +
                    "C1: A♣, 5♣, 9♣, K♣, 4♠, 8♠, Q♠, 3♥, 7♥, J♥, 2♦, 6♦, 10♦\n" +
                    "C2: 2♣, 6♣, 10♣, A♠, 5♠, 9♠, K♠, 4♥, 8♥, Q♥, 3♦, 7♦, J♦\n" +
                    "C3: 3♣, 7♣, J♣, 2♠, 6♠, 10♠, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦\n" +
                    "C4: 4♣, 8♣, Q♣, 3♠, 7♠, J♠, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦",
            state);

  }

  @Test
  // tests getGameState of game that hasn't begun
  public void testGetGameStateGameNotBegun() {
    String state = model6.getGameState();
    assertEquals("",
            state);
  }

  @Test
  // tests to confirm that isGameOver catches an unfinished game as false
  public void testIsGameOverWhenNot() {
    assertEquals(false,
            model2.isGameOver());
  }

  @Test
  // tests to confirm that isGameOver confirms when a game is indeed over
  public void testIsGameOverWhenIs() {
    for (int i = 0; i < 13; i++) {
      model5.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, 0);
    }

    for (int i = 13; i < 26; i++) {
      model5.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, 1);
    }

    for (int i = 26; i < 39; i++) {
      model5.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, 2);
    }

    for (int i = 39; i < 52; i++) {
      model5.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, 3);
    }

    assertEquals(true,
            model5.isGameOver());
  }


}
