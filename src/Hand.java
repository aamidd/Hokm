import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cards = new ArrayList<>();
    private int compactness = 0;

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public void useCard(Card card) {
        cards.remove(card);
    }

    public void useCard(int cardNumber) {
        useCard(cards.get(cardNumber));
    }

    public int playCard(int cardNumber, int tableSuit) { // by table, I mean zamine
        if (hasSuit(tableSuit) && getCard(cardNumber).getSuit() != tableSuit) {
            return 1;
        }
        useCard(cardNumber);
        return 0;
    }

    public boolean hasSuit(int suit) {
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                return true;
            }
        }
        return false;
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

    public String toSuperCompactString() {
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

    public String toCompactString() {
        StringBuilder str = new StringBuilder();
        int size = cards.size();

        for (int i = 1; i <= size; i++) {
            str.append(String.format(" %-2d", i));
        }
        str.append("\n");
        for (int i = 0; i < size; i++) {
            Card card = cards.get(i);
            if (i == size - 1) {
                str.append(card.toString(), 0, 5);
            } else {
                str.append(card.toString(), 0, 3);
            }
        }
        str.append("\n");
        for (int i = 0; i < size; i++) {
            Card card = cards.get(i);
            if (i == size - 1) {
                str.append(card.toString(), 6, 11);
            } else {
                str.append(card.toString(), 6, 9);
            }
        }

        str.append("\n");
        for (int i = 0; i < size; i++) {
            Card card = cards.get(i);
            if (i == size - 1) {
                str.append(card.toString(), 12, 17);
            } else {
                str.append("|__");
            }
        }

        return str.toString();
    }

    public int getCompactness() {
        return compactness;
    }

    public void setCompactness(int compactness) {
        this.compactness = compactness;
    }

    public void sortHand(int hokm) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = 0; j < cards.size() - 1 - i; j++) {
                int rank1 = cards.get(j).getRank();
                int suit1 = cards.get(j).getSuit();
                int rank2 = cards.get(j + 1).getRank();
                int suit2 = cards.get(j + 1).getSuit();

                if (hokm != -1) {
                    suit1 = (suit1 + (4 - hokm)) % 4;
                    suit2 = (suit2 + (4 - hokm)) % 4;
                }

                boolean swap = false;
                if (suit1 > suit2) {
                    swap = true;
                } else if (suit1 == suit2 && rank1 < rank2) {
                    swap = true;
                }

                if (swap) {
                    Card tmp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, tmp);
                }
            }
        }
    }

    public int getSize() {
        return cards.size();
    }

    @Override
    public String toString() {
        if (compactness == 0) {
            return toHorizontalString();
        }
        if (compactness == 1) {
            return toCompactString();
        }
        return toSuperCompactString();
    }
}
