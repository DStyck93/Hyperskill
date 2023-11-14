package machine;

public enum Coffee {
    ESPRESSO(1, 4, 250, 0, 16),
    LATTE(2, 7, 350, 75, 20),
    CAPPUCCINO(3, 6, 200, 100, 12);

    public final int NUMBER;
    public final int COST;
    public final int WATER;
    public final int MILK;
    public final int COFFEE_BEANS;

    private Coffee(int number, int cost, int water, int milk, int coffeeBeans) {
        this.NUMBER = number;
        this.COST = cost;
        this.WATER = water;
        this.MILK = milk;
        this.COFFEE_BEANS = coffeeBeans;
    }
}
