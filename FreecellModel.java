package cs3500.hw02;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;



/**
 * FreecellModel for Freecell game, a type of solitaire game.
 * Implements FreecellOperations interface.
 */
public class FreecellModel implements FreecellOperations<Card> {


  private boolean gameBegun = false;

  private ArrayList<ArrayList<Card>> cascadePiles = new ArrayList<ArrayList<Card>>();
  private ArrayList<ArrayList<Card>> openPiles = new ArrayList<ArrayList<Card>>();
  private ArrayList<ArrayList<Card>> foundationPiles = new ArrayList<ArrayList<Card>>();
  private List<Card> deck = new ArrayList<Card>();


  /**
   * Default constructor for Freecell model, defaults to minimum pile sizes.
   */
  public FreecellModel() {
    this(4, 1);
  }

  /**
   * Constructor for Freecell model where number of cascade and open piles can be specified.
   * @param numOfCascadePiles   the number of cascade piles
   * @param numOfOpenPiles      the number of open piles
   */
  public FreecellModel(int numOfCascadePiles, int numOfOpenPiles) {
    if (numOfCascadePiles < 4) {
      throw new IllegalArgumentException("Incorrect number of cascade piles!");
    }
    if (numOfOpenPiles < 1) {
      throw new IllegalArgumentException("Incorrect number of open piles!");
    }
    if (numOfCascadePiles > 52 || numOfOpenPiles > 52) {
      throw new IllegalArgumentException("Incorrect number of open piles!");
    }

    for (int i = 0; i < numOfCascadePiles; i++) {
      cascadePiles.add(new ArrayList<Card>());
    }
    for (int i = 0; i < numOfOpenPiles; i++) {
      openPiles.add(new ArrayList<Card>());
    }
    foundationPiles.add(new ArrayList<Card>());
    foundationPiles.add(new ArrayList<Card>());
    foundationPiles.add(new ArrayList<Card>());
    foundationPiles.add(new ArrayList<Card>());
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();

    // clubs
    for (int i = 1; i < 14; i++) {
      deck.add(new Card(i, Card.CardSuit.Club));
    }

    // spades
    for (int i = 1; i < 14; i++) {
      deck.add(new Card(i, Card.CardSuit.Spade));
    }

    // hearts
    for (int i = 1; i < 14; i++) {
      deck.add(new Card(i, Card.CardSuit.Heart));
    }

    // diamonds
    for (int i = 1; i < 14; i++) {
      deck.add(new Card(i, Card.CardSuit.Diamond));
    }
    return deck;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean
          shuffle)
          throws
          IllegalArgumentException {
    if (invalidDeck(deck)) {
      throw new IllegalArgumentException("Deck is invalid!");
    }
    else if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Bad number of cascade piles");
    }
    else if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Bad number of open piles");
    }
    else if (numCascadePiles > 52 || numOpenPiles > 52) {
      throw new IllegalArgumentException("Incorrect number of open piles!");
    }

    if (shuffle) {
      Collections.shuffle(deck);
      this.deck = deck;
    }

    gameBegun = true;
    new FreecellModel(numCascadePiles, numOpenPiles);

    cascadePiles = new ArrayList<ArrayList<Card>>();
    openPiles = new ArrayList<ArrayList<Card>>();
    foundationPiles = new ArrayList<ArrayList<Card>>();
    for (int i = 0; i < numCascadePiles; i++) {
      cascadePiles.add(new ArrayList<Card>());
    }

    for (int i = 0; i < numOpenPiles; i++) {
      openPiles.add(new ArrayList<Card>());
    }

    foundationPiles.add(new ArrayList<Card>());
    foundationPiles.add(new ArrayList<Card>());
    foundationPiles.add(new ArrayList<Card>());
    foundationPiles.add(new ArrayList<Card>());

    // round robin
    int cardIterations = 52 / cascadePiles.size();
    int extra = 52 % cascadePiles.size();
    int curCard = 0;
    for (int i = 0; i < cardIterations; i++) {
      for (int j = 0; j < cascadePiles.size(); j++) {
        cascadePiles.get(j).add(deck.get(curCard));
        curCard++;
      }
    }
    for (int i = 0; i < extra; i++) {
      cascadePiles.get(i).add(deck.get(curCard));
      curCard++;
    }
  }

  /**
   * Determines if there are any duplicate cards in this deck.
   * @param deck   Deck to check for duplicates
   * @return       If there is a duplicate or not
   */
  public boolean cardDuplicates(List<Card> deck) {
    boolean duplicated = false;
    for (int i = 0; i < deck.size(); i++) {
      for (int j = 0; j < deck.size(); j++) {
        if (i != j && deck.get(i).equals(deck.get(j))) {
          duplicated = true;
          break;
        }
      }
      if (duplicated) {
        break;
      }
    }
    return duplicated;
  }

  /**
   * Determines if this deck is a valid deck.
   * @param deck    Deck to check for validity
   * @return        If this is an invalid deck or not
   */
  private boolean invalidDeck(List<Card> deck) {
    return deck.size() != 52
            || cardDuplicates(deck);
  }


  @Override
  // FOUNDATION piles cannot be a source, once a card is in the FOUNDATION pile, it stays
  public void move(PileType source,
                   int pileNumber,
                   int cardIndex,
                   PileType destination,
                   int destPileNumber) throws IllegalArgumentException {

    Card moveThis;
    // are the arguments for source valid (valid pileNumber, valid index, non-empty pile)
    switch (source) {
      case OPEN:
        if (pileNumber < 0 || pileNumber >= openPiles.size()
                || openPiles.get(pileNumber).isEmpty() || cardIndex != 0) {
          throw new IllegalArgumentException("Invalid move");
        }
        else {
          moveThis = openPiles.get(pileNumber).get(cardIndex);
        }
        break;
      case CASCADE:
        if (pileNumber < 0 || pileNumber >= cascadePiles.size()
                || cascadePiles.get(pileNumber).size() == 0
                || cardIndex != (cascadePiles.get(pileNumber).size() - 1)) {
          throw new IllegalArgumentException("Invalid move");
        }
        else {
          moveThis = cascadePiles.get(pileNumber).get(cardIndex);
        }
        break;
      case FOUNDATION:
        throw new IllegalArgumentException("Invalid move");
      default: throw new IllegalArgumentException("Invalid move");
    }

    // is it a valid move (empty open pile, correct suit and value for foundation and cascade)
    switch (destination) {
      case OPEN:
        if (destPileNumber < 0 || destPileNumber >= openPiles.size()) {
          throw new IllegalArgumentException("Invalid move");
        }
        else if (openPiles.get(destPileNumber).size() != 0) { // has to be empty
          throw new IllegalArgumentException("Invalid move");
        }
        break;
      case CASCADE:
        if (destPileNumber < 0 || destPileNumber >= cascadePiles.size()) {
          throw new IllegalArgumentException("Invalid move");
        }
        // if not empty destination, colors have to be alternating, value has to be decreasing
        else if (cascadePiles.get(destPileNumber).size() != 0
                && (moveThis.suitToColor().equals(cascadePiles.get(destPileNumber)
                .get(cascadePiles.get(destPileNumber).size() - 1).suitToColor())
                || moveThis.getValue() != cascadePiles.get(destPileNumber)
                .get(cascadePiles.get(destPileNumber).size() - 1).getValue() - 1)) {
          throw new IllegalArgumentException("Invalid move");
        }
        break;
      case FOUNDATION:
        if (destPileNumber < 0 || destPileNumber >= 4) {
          throw new IllegalArgumentException("Invalid move");
        }
        // if empty, has to be an Ace
        else if (foundationPiles.get(destPileNumber).size() == 0
                && moveThis.getValue() != 1) {
          throw new IllegalArgumentException("Invalid move");
        }
        // if not empty, has to be increasing and match the suit
        else if (foundationPiles.get(destPileNumber).size() > 0) {
          if (foundationPiles.get(destPileNumber)
                  .get(foundationPiles.get(destPileNumber).size() - 1).getSuit()
                  != moveThis.getSuit() || foundationPiles.get(destPileNumber)
                  .get(foundationPiles.get(destPileNumber).size() - 1).getValue()
                  != moveThis.getValue() - 1) {
            throw new IllegalArgumentException("Invalid move");
          }
        }

        break;
      default: throw new IllegalArgumentException("Invalid move");
    }
    // card to move
    Card move = new Card(3, Card.CardSuit.Club);
    if (source == PileType.OPEN) {
      move = openPiles.get(pileNumber).remove(cardIndex);
    }
    else if (source == PileType.CASCADE) {
      move = cascadePiles.get(pileNumber).remove(cardIndex);
    }
    switch (destination) { // finding where to put it
      case OPEN:
        openPiles.get(destPileNumber).add(move);
        break;
      case CASCADE:
        cascadePiles.get(destPileNumber).add(move);
        break;
      case FOUNDATION:
        foundationPiles.get(destPileNumber).add(move);
        break;
      default: throw new IllegalArgumentException("Invalid move");
    }


  }

  @Override
  public String getGameState() {
    String toReturn = "";
    if (!gameBegun) {
      return toReturn;
    }
    else {
      // Foundation piles
      for (int i = 0; i < 4; i++) {
        int foundSize = foundationPiles.get(i).size();
        toReturn += "F" + Integer.toString(i + 1) + ":";
        for (int j = 0; j < foundSize; j++) {
          toReturn += " " + foundationPiles.get(i).get(j).toString();
          if (j != foundSize - 1) {
            toReturn += ",";
          }
        }
        toReturn += "\n";
      }

      // Open piles
      for (int i = 0; i < openPiles.size(); i++) {
        toReturn += "O" + Integer.toString(i + 1) + ":";
        if (!(openPiles.get(i).isEmpty())) {
          toReturn += " " + openPiles.get(i).toString();
        }
        toReturn += "\n";
      }

      // Cascade piles
      for (int i = 0; i < cascadePiles.size(); i++) {
        toReturn += "C" + Integer.toString(i + 1) + ":";
        int cascLength = cascadePiles.get(i).size();
        for (int j = 0; j < cascLength; j++) {
          toReturn += " " + cascadePiles.get(i).get(j).toString();
          if (j != cascLength - 1) {
            toReturn += ",";
          }
        }
        if (i != cascadePiles.size() - 1) {
          toReturn += "\n";
        }
      }
    }

    return toReturn;
  }

  @Override
  public boolean isGameOver() {
    int numberOfFoundationCards = 0;
    for (int i = 0; i < 4; i++) {
      int pileSize = foundationPiles.get(i).size();
      numberOfFoundationCards += pileSize;
    }
    return numberOfFoundationCards == 52;
  }
}
