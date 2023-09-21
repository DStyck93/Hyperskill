import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Create empty grid
        char[][] grid = new char[3][3];
        // WARNING!!! x,y values are swapped for this assignment
        for (int x = 0; x < 3; x ++) {
            for (int y = 0; y < 3; y++) {
                grid[y][x] = ' ';
            }
        }

        // Output grid
        displayGrid(grid);

        // Play game
        boolean isOver = false;
        int turnNum = 1;
        char turn = 'X'; // X moves 1st
        while(!isOver) {

            // Prompt user to make a move
            // WARNING!!! x and y values are swapped for this assignment
            int[] move = getMove(scanner, grid);
    
            // Update grid
            updateGrid(grid, move[0], move[1], turn);
            
            // Output updated grid
            displayGrid(grid);

            // Check for winner
            boolean isWinner = false;
            if(turnNum >= 5) {
                isWinner = checkForWinner(grid, turn);
            }

            if (isWinner) {
                System.out.println(turn + " wins");
                isOver = true;
            } else if (turnNum == 9) {
                System.out.println("Draw");
                isOver = true;
            }
            
            // Update turn
            if (turn == 'X') turn = 'O';
            else turn = 'X';
            turnNum++;
        }

        scanner.close();
    }

    private static boolean checkForWinner(char[][] grid, char turn) {
        boolean isOver = false;
        
        // Check for row
        for (int row = 0; row < 3; row++) {
            if (grid[0][row] == turn && grid[1][row] == turn && grid[2][row] == turn) isOver = true;
        }

        // Check for col
        for (int col = 0; col < 3; col++) {
                if (grid[col][0] == turn && grid[col][1] == turn && grid[col][2] == turn) isOver = true;
        }

        // Check for diagonal
        if (grid[1][1] == turn) {
            if (grid[0][0] == turn && grid[2][2] == turn) isOver = true;
            if (grid[2][0] == turn && grid[0][2] == turn) isOver = true;
        }

        return isOver;        
    }

    private static void displayGrid(char[][] grid) {
        System.out.println("---------");
        System.out.println("| " + grid[0][0] + " " + grid[1][0] + " " + grid[2][0] + " |");
        System.out.println("| " + grid[0][1] + " " + grid[1][1] + " " + grid[2][1] + " |");
        System.out.println("| " + grid[0][2] + " " + grid[1][2] + " " + grid[2][2] + " |");
        System.out.println("---------");
    }

    // WARNING!!! Order of x,y values are swapped for this assignment
    private static int[] getMove(Scanner scanner, char[][] grid){
        boolean isValid = false;
        int x = -1, y = -1;

        while(!isValid){
            y = scanner.nextInt();
            x = scanner.nextInt();

            // Account for grid starting at 0
            x--;
            y--;

            // Check for integers
            if (x == -1 || y == -1) {
               System.out.println("You should enter numbers!");
            }

            // Check for valid range
            else if (x > 2 || x < 0 || y > 2 || y < 0) {
                System.out.println("Coordinates should be from 1 to 3!");
            }

            // Check for occupied cell
            else if (grid[x][y] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            }

            else {
                isValid = true;
            }
        }

        return new int[] {x, y};
    }

    private static void updateGrid(char[][] grid, int x, int y, char turn) {
        grid[x][y] = turn;
    }
}