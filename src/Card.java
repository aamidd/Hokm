public class Card {
    private final int suit; // 0: Club, 1: Diamonds, 2: Hearts, 3: Spades
    private final int rank; // 2 to 14 (ace)

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }
}
