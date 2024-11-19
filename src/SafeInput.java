import java.util.Scanner;

public class SafeInput {

    public static String getString(Scanner console, String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    public static String getRegExString(Scanner console, String prompt, String regex) {
        String input;
        do {
            System.out.print(prompt);
            input = console.nextLine();
        } while (!input.matches(regex));
        return input;
    }

    public static boolean getYNConfirm(Scanner console, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = console.nextLine();
        } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
        return input.equalsIgnoreCase("y");
    }

    public static int getRangedInt(Scanner console, String prompt, int low, int high) {
        int input;
        do {
            System.out.print(prompt);
            input = Integer.parseInt(console.nextLine());
        } while (input < low || input > high);
        return input;
    }
}
