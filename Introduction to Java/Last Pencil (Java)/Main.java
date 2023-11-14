package lastpencil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int numPencils;

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();

        // Get number of pencils from the user
        numPencils = getNumberOfStartingPencils();

        // Initialize players
        Player John = new Player("John");
        Player Jack = new Player("Jack");
        players.add(John);
        players.add(Jack);

        // Determine first player
        Player currentPlayer = determineFirstPlayer(players);

        // Play game
        Player winner = new Player("");
        while (numPencils > 0) {
            int numPencilsToRemove;

            // Display pencils remaining
            displayPencils(numPencils);

            // Perform turn
            if (currentPlayer == John) performPlayerTurn();
            else performBotTurn();

            // Update turn
            if (currentPlayer == players.get(0)) currentPlayer = players.get(1);
            else currentPlayer = players.get(0);

            // Winner?
            if(numPencils == 0) winner = currentPlayer;
        }

        System.out.println(winner.getName() + " won!");
    }

    public static void performBotTurn() {
        int numToRemove = 0;

        System.out.println("Jack's turn:");

        if (numPencils % 4 == 0) numToRemove = 3;
        else if ((numPencils + 1) % 4 == 0) numToRemove = 2;
        else if ((numPencils + 2) % 4 == 0) numToRemove = 1;
        else if (numPencils == 1) numToRemove = 1;
        else {
            final int MIN = 1;
            final int MAX = 3;
            Random random = new Random();
            numToRemove = random.nextInt(MAX - MIN + 1) + MIN;
        }

        System.out.println(numToRemove);
        numPencils -= numToRemove;
    }

    public static void displayPencils(int numPencils) {
        for (int i = 0; i < numPencils; i++) System.out.print("|");
        System.out.println();
    }

    public static void performPlayerTurn() {
        Scanner scanner = new Scanner(System.in);
        String[] possibleValues = {"1", "2", "3"};

        // Display turn
        System.out.println("John's turn!");

        // Get input
        String input = scanner.nextLine();

        // Validate input
        boolean isValid = false;
        boolean isPossibleValue = false;
        int numToRemove = 0;
        while (!isValid) {
            for (String string : possibleValues) {
                // Possible value?
                if (input.equals(string)) {
                    numToRemove = Integer.parseInt(input);
                    isPossibleValue = true;

                    // Too many pencils?
                    if (numToRemove <= numPencils) isValid = true;
                    else System.out.println("Too many pencils were taken");
                    break;
                }
            }

            if (!isPossibleValue) System.out.println("Possible values: '1', '2' or '3'");

            if (!isValid) input = scanner.nextLine();
        }

        // Update remaining pencil count
        numPencils -= numToRemove;
        scanner.close();
    }

    public static int getNumberOfStartingPencils() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isValid = false;
        int numPencils = 0;

        System.out.println("How many pencils would you like to use:");
        while (!isValid) {
            // Get input
            input = scanner.nextLine();

            // Validate input
            if (isNumeric(input) && !input.isBlank()) {
                numPencils = Integer.parseInt(input);

                if (numPencils > 0) isValid = true;
                else System.out.println("The number of pencils should be positive");
            }
            else System.out.println("The number of pencils should be numeric");
        }

        scanner.close();
        return numPencils;
    }

    public static boolean isNumeric(String string) {
        try {
            int x = Integer.parseInt(string);
            return x >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Player determineFirstPlayer(List<Player> players) {
        Scanner scanner = new Scanner(System.in);
        Player firstPlayer = new Player("");

        // Ask to pick player
        System.out.print("Who will be the first (");
        for (int i = 0; i < players.size() - 1; i++) {
            System.out.print(players.get(i).getName() + ", ");
        }
        System.out.println(players.get(players.size() - 1).getName() + "):");

        // Validate input
        String input = scanner.next();
        boolean isValid = false;
        while (!isValid) {
            for (Player player : players) {
                if(input.equals(player.getName())) {
                    firstPlayer = player;
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                System.out.print("Choose between ");
                for (int i = 0; i < players.size()  - 1; i++) {
                    System.out.print("'" + players.get(i).getName() + "' ");
                }
                System.out.println("and '" + players.get(players.size() - 1).getName() + "'");
                input = scanner.next();
            }
        }

        scanner.close();
        return firstPlayer;
    }
}


