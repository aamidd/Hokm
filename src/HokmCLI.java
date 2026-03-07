import java.util.Scanner;

public class HokmCLI {
    public void run() {
        System.out.println("Welcome to Hokm!\n");

        Hokm game = new Hokm();
        for (int i = 1; i <= 4; i++) {
            String username = getInput(String.format("Player %d: ", i));
            User user = new User(username);
            game.addUser(user);
        }
    }

    public String getInput(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        return input.nextLine();
    }
}
