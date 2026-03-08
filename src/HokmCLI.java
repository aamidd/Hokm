import java.util.Scanner;

public class HokmCLI {
    private final Hokm game = new Hokm();

    public void run() {
        System.out.println("Welcome to Hokm!\n");

        for (int i = 1; i <= 4; i++) {
            String username = getInput(String.format("Player %d: ", i));
            User user = new User(username);
            game.addUser(user);
        }

        System.out.println("1. Deal cards to choose teams");
        System.out.println("2. Choose your own teams");
        prompt:
        while (true) {
            String option = getInput("> ");
            switch (option) {
                case "1":
                    game.chooseTeams();
                    break prompt;
                case "2":
                    chooseTeamsManually();
                    break prompt;
                default:
                    System.out.println("Choose between 1 and 2");
            }
        }
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
            if (!validateTeam(team1String) || !validateTeam(team2String)) {
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

    public boolean validateTeam(String team) {
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
}
