import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }

    public void useCard(Card card) {
        cards.remove(card);
    }

    public void useCard(int cardNumber) {
        useCard(cards.get(cardNumber - 1));
    }

    public String toHorizontalString() {
        StringBuilder str = new StringBuilder();
        for (int i = 1; i <= cards.size(); i++) {
            str.append(String.format("%3d   ", i));
        }
        str.append("\n");
        for (Card card : cards) {
            String top = card.toString().substring(0, 5);
            str.append(String.format("%-6s", top));
        }
        str.append("\n");
        for (Card card : cards) {
            String middle = card.toString().substring(6, 11);
            str.append(String.format("%-6s", middle));
        }
        str.append("\n");
        for (Card card : cards) {
            String bottom = card.toString().substring(12, 17);
            str.append(String.format("%-6s", bottom));
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
