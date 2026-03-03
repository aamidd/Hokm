import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards = new ArrayList<Card>();

    public Deck() {
        initialize();
    }

    private void initialize() {
        for (int i = 0; i <= 3; i++) {
            for (int j = 2; j <= 14; j++) {
                Card card = new Card(i, j);
                cards.add(card);
            }
        }
    }
}
