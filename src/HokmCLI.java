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
                    chooseTeams();
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

    public void chooseTeams() {
        System.out.println("(Separate teammates' numbers by space)");
        System.out.println("Example:");
        System.out.println("1 4");
        System.out.println("2 3");
        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.printf("%d: %s\n", i + 1, game.getUsers().get(i));
        }
        while (true) {
            String team1String = getInput("Team 1: ");
            String team2String = getInput("Team 2: ");
            if (!validateTeam(team1String) || !validateTeam(team2String)) {
                System.out.println("You should choose the number of the players in this form:\nnum num");
                continue;
            }
            User user1 = game.getUser(Character.getNumericValue(team1String.charAt(0)));
            User user2 = game.getUser(Character.getNumericValue(team1String.charAt(2)));
            User user3 = game.getUser(Character.getNumericValue(team2String.charAt(0)));
            User user4 = game.getUser(Character.getNumericValue(team2String.charAt(2)));
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
        return Character.isDigit(team.charAt(0)) && Character.isDigit(team.charAt(2));
    }
}
