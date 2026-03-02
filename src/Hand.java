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
        String str = "";
        int cardsSize = cards.size();
        for (int i = 1; i <= cardsSize; i++) {
            str += String.format("%3d   ", i);
        }
        str += "\n";
        for (Card card : cards) {
            String up = String.format("⌜%-3s⌝", card.getRankStr());
            str += String.format("%-6s", up);
        }
        str += "\n";
        for (Card card : cards) {
            String middle = String.format("  %s  ", card.getSuitStr());
            str += String.format("%-6s", middle);
        }
        str += "\n";
        for (Card card : cards) {
            String lower = String.format("⌞%3s⌟", card.getRankStr());
            str += String.format("%-6s", lower);
        }
        return str;
    }
}
