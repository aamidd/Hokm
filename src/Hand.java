import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public void useCard(Card card) {
        cards.remove(card);
    }

    public void useCard(int cardNumber) {
        useCard(cards.get(cardNumber - 1));
    }

    public String toHorizontalString() {
        StringBuilder str = new StringBuilder();
        int cardsSize = cards.size();
        for (int i = 1; i <= cardsSize; i++) {
            str.append(String.format("%3d   ", i));
        }
        str.append("\n");
        for (Card card : cards) {
            String up = String.format("⌜%s%s⌝", card.getRankStr(), card.getRank() == 10 ? "¯" : "¯¯");
            str.append(String.format("%-6s", up));
        }
        str.append("\n");
        for (Card card : cards) {
            String middle = String.format("│ %s │", card.getSuitStr());
            str.append(String.format("%-6s", middle));
        }
        str.append("\n");
        for (Card card : cards) {
            String lower = String.format("⌞%s%s⌟", card.getRank() == 10 ? "_" : "__", card.getRankStr());
            str.append(String.format("%-6s", lower));
        }
        return str.toString();
    }
}
