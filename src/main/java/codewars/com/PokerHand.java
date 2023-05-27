package codewars.com;

import java.util.Arrays;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @see <a href="https://www.codewars.com/kata/5739174624fc28e188000465">Ranking Poker Hands</a>
 */
public class PokerHand {
  List<Card> handList;
  Map<Integer, Integer> handMap;
  List<Supplier<Boolean>> checkerRank = new ArrayList<>();
  Map<Integer, Function<PokerHand, Integer>> checkerEqualsRank = new HashMap<>();

  public static void main(String[] args) {
    PokerHand myHand = new PokerHand("2H 3H 4H 5H 6H");
    PokerHand opponentHand = new PokerHand("KS AS TS QS JS");
    System.out.println(myHand.getHandMap());
    System.out.println(opponentHand.getHandMap());
    System.out.println(myHand.getRank());
    System.out.println(opponentHand.getRank());
    System.out.println(myHand.compareWith(opponentHand));
  }

  public PokerHand(String hand) {
    this.handList = createHandList(hand);
    this.handMap = createHandMap();
    fillCheckerRank();
    fillCheckerEqualsRank();
  }

  public Result compareWith(PokerHand hand) {
    int myRank = getRank();
    int opponentRank = hand.getRank();
    if (myRank - opponentRank > 0) return Result.WIN;
    if (myRank - opponentRank < 0) return Result.LOSS;
    int check = checkerEqualsRank.get(myRank).apply(hand);
    return check >= 0 ? check == 0 ? Result.TIE : Result.WIN : Result.LOSS;
  }

  public int getRank() {
    for (int i = checkerRank.size() - 1; i >= 0; i--) {
      if (checkerRank.get(i).get()) return i + 1;
    }
    return 0;
  }

  // Check for combination
  private void fillCheckerRank() {
    checkerRank.add(this::checkPair);
    checkerRank.add(this::checkTwoPair);
    checkerRank.add(this::checkThreeOfKind);
    checkerRank.add(this::checkStraight);
    checkerRank.add(this::checkFlush);
    checkerRank.add(this::checkFullHouse);
    checkerRank.add(this::checkFourOfKind);
    checkerRank.add(this::checkStraightFlush);
  }

  public boolean checkStraightFlush() {
    return checkFlush() && checkStraight();
  }

  public boolean checkFourOfKind() {
    return handMap.containsValue(4);
  }

  public boolean checkFullHouse() {
    return handMap.containsValue(3) && handMap.containsValue(2);
  }

  public boolean checkFlush() {
    return handList.stream()
            .collect(Collectors.groupingBy(Card::getSuit, Collectors.summingInt(s -> 1)))
            .size()
            == 1;
  }

  public boolean checkStraight() {
    if (getLast() == 14 && getFirst() == 2) {
      return IntStream.range(0, 3)
              .noneMatch(i -> handList.get(i).getOrder() != handList.get(i + 1).getOrder() - 1);
    } else {
      return IntStream.range(0, 4)
              .noneMatch(i -> handList.get(i).getOrder() != handList.get(i + 1).getOrder() - 1);
    }
  }

  public boolean checkThreeOfKind() {
    return handMap.containsValue(3);
  }

  public boolean checkTwoPair() {
    return handMap.keySet().size() == 3 && handMap.containsValue(2);
  }

  public boolean checkPair() {
    return handMap.keySet().size() == 4 && handMap.containsValue(2);
  }

  // Block check equals Rank
  private void fillCheckerEqualsRank() {
    checkerEqualsRank.put(8, this::checkStraightHighest);
    checkerEqualsRank.put(7, this::checkFourOfKindHighest);
    checkerEqualsRank.put(6, this::checkFullHouseHighest);
    checkerEqualsRank.put(5, this::checkHighestCard);
    checkerEqualsRank.put(4, this::checkStraightHighest);
    checkerEqualsRank.put(3, this::checkThreeOfKindHighest);
    checkerEqualsRank.put(2, this::checkTwoPairHighest);
    checkerEqualsRank.put(1, this::checkPairHighest);
    checkerEqualsRank.put(0, this::checkHighestCard);
  }

  public int checkStraightHighest(PokerHand hand) {
    int myFirst = getFirst();
    int opponentFirst = hand.getFirst();
    int myLast = getLast();
    int opponentLast = hand.getLast();
    return myFirst == 2 && opponentFirst == 2
            ? Integer.compare(opponentLast, myLast)
            : Integer.compare(myFirst, opponentFirst);
  }

  public int checkFourOfKindHighest(PokerHand hand) {
    int myCard = getCardByValue(4);
    int opponentCard = hand.getCardByValue(4);
    if (myCard == opponentCard) {
      myCard = getCardByValue(1);
      opponentCard = hand.getCardByValue(1);
    }
    return Integer.compare(myCard, opponentCard);
  }

  public int checkFullHouseHighest(PokerHand hand) {
    int myCard = getCardByValue(3);
    int opponentCard = hand.getCardByValue(3);

    if (myCard == opponentCard) {
      myCard = getCardByValue(2);
      opponentCard = hand.getCardByValue(2);
    }
    return Integer.compare(myCard, opponentCard);
  }

  public int checkThreeOfKindHighest(PokerHand hand) {
    int myCard = getCardByValue(3);
    int opponentCard = hand.getCardByValue(3);
    if (myCard == opponentCard) {
      myCard = getCardByValue(1, true);
      opponentCard = hand.getCardByValue(1, true);
      if (myCard == opponentCard) {
        myCard = getCardByValue(1, false);
        opponentCard = hand.getCardByValue(1, false);
      }
    }
    return Integer.compare(myCard, opponentCard);
  }

  public int checkTwoPairHighest(PokerHand hand) {
    int myCard = getCardByValue(2, true);
    int opponentCard = hand.getCardByValue(2, true);
    if (myCard == opponentCard) {
      myCard = getCardByValue(2, false);
      opponentCard = hand.getCardByValue(2, false);
      if (myCard == opponentCard) {
        myCard = getCardByValue(1);
        opponentCard = hand.getCardByValue(1);
      }
    }
    return Integer.compare(myCard, opponentCard);
  }

  public int checkPairHighest(PokerHand hand) {
    int myCard = getCardByValue(2);
    int opponentCard = hand.getCardByValue(2);
    return myCard == opponentCard
            ? checkHighestCardPair(hand)
            : Integer.compare(myCard, opponentCard);
  }

  public int checkHighestCardPair(PokerHand hand) {
    var myShortList =
            handMap.entrySet().stream()
                    .filter(kv -> kv.getValue() == 1)
                    .map(Map.Entry::getKey)
                    .sorted().toList();
    var opponentShortList =
            hand.getHandMap().entrySet().stream()
                    .filter(kv -> kv.getValue() == 1)
                    .map(Map.Entry::getKey)
                    .sorted().toList();
    for (int i = myShortList.size() - 1; i >= 0; i--) {
      int myCard = myShortList.get(i);
      int opponentCard = opponentShortList.get(i);
      if (myCard != opponentCard) return Integer.compare(myCard, opponentCard);
    }
    return 0;
  }

  public int checkHighestCard(PokerHand hand) {
    for (int i = 4; i >= 0; i--) {
      int myCard = handList.get(i).order;
      int opponentCard = hand.getHandList().get(i).getOrder();
      if (myCard != opponentCard) return Integer.compare(myCard, opponentCard);
    }
    return 0;
  }

  private int getCardByValue(int value) {
    return handMap.entrySet().stream()
            .filter(kv -> kv.getValue() == value)
            .mapToInt(Map.Entry::getKey)
            .sum();
  }

  private int getCardByValue(int value, boolean max) {
    var streamKey =
            handMap.entrySet().stream()
                    .filter(kv -> kv.getValue() == value)
                    .mapToInt(Map.Entry::getKey);
    return max ? streamKey.max().orElseThrow() : streamKey.min().orElseThrow();
  }

  private List<Card> createHandList(String hand) {
    return Arrays.stream(hand.split(" ")).map(Card::new).sorted().collect(Collectors.toList());
  }

  private Map<Integer, Integer> createHandMap() {
    return handList.stream()
            .collect(Collectors.groupingBy(Card::getOrder, Collectors.summingInt(c -> 1)));
  }

  public Map<Integer, Integer> getHandMap() {
    return handMap;
  }

  public List<Card> getHandList() {
    return handList;
  }

  public int getFirst() {
    return handList.get(0).order;
  }

  public int getLast() {
    return handList.get(4).order;
  }

  private static class Card implements Comparable<Card> {
    int order;
    String suit;
    String card;

    public Card(String card) {
      this.card = card;
      this.order = parseOrder(card);
      this.suit = card.split("")[1];
    }

    private static int parseOrder(String card) {
      String order = card.split("")[0];
      if (order.matches("\\d")) {
        return Integer.parseInt(order);
      } else {
        return switch (order) {
          case "T" -> 10;
          case "J" -> 11;
          case "Q" -> 12;
          case "K" -> 13;
          case "A" -> 14;
          default -> throw new IllegalStateException("Unexpected value: " + order);
        };
      }
    }

    public String getSuit() {
      return suit;
    }

    public int getOrder() {
      return order;
    }

    @Override
    public int compareTo(Card o) {
      return Integer.compare(order, o.order);
    }

    @Override
    public String toString() {
      return card;
    }
  }

  public enum Result {
    TIE,
    WIN,
    LOSS
  }
}
