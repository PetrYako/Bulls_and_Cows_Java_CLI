package bullscows;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Input the length of the secret code:");
        Scanner scanner = new Scanner(System.in);
        String inputLength = scanner.nextLine();
        if (isNotNumber(inputLength)) {
            System.out.println("Error");
            System.exit(-1);
        }

        System.out.println("Input the number of possible symbols in the code:");
        int length = Integer.parseInt(inputLength);

        if (length == 0) {
            System.out.println("Error");
            System.exit(-1);
        }

        String inputSymbols = scanner.nextLine();

        if (isNotNumber(inputSymbols)) {
            System.out.println("Error");
            System.exit(-1);
        }

        int symbols = Integer.parseInt(inputSymbols);
        if (length > symbols || symbols == 0 || symbols > 36) {
            System.out.println("Error");
        } else {
            Game game = new Game(length, symbols);
            game.start();
        }
    }

    private static boolean isNotNumber(String input) {
        return !input.matches("\\d+");
    }
}
