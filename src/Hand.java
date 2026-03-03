import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCards(ArrayList<Card> cards) {
        this.cards.addAll(cards);
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
            String up = String.format("|%s%s|", card.getRankStr(), card.getRank() == 10 ? "¯" : "¯¯");
            str.append(String.format("%-6s", up));
        }
        str.append("\n");
        for (Card card : cards) {
            String middle = String.format("│ %s │", card.getSuitStr());
            str.append(String.format("%-6s", middle));
        }
        str.append("\n");
        for (Card card : cards) {
            String lower = String.format("|%s%s|", card.getRank() == 10 ? "_" : "__", card.getRankStr());
            str.append(String.format("%-6s", lower));
        }
        return str.toString();
    }

    public String toDiagonalString() {
        StringBuilder str = new StringBuilder();
        int size = cards.size();
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j < i * 2; j++) {
                if (j == i * 2 - 4) {
                    str.append("|_");
                    j++;
                } else if (j == i * 2 - 2) {
                    str.append("│");
                    str.append(i == size ? " " : cards.get(i - 1).getSuitStr());
                    j++;
                } else {
                    str.append(" ");
                }
            }
            if (i == size) {
                str.append(String.format("%s │\n", cards.get(i - 1).getSuitStr()));
                str.append(" ".repeat(size * 2 - 2));
                str.append(String.format("|%s%s|\n", cards.get(i - 1).getRank() == 10 ? "_" : "__", cards.get(i - 1).getRankStr()));
                continue;
            }
            str.append(String.format("|%s%s|%5d", cards.get(i).getRankStr(), cards.get(i).getRank() == 10 ? "¯" : "¯¯", i + 1));
            str.append("\n");
        }
        return str.toString();
    }
}
