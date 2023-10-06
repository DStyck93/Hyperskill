package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static boolean has2Nums;
    private static boolean hasPropertyFilter;
    private static long[] requestNums = new long[2];
    private static List<Property> requestedProperties;
    private static List<Property> requestedConstraints;
    private static Property[] mutuallyExclusiveProperties = new Property[2];

    public static void main(String[] args) {
        // 1. Welcome users
        System.out.println("Welcome to Amazing Numbers!");

        // 2. Display instructions
        printInstructions();

        // 3. Get request
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter a request:");
        String request = scanner.nextLine().trim();
        processRequest(request);

        // 5. If the user enters zero, terminate the program
        while (!request.equals("0")) {

            // 4. If a user enters an empty request, print the instructions
            if (request.trim().isEmpty()) {
                printInstructions();
            }

            else {
                // 6. check for natural numbers
                if (!isNaturalNum(requestNums[0])) {
                    System.out.println("The first parameter should be a natural number or zero");
                }
                else if (has2Nums && !isNaturalNum(requestNums[1])){
                    System.out.println("The second parameter should be a natural number.");
                }

                else {

                    // 8. If 1 number is entered, calculate and print properties
                    if(!has2Nums) printProperties(requestNums[0]);

                    // 9. For 2 numbers, print the list of numbers with properties
                    else if(!hasPropertyFilter) printProperties(requestNums[0], requestNums[1]);

                    // 10. For 2 numbers and property filter, print
                    else {

                        // 7. Validate properties
                        boolean valid = true;
                        String[] requests = request.split(" ");
                        for (int i = 2; i < requests.length; i++) {
                            if (requests[i].charAt(0) == '-' && !isProperty(requests[i].substring(1))) {
                                System.out.println("The property [" + requests[i].substring(1).toUpperCase() +
                                        "] is wrong.");
                                displayProperties();
                                valid = false;
                                break;
                            }
                            else if (requests[i].charAt(0) != '-' && !isProperty(requests[i])) {
                                System.out.println("The property [" + requests[i].toUpperCase() + "] is wrong.");
                                displayProperties();
                                valid = false;
                                break;
                            }
                        }

                        if (valid) {
                            // 12. If a user specifies mutually exclusive properties, abort the request and warn user
                            if (isMutuallyExclusive()) {
                                System.out.println("The request contains mutually exclusive properties: [" +
                                        mutuallyExclusiveProperties[0].name() + ", " +
                                        mutuallyExclusiveProperties[1].name() + "]");
                                System.out.println("There are no numbers with these properties");
                            }

                            else printPropertiesWithFilter(requestNums[0], requestNums[1]);
                        }
                    }
                }
            }
            // 13. Once the request is processed, continue execution from step 3;
            System.out.println("\nEnter a request:");
            request = scanner.nextLine().trim();
            processRequest(request);
        }
        // End program
        System.out.println("Goodbye!");
    }

    private static boolean isValidProperties(String request) {
        String[] requests = request.split(" ");
        for (int i = 2; i < requests.length; i++) {
            if (!isProperty(requests[i].toUpperCase())) return false;
        }
        return true;
    }

    private static void processRequest(String request) {
        has2Nums = false;
        hasPropertyFilter = false;
        requestedProperties = new ArrayList<>();
        requestedConstraints = new ArrayList<>();

        // If request has more than 1 input
        if (request.contains(" ")) {
            has2Nums = true;
            String[] requests = request.split(" ");
            requestNums[0] = Long.parseLong(requests[0]);
            requestNums[1] = Long.parseLong(requests[1]);

            // If request has properties
            if (requests.length >= 3) {
                hasPropertyFilter = true;
                // For each property
                for (int i = 2; i < requests.length; i++) {
                    // 11. Property constraints
                    if (requests[i].charAt(0) == '-' && isProperty(requests[i].substring(1))) {
                        requestedConstraints.add(assignProperty(requests[i].substring(1)));
                    }
                    // Properties to look for
                    else if (isProperty(requests[i])) {
                        requestedProperties.add(assignProperty(requests[i]));
                    }
                }
            }
        }

        // Single num request
        else requestNums[0] = Long.parseLong(request);
    }

    private static void displayProperties() {
        System.out.print("Available properties: [");
        for (int i = 0; i < Property.values().length - 1; i++) System.out.print(Property.values()[i] + ", ");
        System.out.println(Property.values()[Property.values().length - 1] + "]");
    }

    // EVEN & ODD
    // DUCK & SPY
    // SUNNY & SQUARE
    // HAPPY & SAD
    private static boolean isMutuallyExclusive() {
        if (requestedProperties.contains(Property.EVEN) && requestedProperties.contains(Property.ODD)) {
            mutuallyExclusiveProperties[0] = Property.EVEN;
            mutuallyExclusiveProperties[1] = Property.ODD;
            return true;
        }
        if (requestedProperties.contains(Property.DUCK) && requestedProperties.contains(Property.SPY)) {
            mutuallyExclusiveProperties[0] = Property.DUCK;
            mutuallyExclusiveProperties[1] = Property.SPY;
            return true;
        }
        if (requestedProperties.contains(Property.SUNNY) && requestedProperties.contains(Property.SQUARE)) {
            mutuallyExclusiveProperties[0] = Property.SUNNY;
            mutuallyExclusiveProperties[1] = Property.SQUARE;
            return true;
        }
        if (requestedProperties.contains(Property.HAPPY) && requestedProperties.contains(Property.SAD)) {
            mutuallyExclusiveProperties[0] = Property.HAPPY;
            mutuallyExclusiveProperties[1] = Property.SAD;
            return true;
        }

        if (requestedConstraints.contains(Property.EVEN) && requestedConstraints.contains(Property.ODD)) {
            mutuallyExclusiveProperties[0] = Property.EVEN;
            mutuallyExclusiveProperties[1] = Property.ODD;
            return true;
        }
        if (requestedConstraints.contains(Property.DUCK) && requestedConstraints.contains(Property.SPY)) {
            mutuallyExclusiveProperties[0] = Property.DUCK;
            mutuallyExclusiveProperties[1] = Property.SPY;
            return true;
        }
        if (requestedConstraints.contains(Property.SUNNY) && requestedConstraints.contains(Property.SQUARE)) {
            mutuallyExclusiveProperties[0] = Property.SUNNY;
            mutuallyExclusiveProperties[1] = Property.SQUARE;
            return true;
        }
        if (requestedConstraints.contains(Property.HAPPY) && requestedConstraints.contains(Property.SAD)) {
            mutuallyExclusiveProperties[0] = Property.HAPPY;
            mutuallyExclusiveProperties[1] = Property.SAD;
            return true;
        }

        for (Property p : Property.values()) {
            if (requestedProperties.contains(p) && requestedConstraints.contains(p)) {
                mutuallyExclusiveProperties[0] = p;
                mutuallyExclusiveProperties[1] = p;
                return true;
            }
        }

        return false;
    }

    private static Property assignProperty(String string) {
        for (Property p : Property.values()) {
            if(string.toUpperCase().equals(p.name())) return p;
        }
        return null;
    }

    private enum Property {
        EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD;
    }

    private static boolean isProperty(String string) {
        for (Property p : Property.values()) {
            if(string.toUpperCase().equals(p.name())) return true;
        }
        return false;
    }

    private static boolean isHappy(long num) {
        List<Integer> digits = new ArrayList<>();
        List<Long> sums = new ArrayList<>();
        String string = Long.toString(num);
        long sum = 0;
        while (sum != num) {
            sum = 0;
            for (int i = 0; i < string.length(); i++) {
                digits.add((Character.getNumericValue(string.charAt(i))));
            }
            for (int x : digits) {
                sum += (long)Math.pow(x, 2);
            }
            if(sum == 1) return true;
            for(Long x : sums) {
                if(sum == x) return false;
            }
            sums.add(sum);
            string = Long.toString(sum);
            digits.clear();
        }
        return false;
    }

    private static boolean isSunny(long num) {
        double x = Math.sqrt(++num);
        return x == (int)x;
    }

    private static boolean isJumping(long num) {
        String x = String.valueOf(num);
        if (x.length() == 1) return true;
        for (int i = 0; i < x.length() - 1; i++) {
            if (Math.abs(x.charAt(i) - x.charAt(i + 1)) != 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSquare(long num) {
        double x = Math.sqrt(num);
        return x == (int)x;
    }

    private static boolean isSpy(long num) {
        String str = String.valueOf(num);
        long sum = 0, product = 1;
        for (int i = 0; i < str.length(); i++) {
            long x = Character.getNumericValue(str.charAt(i));
            sum += x;
            product *= x;
        }
        return sum == product;
    }

    private static void printInstructions() {
        System.out.println("""

                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.""");
    }

    private static boolean isGapful(long num) {
        String str = Long.toString(num);
        if (str.length() < 3) return false;
        else {
            StringBuilder firstLastSB = new StringBuilder();
            firstLastSB.append(str.charAt(0));
            firstLastSB.append(str.charAt(str.length() - 1));
            int firstLastInt = Integer.parseInt(firstLastSB.toString());
            return num % firstLastInt == 0;
        }
    }

    private static List<String> getProperties(long num) {
        List<String> properties = new ArrayList<>();
        for (Property p : Property.values()) {
            if(isThisProperty(num, p)) properties.add(p.name().toLowerCase());
        }
        return properties;
    }

    private static boolean isThisProperty(long num, Property property) {
        return switch (property) {
            case EVEN -> isEven(num);
            case ODD -> !isEven(num);
            case BUZZ -> isBuzzNum(num);
            case DUCK -> isDuckNum(num);
            case PALINDROMIC -> isPalindromic(num);
            case GAPFUL -> isGapful(num);
            case SPY -> isSpy(num);
            case SQUARE -> isSquare(num);
            case SUNNY -> isSunny(num);
            case JUMPING -> isJumping(num);
            case HAPPY -> isHappy(num);
            case SAD -> !isHappy(num);
        };
    }

    private static void printPropertiesWithFilter(long num1, long num2) {
        int count = 0;
        for (long i = num1; count < num2; i++) {
            boolean isValid = true;
            if(!requestedProperties.isEmpty()) {
                for (Property p : requestedProperties) {
                    if (!isThisProperty(i, p)) isValid = false;
                }
            }
            if(!requestedConstraints.isEmpty()) {
                for (Property p : requestedConstraints) {
                    if (isThisProperty(i, p)) isValid = false;
                }
            }
            if (isValid) {
                count++;
                List<Property> properties = new ArrayList<>();
                for (Property p : Property.values()) {
                    if (isThisProperty(i, p)) {
                       properties.add(p);
                    }
                }
                System.out.print(i + " is ");
                for (int j = 0; j < properties.size() - 1; j++) {
                    System.out.print(properties.get(j) + ", ");
                }
                System.out.println(properties.get(properties.size() - 1));
            }
        }
    }

    private static void printProperties(long num1, long num2) {
        for (long i = num1; i < num1 + num2; i++) {
            System.out.print(i + " is ");
            List<String> properties = getProperties(i);
            for(int j = 0; j < properties.size() - 1; j++) {
                System.out.print(properties.get(j) + ", ");
            }
            System.out.println(properties.get(properties.size() - 1));
        }
    }

    private static void printProperties(long num) {
        System.out.println("Properties of " + num);
        System.out.println("       buzz: " + isBuzzNum(num));
        System.out.println("       duck: " + isDuckNum(num));
        System.out.println("palindromic: " + isPalindromic(num));
        System.out.println("     gapful: " + isGapful(num));
        System.out.println("        spy: " + isSpy(num));
        System.out.println("       even: " + isEven(num));
        System.out.println("        odd: " + !isEven(num));
        System.out.println("     square: " + isSquare(num));
        System.out.println("      sunny: " + isSunny(num));
        System.out.println("    jumping: " + isJumping(num));
        System.out.println("      happy: " + isHappy(num));
        System.out.println("        sad: " + !isHappy(num));
    }

    private static boolean isPalindromic(long num) {
        String str = Long.toString(num);
        StringBuilder backwards = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            backwards.append(str.charAt(i));
        }
        return backwards.toString().equals(str);
    }

    private static boolean isBuzzNum(long num) {
        return isDivisibleBy7(num) || endsWith7(num);
    }

    private static boolean isDuckNum(long num) {
        String str = Long.toString(num);
        return str.contains("0");
    }

    private static boolean isNaturalNum(long num) {
        return num > 0;
    }

    private static boolean isEven(long num) {
        return num % 2 == 0;
    }

    private static boolean isDivisibleBy7(long num) {
        return num % 7 == 0;
    }

    private static boolean endsWith7(long num) {
        return Long.toString(num).endsWith("7");
    }
}
