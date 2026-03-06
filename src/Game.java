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
        // the second user has to be chosen from the remaining three users
        int secondUserIndex = secondAce % 3;
        secondUserIndex = (firstUserIndex + secondUserIndex + 1) % 4;

        if (!(firstUserIndex == 0 && secondUserIndex == 2)) {
            User tmp = users.get(firstUserIndex);
            users.set(firstUserIndex, users.getFirst());
            users.set(0, tmp);

            User tmp2 = users.get(secondUserIndex);
            users.set(secondUserIndex, users.get(2));
            users.set(2, tmp2);
        }
    }
}
