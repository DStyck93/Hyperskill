package cinema;

import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get inputs
        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRow = scanner.nextInt();

        // Create screen room
        Seat[][] seats = generateSeatMatrix(numRows, seatsPerRow);
        ScreenRoom screenRoom = new ScreenRoom(seats, numRows, seatsPerRow);

        // Display Options
        System.out.println();
        showOptions();

        // Get input
        int option = scanner.nextInt();
        System.out.println();

        // Run program
        while(option != 0) {
            switch (option) {
                case 1: // Show the seats
                    screenRoom.printSeatChart();
                    break;

                case 2: // Buy a ticket
                    buyTicket(screenRoom);
                    break;

                case 3: // Statistics
                    screenRoom.printStatistics();
                    break;
            }

            // Get next option
            System.out.println();
            showOptions();
            option = scanner.nextInt();
            System.out.println();
        }
    }

    private static void buyTicket(ScreenRoom screenRoom) {
        Scanner scanner2 = new Scanner(System.in);
        boolean isValid = false;
        int rowNum = -1;
        int seatNum = -1;
        while(!isValid) {
            // Get input
            System.out.println("Enter a row number:");
            rowNum = scanner2.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNum = scanner2.nextInt();

            // Check for valid input
            if(rowNum > screenRoom.getNumRows() || rowNum < 1 || seatNum > screenRoom.getSeatsPerRow() || seatNum < 1) {
                System.out.println("Wrong input!");
            } else {
                isValid = true;
            }

            if (isValid) {
                // Check if seat is available
                Seat seat = screenRoom.getSeats()[rowNum - 1][seatNum - 1];
                if(seat.isOpen()) {
                    screenRoom.addGuest(rowNum, seatNum);
                } else {
                    System.out.println("\nThat ticket has already been purchased!\n");
                    isValid = false;
                }
            }
        }

        System.out.println("Ticket price: $" + screenRoom.getSeats()[rowNum - 1][seatNum - 1].getPrice());
    }

    private static void showOptions() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private static Seat[][] generateSeatMatrix(int numRows, int seatsPerRow) {
        Seat[][] seats = new Seat[numRows][seatsPerRow];
        for (int row = 1; row <= numRows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                int ticketPrice = calculateTicketPrice(numRows, seatsPerRow, row);
                seats[row - 1][seatNum - 1] = new Seat(true, ticketPrice, row , seatNum);
            }
        }
        return seats;
    }

    // If the total number of seats <= 60, ticket price = $10.
    // else, back half = $8, front half = $10. If odd rows, back half > front half
    private static int calculateTicketPrice(int numRows, int seatsPerRow, int row) {
        int ticketPrice;

        int numSeats = numRows * seatsPerRow;
        if (numSeats <= 60) {
            ticketPrice = 10;

        } else {
            int frontPrice = 10;
            int backPrice = 8;

            if (numRows % 2 == 0) { // even num rows
                if (row > numRows / 2) ticketPrice = backPrice;
                else ticketPrice = frontPrice;

            } else { // odd num rows
                if (row <= numRows / 2) ticketPrice = frontPrice;
                else ticketPrice = backPrice;
            }
        }

        return ticketPrice;
    }



}