package bullscows;

import java.util.Scanner;

public class Main {
    private static boolean hasError = false;
    public static void main(String[] args) {

        // Get code length from user
        int codeSize = getCodeSize();

        int numCharsPossible = 0;
        if (!hasError) {
            numCharsPossible = getNumPossibleChars();
        }

        // Verify inputs
        if (!hasError && codeSize > numCharsPossible) {
            System.out.println("Error: it's not possible to generate a code with a length of " + codeSize +
                    " with " + numCharsPossible + " unique symbols.");
            hasError = true;
        }

        // Generate code
        char[] code;
        if (!hasError) {
            code = generateCode(codeSize, numCharsPossible);

            // Play game
            if (!hasError) {
                playGame(code, numCharsPossible);
            }
        }
    }

    private static int getCodeSize() {
        final int MAX_SIZE = 36;
        int codeSize = -1;

        // Get input
        System.out.println("Please, enter the secret code's length:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        boolean isValid = true;
        for (int i = 0; i < input.length(); i++) {
            if(!Character.isDigit(input.charAt(i))) {
                System.out.println("Error: \"" + input + "\" isn't a valid number.");
                hasError = true;
                isValid = false;
                break;
            }
        }

        if (isValid) {
            codeSize = Integer.parseInt(input);
            if (codeSize > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                hasError = true;
            } else if (codeSize <= 0) {
                System.out.println("Error: minimum value is 1.");
                hasError = true;
            }
            return codeSize;
        }

        else {
            return -1;
        }
    }

    private static int getNumPossibleChars(){
        final int MAX_SIZE = 36;

        // Get input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the number of possible symbols in the code:");
        int input = scanner.nextInt();
        scanner.close();

        // Validate size
        if (input > MAX_SIZE) {
            System.out.println("Error: Max input is" + MAX_SIZE + ". Please try again.");
            hasError = true;
        }

        return input;
    }

    private static void playGame(char[] code, int numCharsAvailable) {
        Scanner scanner = new Scanner(System.in);
        int turn = 1;
        char[] guess = new char[code.length];

        // Intro
        System.out.print("The secret is prepared: ");
        for(int i = 0; i < code.length; i++) {
            System.out.print("*");
        }
        System.out.println(" " + getPossibleValues(numCharsAvailable) + ".");
        System.out.println("Okay, let's start a game!");

        // Loop until proper guess is found
        boolean isCorrectGuess = false;
        while (!isCorrectGuess) {

            // Display turn
            System.out.println("Turn " + turn + ":");

            // Get input
            String input = scanner.nextLine();

            // Convert input to char array
            guess = input.toCharArray();

            // Determine if guess is correct
            isCorrectGuess = determineGuess(code, guess);

            // Display grade
            displayGrade(code, guess);

            // Advance turn
            turn++;
        }
        scanner.close();

        // Victory message
        System.out.println("Congratulations! You guessed the secret code.");
    }

    private static String getPossibleValues(int numCharsPossible) {
        String values = "(0-";
        if (numCharsPossible <= 10) {
            values += (numCharsPossible - 1);
        } else {
            values += "9, a-";
            values += (char)('a' + numCharsPossible - 11);
        }
        values += ")";
        return values;
    }

    private static boolean determineGuess(char[] code, char[] guess) {
        for (int i = 0; i < code.length; i++) {
            if (code[i] != guess[i]) {
                return false;
            }
        }
        return true;
    }

    private static void displayGrade(char[] code, char[] guess) {

        // Get count of bulls and cows
        int numBulls = getNumBulls(code, guess);
        int numCows = getNumCows(code, guess);

        // Output
        System.out.print("Grade: ");
        if (numBulls == 0 && numCows == 0) {
            System.out.println("None");
        }
        else if (numCows == 0) {
            System.out.println(numBulls + " bull(s)");
        }
        else if (numBulls == 0) {
            System.out.println(numCows + " cow(s)");
        }
        else {
            System.out.println(numBulls + " bull(s) and " + numCows + " cow(s)");
        }
    }

    private static int getNumBulls(char[] code, char[] guess) {
        int count = 0;
        for (int i = 0; i < code.length; i++) {
            if (code[i] == guess[i]) {
                count++;
            }
        }
        return count;
    }

    private static int getNumCows(char[] code, char[] guess) {
        int count = 0;
        for (int i = 0; i < code.length; i++) {
            for (int j = 0; j < code.length; j++) {
                if (code[i] == guess[j] && i != j) {
                    count++;
                }
            }
        }
        return count;
    }

    private static char[] generateCode(int codeSize, int numCharsPossible) {

        // Initialize code with proper size and set all values to 'Z'
        char[] code = new char[codeSize];
        for (char x : code) {
            x = 'Z';
        }

        // Create array of available chars
        char[] availableChars = createCharArray(numCharsPossible);

        // Get unique random character for each index of the array
        for (int i = 0; i < codeSize; i++) {
            char randomChar = 'Z';
            boolean isUnique = false;
            while (!isUnique) {
                isUnique = true;
                randomChar = getRandomChar(availableChars);
                for (int n : code) {
                    if (n == randomChar) {
                        isUnique = false;
                        break;
                    }
                }
            }
            code[i] = randomChar;
        }

        return code;
    }

    private static char[] createCharArray(int maxSize) {
        // Create array of available chars to choose from
        char[] arr = new char[maxSize];
        for (int i = 0; i < maxSize; i++) {
            if (i <= 9) {
                arr[i] = (char)('0' + i);
            }
            else {
                arr[i] = (char)('a' + (i - 10));
            }
        }
        return arr;
    }

    private static char getRandomChar(char[] availableChars) {
        int randomNum = (int)(Math.random() * (availableChars.length));
        return availableChars[randomNum];
    }
}
