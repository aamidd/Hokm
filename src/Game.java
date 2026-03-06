import java.util.ArrayList;

public class Game {
    ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void chooseTeams(Deck deck) {
        int firstAce = 51 - deck.find("A");
        int secondAce = 51 - deck.find("A", 2);

        int firstUserIndex = firstAce % 4;
        int secondUserIndex = secondAce % 4;

        User tmp = users.get(firstUserIndex);
        User tmp2 = users.get(secondUserIndex);

        users.set(firstUserIndex, users.getFirst());
        users.set(0, tmp);
        users.set(secondUserIndex, users.get(2));
        users.set(2, tmp2);
    }
}
