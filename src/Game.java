import java.util.ArrayList;

public class Game {
    private ArrayList<User> users = new ArrayList<>();

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

    public User getUser(int index) {
        return users.get(index);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
