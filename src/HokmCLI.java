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
        gameLoop();
    }

    private void gameLoop() {
        showPlayersSitting();

        /*
            phase one:
            the Hakem should choose their Hokm and then the rest of the game continues.
         */
        game.dealFive();
        turn(game.getHakem().getUsername());
        System.out.println(game.getHakem());
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

        /*
            phase two:
            players play their cards and the game continues.
         */
        game.dealRest();
        int userIndex = 0;
        while (true) {
            if (game.isTableFull())
                game.purgeTable();
            User currentUser = game.getUser(userIndex);
            turn(currentUser.getUsername());
            System.out.println(game.getTable());
            System.out.println(centerName(userIndex));
            System.out.println(game.getHands().get(userIndex));
            System.out.printf("Team 1: %d\nTeam 2: %d\n", game.getScore(0), game.getScore(1));
            while (true) {
                String cardNumberStr = getInput("enter the card number: ");
                if (isNumber(cardNumberStr)) {
                    System.out.println("Please enter a valid card number.");
                    continue;
                }
                int cardNumber = Integer.parseInt(cardNumberStr);
                if (cardNumber < 1 || cardNumber > game.getHands().get(userIndex).getSize()) {
                    System.out.printf("Card number should be between 1 and %d\n", game.getHands().get(userIndex).getSize());
                    continue;
                }
                int status = game.playCard(userIndex, cardNumber - 1);
                if (status == 1) {
                    System.out.printf("You already have %s. so play it!\n", game.getTable().getZamineStr());
                    continue;
                } else if (status == 2) {
                    starterIndex += game.determineWinner();
                    starterIndex %= 4;
                    System.out.printf("%s took this hand.\n", game.getUser(starterIndex));
                    game.addScoreTo(userIndex % 2); // this way users 0 & 2 are in team 0 and users 1 & 3 are in team 1
                    userIndex = starterIndex - 1;
                }
                break;
            }
            int score0 = game.getScore(0);
            int score1 = game.getScore(1);
            if (score0 == 7 || score1 == 7) {
                int winnerTeam = score0 == 7 ? 0 : 1;
                System.out.printf("%s and %s won!\n", game.getUser(winnerTeam), game.getUser(winnerTeam + 2));
                break;
            }
            userIndex++;
            userIndex %= 4;
            getInput(String.format("(hit enter and hand the device to %s)", game.getUser(userIndex)));
            clearTerminal();
        }
    }

    public boolean isNumber(String str) {
        return !str.matches("\\d+");
    }

    public String centerName(int userIndex) {
        String username = game.getUser(userIndex).getUsername();
        String padding = " ".repeat(Math.max(0, (game.getHands().get(userIndex).getSize() * 6 - 1) / 2 - username.length() / 2));
        return padding + username;
    }

    // inform the user's turn and clear the screen
    private void turn(String username) {
        getInput(String.format("(%s's turn. hit enter to reveal cards)", username));
        clearTerminal();
    }

    private String getInput(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        return input.nextLine();
    }

    private void chooseTeamsManually() {
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

    private void chooseTeamsWithCards() {
        Deck deck = game.getDeck();
        int index = 51;
        int userIndex = 0;
        int hakemIndex = -1;
        while (true) {
            int wait = 300; // how many milliseconds to wait after each dealing
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
    }

    private void showPlayersSitting() {
        String topName = game.getUser(0).getUsername();
        String leftName = game.getUser(1).getUsername();
        String bottomName = game.getUser(2).getUsername();
        String rightName = game.getUser(3).getUsername();

        int leftUserLength = leftName.length();
        int maxMiddleLength = Math.max(topName.length(), bottomName.length());

        String indent = " ".repeat(leftUserLength + 5);
        String topPadding = " ".repeat((maxMiddleLength - topName.length()) / 2);
        String bottomPadding = " ".repeat((maxMiddleLength - bottomName.length()) / 2);

        System.out.println(indent + topPadding + topName);
        System.out.println(leftName + " ".repeat(5 + maxMiddleLength + 5) + rightName);
        System.out.println(indent + bottomPadding + bottomName);
    }

    private boolean validateTeamString(String team) {
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

    private void clearTerminal() {
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
