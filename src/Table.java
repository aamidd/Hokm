import java.util.ArrayList;

public class Table {
    private final ArrayList<Card> cards = new ArrayList<>(); // played cards
    private int hokm = -1;
    private int zamine = -1;

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

    public void setZamine(int zamine) {
        if (zamine > 3 || zamine < 0) {
            throw new IllegalArgumentException("Zamine should be between 0-3");
        }
        this.zamine = zamine;
    }
}
