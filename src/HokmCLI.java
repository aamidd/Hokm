import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HokmCLI {
    private final Hokm game = new Hokm();

    public void run() {
        System.out.println("Welcome to Hokm!\n");

        for (int i = 1; i <= 4; i++) {
            String username = getInput(String.format("Player %d: ", i));
            User user = new User(username);
            game.addUser(user);
        }

        System.out.println("\nHow to choose teams:");
        System.out.println("1. Deal cards to choose teams");
        System.out.println("2. Choose your own teams");
        prompt:
        while (true) {
            String option = getInput("> ");
            switch (option) {
                case "1":
                    chooseTeamsWithCards();
                    break prompt;
                case "2":
                    chooseTeamsManually();
                    break prompt;
                default:
                    System.out.println("Choose between 1 and 2");
            }
        }

        getInput("(hit enter to continue)");
        clearTerminal();

        game.dealFive();
        System.out.println(game.getHands().getFirst());
        System.out.println("You're the hakem. choose your hokm.");
        System.out.println("1: ♣  2: ♦  3: ♥  4: ♠");
        while (true) {
            String hokmStr = getInput("> ");
            if (hokmStr.length() == 1) {
                if (Character.isDigit(hokmStr.charAt(0))) {
                    int hokm = Integer.parseInt(hokmStr);
                    if (hokm <= 4 && hokm >= 1) {
                        game.setHokm(hokm - 1);
                        break;
                    }
                }
            }
            System.out.println("Hokm should be a number between 1 and 4");
        }
    }

    public void turn(String username) {
        getInput(String.format("(%s's turn. hit enter to reveal cards)", username));
    }

    public String getInput(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        return input.nextLine();
    }

    public void chooseTeamsManually() {
        System.out.println("(Separate teammates' numbers by space)");
        System.out.println("Example:");
        System.out.println("1 4");
        System.out.println("2 3");
        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.printf("%d: %s\n", i + 1, game.getUsers().get(i).getUsername());
        }
        while (true) {
            String team1String = getInput("Team 1: ");
            String team2String = getInput("Team 2: ");
            if (!validateTeamString(team1String) || !validateTeamString(team2String)) {
                System.out.println("You should choose the number of the players in this form (num is from 1-4):\nnum num");
                continue;
            }
            int index1 = Character.getNumericValue(team1String.charAt(0)) - 1;
            int index2 = Character.getNumericValue(team1String.charAt(2)) - 1;
            int index3 = Character.getNumericValue(team2String.charAt(0)) - 1;
            int index4 = Character.getNumericValue(team2String.charAt(2)) - 1;

            if ((index1 ^ index2 ^ index3 ^ index4) != (0 ^ 1 ^ 2 ^ 3)) {
                System.out.println("Do not choose the same number more than once");
                continue;
            }

            User user1 = game.getUser(index1);
            User user2 = game.getUser(index2);
            User user3 = game.getUser(index3);
            User user4 = game.getUser(index4);
            game.chooseTeams(user1, user2, user3, user4);
            break;
        }
    }

    public void chooseTeamsWithCards() {
        Deck deck = game.getDeck();
        int index = 51;
        int userIndex = 0;
        int hakemIndex = -1;
        while (true) {
            int wait = 400; // how many milliseconds to wait after each dealing
            if (userIndex == hakemIndex) {
                userIndex = (userIndex + 1) % 4;
                continue;
            }
            System.out.printf("%s:\n%s\n", game.getUser(userIndex).getUsername(), deck.peek(index));
            if (deck.peek(index).getRank() == 14) {
                if (hakemIndex != -1)
                    break;
                hakemIndex = userIndex;
                System.out.println(game.getUser(hakemIndex).getUsername() + " is the Hakem!");
                wait = 2000;
            }
            index--;
            // wait for users to see
            try {
                TimeUnit.MILLISECONDS.sleep(wait);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            userIndex = (userIndex + 1) % 4;
        }
        game.chooseTeams();
        int leftUserLength = game.getUser(1).getUsername().length();
        int maxMiddleLength = Math.max(game.getUser(0).getUsername().length(), game.getUser(2).getUsername().length());

        System.out.println(" ".repeat(leftUserLength + 5) + " ".repeat((maxMiddleLength - game.getUser(0).getUsername().length()) / 2) + game.getUser(0).getUsername() + " ".repeat((maxMiddleLength - game.getUser(0).getUsername().length()) / 2));
        System.out.println(game.getUser(1).getUsername() + " ".repeat(5 + maxMiddleLength + 5) + game.getUsers().get(3).getUsername());
        System.out.println(" ".repeat(leftUserLength + 5) + " ".repeat((maxMiddleLength - game.getUser(2).getUsername().length()) / 2) + game.getUser(2).getUsername() + " ".repeat((maxMiddleLength - game.getUser(2).getUsername().length()) / 2));
    }

    public boolean validateTeamString(String team) {
        if (team.length() != 3) {
            return false;
        }
        if (team.charAt(1) != ' ') {
            return false;
        }
        if (!Character.isDigit(team.charAt(0)) || !Character.isDigit(team.charAt(2))) {
            return false;
        }
        return Character.getNumericValue(team.charAt(0)) <= 4 && Character.getNumericValue(team.charAt(2)) <= 4;
    }

    public void clearTerminal() {
        String term = System.getenv("TERM");
        if (term != null && !term.isEmpty()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}
