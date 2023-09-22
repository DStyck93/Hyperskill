package cinema;

public class Seat {
    private boolean isOpen;
    private final int price;
    private final int row;
    private final int number;
    public Seat(boolean isOpen, int price, int row, int number) {
        this.isOpen = isOpen;
        this.price = price;
        this.row = row;
        this.number = number;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getPrice() {
        return price;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
