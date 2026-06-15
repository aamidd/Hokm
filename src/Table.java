import java.util.ArrayList;

public class Table {
    private final ArrayList<Card> cards = new ArrayList<>(); // played cards
    private int hokm = -1;

    public void addCard(Card card) {
        if (cards.size() >= 4) {
            throw new IllegalStateException("Table is full. max cards on the table is 4.");
        } else {
            cards.add(card);
        }
    }

    public void setHokm(int hokm) {
        if (hokm > 3 || hokm < 0) {
            throw new IllegalArgumentException("Hokm should be between 0-3");
        }
        this.hokm = hokm;
    }

    public int getHokm() {
        return hokm;
    }

    public int getZamine() {
        if (!cards.isEmpty()) {
            return cards.getFirst().getSuit();
        }
        return -1;
    }

    public int determineWinner() {
        int winner = 0;
        for (int i = 1; i < cards.size(); i++) {
            Card card1 = cards.get(winner);
            Card card2 = cards.get(i);
            if (card2.getSuit() != getZamine()) {
                if (card2.getSuit() != getHokm())
                    continue;
                if (card1.getSuit() == getHokm()) {
                    winner = card2.getRank() > card1.getRank() ? i : winner;
                }
            } else if (card1.getSuit() == getZamine()) {
                    winner = card2.getRank() > card1.getRank() ? i : winner;
            }
        }
        return winner;
    }
}
