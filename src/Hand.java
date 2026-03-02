import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cardsInHand = new ArrayList<>();

    public void addCard(Card card) {
        cardsInHand.add(card);
    }

    public void useCard(Card card) {
        cardsInHand.remove(card);
    }

    public void useCard(int cardNumber) {
        useCard(cardsInHand.get(cardNumber - 1));
    }
}
