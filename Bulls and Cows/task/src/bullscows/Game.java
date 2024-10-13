package bullscows;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final int lengthOfCode;
    private final int possibleSymbolsRange;

    private String secretCode;
    private int numberMax = 9;
    private int alphabetMax = 26;

    public Game(int lengthOfCode, int possibleSymbolsRange) {
        this.possibleSymbolsRange = possibleSymbolsRange;
        this.lengthOfCode = lengthOfCode;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int currentTurn = 1;

        String possibleRange = generatePossibleRange();
        this.secretCode = generateSecretCode();
        String asterisk = new String(new char[secretCode.length()]).replace('\0', '*');
        System.out.printf("The secret is prepared: %s %s.%n", asterisk, possibleRange);
        System.out.println("Okay, let's start the game!");
        while (true) {
            System.out.println("Turn " + currentTurn + ": ");
            String input = scanner.nextLine();
            processInput(input);
            currentTurn++;
        }
    }

    private String generatePossibleRange() {
        StringBuilder result = new StringBuilder();
        int rest = possibleSymbolsRange - 10;
        if (rest > 0) {
            result.append("(0-").append(numberMax);
            alphabetMax = rest - 1;
            result.append(", ").append("a-").append((char) ('a' + alphabetMax));
        } else {
            numberMax += rest;
            alphabetMax = 0;
            result.append("(0-").append(numberMax);
        }
        result.append(")");
        return result.toString();
    }

    private void processInput(String input) {
        int bulls = countBulls(input);
        int cows = countCows(input);
        result(bulls, cows);
    }

    private String generateSecretCode() {
        List<String> symbolsList = new ArrayList<>();
        Random random = new Random();
        while (symbolsList.size() != lengthOfCode) {
            for (int i = 0; i < lengthOfCode; i++) {
                int number = random.nextInt(0, numberMax);

                char symbol = '\0';
                if (alphabetMax != 0) {
                    symbol = ((char) ('a' + random.nextInt(alphabetMax)));
                }

                if (random.nextBoolean() && alphabetMax != 0) {
                    if (symbolsList.contains(symbol + "")) {
                        continue;
                    }
                    symbolsList.add(symbol + "");
                } else {
                    if (symbolsList.contains(number + "")) {
                        continue;
                    }
                    symbolsList.add(number + "");
                }

                if (symbolsList.size() == lengthOfCode) {
                    break;
                }
            }
        }
        Collections.shuffle(symbolsList);
        return String.join("", symbolsList);
    }

    private void result(int bulls, int cows) {
        StringBuilder result = new StringBuilder();
        result.append("Grade: ");
        if (bulls == 0 && cows == 0) {
            result.append("None");
        } else {
            if (bulls > 0) {
                result.append(bulls).append(" bull(s)");
                if (cows > 0) {
                    result.append(" and ");
                }
            }
            if (cows > 0) {
                result.append(cows).append(" cow(s)");
            }
        }
        System.out.println(result);
        if (bulls == secretCode.length()) {
            System.out.println("Congratulations! You guessed the secret code.");
            System.exit(0);
        }
    }

    private int countBulls(String input) {
        int amount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == secretCode.charAt(i)) {
                amount++;
            }
        }
        return amount;
    }

    private int countCows(String input) {
        int amount = 0;
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (secretCode.contains(currentChar + "") && secretCode.indexOf(currentChar) != i) {
                amount++;
            }
        }
        return amount;
    }
}
