import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();

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

    public void shuffle() {
        Random rand = new Random();

        for (int i = 0; i < 52; i++) {
            int randInt = rand.nextInt(cards.size());
            Card tmp = cards.get(randInt);
            cards.set(randInt, cards.get(i));
            cards.set(i, tmp);
        }
    }

    public Card deal() {
        return cards.removeLast();
    }

    public ArrayList<Card> deal(int amount) {
        ArrayList<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            dealtCards.add(deal());
        }

        return dealtCards;
    }

    public Card peek() {
        return cards.getLast();
    }

    public Card peek(int nthIndex) {
        return cards.get(nthIndex);
    }

    public int find(String rank) {
        for (int i = 51; i >= 0; i--) {
            if (rank.equals(peek(i).getRankStr())) {
                return i;
            }
        }
        return -1;
    }

    public int find(String rank, int nth) {
        if (nth > 4 || nth < 1) {
            return -1;
        }
        int count = 0;
        for (int i = 51; i >= 0; i--) {
            if (rank.equals(peek(i).getRankStr())) {
                if (++count == nth) {
                    return i;
                }
            }
        }
        return -1;
    }
}
