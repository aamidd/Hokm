public class Card {
    private final int rank; // 2 to 14 (ace)
    private final int suit; // 0: Clubs, 1: Diamonds, 2: Hearts, 3: Spades

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
            return switch (rank) {
                case "J" -> 11;
                case "Q" -> 12;
                case "K" -> 13;
                case "A" -> 14;
                default -> throw new IllegalArgumentException();
            };
        }
    }

    public int parseSuit(String suit) {
        return switch (suit.toLowerCase()) {
            case "clubs" -> 0;
            case "diamonds" -> 1;
            case "hearts" -> 2;
            case "spades" -> 3;
            default -> throw new IllegalArgumentException();
        };
    }

    public int getRank() {
        return rank;
    }

    public String getRankStr() {
        if (getRank() == 11)
            return "J";
        if (getRank() == 12)
            return "Q";
        if (getRank() == 13)
            return "K";
        if (getRank() == 14)
            return "A";

        return String.valueOf(getRank());
    }

    public int getSuit() {
        return suit;
    }

    public String getSuitStr() {
        if (getSuit() == 0)
            return "♣";
        if (getSuit() == 1)
            return "♦";
        if (getSuit() == 2)
            return "♥";
        return "♠";
    }

    @Override
    public String toString() {
        return String.format("⌜%-3s⌝\n  %s\n⌞%3s⌟", getRankStr(), getSuitStr(), getRankStr());
    }
}
