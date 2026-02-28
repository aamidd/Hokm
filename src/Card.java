public class Card {
    private final int rank; // 2 to 14 (ace)
    private final int suit; // 0: Club, 1: Diamonds, 2: Hearts, 3: Spades

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }
    
    public Card(String rankStr, String suitStr) {
        this.rank = parseRank(rankStr);
        this.suit = parseSuit(suitStr);
    }

    public int parseRank(String rank) {
        try {
            return Integer.parseInt(rank);
        } catch (NumberFormatException e) {
            return switch (rank.toLowerCase()) {
                case "jack" -> 11;
                case "queen" -> 12;
                case "king" -> 13;
                case "ace"  -> 14;
                default -> throw new IllegalArgumentException();
            };
        }
    }

    public int parseSuit(String suit) {
        return switch (suit.toLowerCase()) {
            case "club" -> 0;
            case "diamonds" -> 1;
            case "hearts" -> 2;
            case "spades" -> 3;
            default -> throw new IllegalArgumentException();
        };
    }
}
