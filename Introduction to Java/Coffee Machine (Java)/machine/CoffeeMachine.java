package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int dollars; // USD($) available
    private int water; // ml of available water
    private int milk; // ml of available milk
    private int coffeeBeans; // g of available coffee beans
    private int disposableCups; // Number of disposable cups remaining

    public CoffeeMachine(int dollars, int water, int milk, int coffeeBeans, int disposableCups) {
        this.dollars = dollars;
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.disposableCups = disposableCups;
    }

    public static void main(String[] args) {

        // Initialize coffee machine
        int dollars = 550, water = 400, milk = 540, coffeeBeans = 120, disposableCups = 9;
        CoffeeMachine machine = new CoffeeMachine(dollars, water, milk, coffeeBeans, disposableCups);

        String action = "";
        while (!action.equals("exit")) {

            // Get action
            action = getAction();

            // Perform action
            switch (action) {
                case "buy" -> machine.buy();
                case "fill" -> machine.fill();
                case "take" -> machine.take();
                case "remaining" -> machine.displayAvailableItems();
            }
        }
    }

    public void displayAvailableItems() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffeeBeans + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + dollars + " of money");
    }

    public static String getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWrite action (buy, fill, take, remaining, exit)");
        String action = scanner.nextLine();
        scanner.close();
        return action;
    }

    public void buy() {
        Scanner scanner = new Scanner(System.in);
        Coffee coffee = null;

        // Get type of coffee
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String input = scanner.nextLine();
        scanner.close();
        if(!input.equals("back")) {
            for (Coffee c : Coffee.values()) {
                if (c.NUMBER == Integer.parseInt(input)) coffee = c;
            }

            // Buy coffee
            if (coffee != null && canMake(coffee)) {
                System.out.println("I have enough resources, making you a coffee!");
                water -= coffee.WATER;
                milk -= coffee.MILK;
                coffeeBeans -= coffee.COFFEE_BEANS;
                disposableCups--;
                dollars += coffee.COST;
            }
        }
    }

    public void take() {
        // Take all money from machine
        System.out.println("\nI gave you $" + dollars);
        dollars = 0;
    }

    public void fill() {
        Scanner scanner = new Scanner(System.in);

        // Water
        System.out.println("Write how many ml of water you want to add:");
        water += scanner.nextInt();

        // Milk
        System.out.println("Write how many ml of milk you want to add:");
        milk += scanner.nextInt();

        // Coffee Beans
        System.out.println("Write how many grams of coffee beans you want to add:");
        coffeeBeans += scanner.nextInt();

        // Disposable cups
        System.out.println("Write how many disposable cups you want to add:");
        disposableCups += scanner.nextInt();
        scanner.close();
    }

    public boolean canMake(Coffee coffee) {
        if (disposableCups == 0) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }
        else if (coffee.WATER > water) {
            System.out.println("Sorry, not enough water!");
            return false;
        }
        else if (coffee.MILK > milk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }
        else if (coffee.COFFEE_BEANS > coffeeBeans) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }

        return true;
    }
}

