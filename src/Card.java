public class Card {
    private final int rank; // 2 to 14 (ace)
    private final int suit; // 0: Clubs, 1: Diamonds, 2: Hearts, 3: Spades

    public Card(int suit, int rank) {
        this.rank = rank;
        this.suit = suit;
    }

    public Card(String suitStr, String rankStr) {
        this.rank = parseRank(rankStr);
        this.suit = parseSuit(suitStr);
    }

    public static int parseRank(String rank) {
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

    public static int parseSuit(String suit) {
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

    public static String getRankStr(int rank) {
        if (rank == 11)
            return "J";
        if (rank == 12)
            return "Q";
        if (rank == 13)
            return "K";
        if (rank == 14)
            return "A";

        return String.valueOf(rank);
    }

    public String getRankStr() {
        return getRankStr(rank);
    }

    public int getSuit() {
        return suit;
    }

    public static String getSuitStr(int suit) {
        if (suit == 0)
            return "♣";
        if (suit == 1)
            return "♦";
        if (suit == 2)
            return "♥";
        return "♠";
    }

    public String getSuitStr() {
        return getSuitStr(getSuit());
    }

    @Override
    public String toString() {
        boolean isTen = getRank() == 10;
        String top = isTen ? "¯" : "¯¯";
        String bottom = isTen ? "_" : "__";
        return String.format("|%s%s|\n│ %s │\n|%s%s|", getRankStr(getRank()), top, getSuitStr(getSuit()), bottom, getRankStr(getRank()));
    }
}
