import java.util.Scanner;

public class HokmCLI {
    public void run() {


    }

    public String getInput(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt);
        return input.nextLine();
    }
}
