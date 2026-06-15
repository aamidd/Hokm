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
}
