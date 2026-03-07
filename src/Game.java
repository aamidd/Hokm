import java.util.ArrayList;

public class Game {
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Hand> hands = new ArrayList<>();
    private final Deck deck = new Deck();
    private int hokm = -1; // 0: Clubs, 1: Diamonds, 2: Hearts, 3: Spades

    public Game() {
        deck.shuffle();
    }

    public void addUser(User user) {
        if (users.size() == 4) {
            throw new UnsupportedOperationException("4 users already added");
        }
        users.add(user);
    }

    public void chooseTeams(Deck deck) {
        ArrayList<User> tmpArr = new ArrayList<>(users); // this is here to easily choose the second team
        int firstAce = 51 - deck.find("A");
        int secondAce = 51 - deck.find("A", 2);

        int hakem = firstAce % 4;
        // the second user has to be chosen from the remaining three users
        int teammate = (secondAce - firstAce - 1) % 3; // hakem's teammate
        teammate = (hakem + teammate + 1) % 4; // start from after the hakem

        if (!(hakem == 0 && teammate == 2)) {
            User[] teams = new User[4];
            teams[0] = users.get(hakem);
            tmpArr.remove(teams[0]);
            teams[2] = users.get(teammate);
            tmpArr.remove(teams[2]);

            teams[1] = tmpArr.getFirst();
            teams[3] = tmpArr.getLast();

            for (int i = 0; i < 4; i++) {
                users.set(i, teams[i]);
            }
        }
    }

    public void chooseTeams(User team1, User teammate1, User team2, User teammate2) {
        users.set(0, team1);
        users.set(2, teammate1);
        users.set(1, team2);
        users.set(3, teammate2);
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public ArrayList<User> getUsers() {
        return users;
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

    public String getHokmStr() {
        return Card.getSuitStr(getHokm());
    }

    public void dealFive() {
        for (int i = 0; i < 4; i++) {
            Hand hand = new Hand();
            hand.addCards(deck.deal(5));
            hand.sortHand(hokm);
            hands.add(hand);
        }
    }

    // deal the rest of the hand in the same style as IRL Hokm
    public void dealRest() {
        if (hands.isEmpty()) {
            throw new UnsupportedOperationException("Haven't dealt the first 5");
        }

        if (hokm == -1) {
            throw new UnsupportedOperationException("Haven't chosen the hokm");
        }

        for (int i = 0; i < 2; i++) {
            for (Hand hand : hands) {
                hand.addCards(deck.deal(4));
            }
        }

        for (Hand hand : hands) {
            hand.sortHand(hokm);
        }
    }

    public ArrayList<Hand> getHands() {
        return hands;
    }
}
