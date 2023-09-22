package cinema;

public class ScreenRoom {
    private final Seat[][] seats;
    private final int numRows;
    private final int seatsPerRow;
    private int seatsFilled;
    private final int numSeats;
    private int currentIncome;
    private final int totalIncome;

    public ScreenRoom(Seat[][] seats, int numRows, int seatsPerRow) {
        this.seats = seats;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.numSeats = numRows * seatsPerRow;
        this.seatsFilled = 0;
        this.currentIncome = 0;
        this.totalIncome = calculateTotalIncome();
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public int getTotalIncome() {
        return totalIncome;
    }

    public int getSeatsFilled() {
        return seatsFilled;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public Seat[][] getSeats() {
        return seats;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void printSeatChart() {
        // Header
        System.out.println("Cinema:");

        // Seat Number
        System.out.print("  ");
        for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
            System.out.print(seatNum + " ");
        }
        System.out.println();

        // Rows
        for (int row = 1; row <= numRows; row++) {
            System.out.print(row + " ");
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                if (seats[row - 1][seatNum - 1].isOpen()) {
                    System.out.print("S ");
                } else {
                    System.out.print("B ");
                }
            }
            System.out.println();
        }
    }

    public void addGuest(int rowNum, int seatNum) {
        seatsFilled++;
        seats[rowNum - 1][seatNum - 1].setOpen(false);
        currentIncome += seats[rowNum - 1][seatNum - 1].getPrice();
    }

    private int calculateTotalIncome() {
        int totalIncome;
        // If the total number of seats is <= 60, ticket price = $10.
        // else, $10 front 1/2, $8 back 1/2. (for odd rows 2nd half is bigger)
        int numSeats = numRows * seatsPerRow;
        int ticketPrice; // $USD
        if (numSeats <= 60) {
            ticketPrice = 10;
            totalIncome = ticketPrice * numSeats;
        } else {
            int frontTicketPrice = 10;
            int backTicketPrice = 8;
            if (numRows % 2 == 0) {
                totalIncome = (numSeats / 2) * (frontTicketPrice + backTicketPrice);
            } else {
                int numFrontRows = numRows / 2;
                int numBackRows = numFrontRows + 1;
                totalIncome = seatsPerRow * (numFrontRows * frontTicketPrice + numBackRows * backTicketPrice);
            }
        }

        return totalIncome;
    }

    public void addCurrentIncome(int price) {
        currentIncome += price;
    }

    public void printStatistics() {
        System.out.println("Number of purchased tickets: " + seatsFilled);
        float percentPurchased = (float)seatsFilled / (float)numSeats * 100f;
        System.out.printf("Percentage: %.2f%%\n", percentPurchased);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}
