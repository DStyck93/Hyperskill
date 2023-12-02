package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {
    //region Private Variables
    private final int NUM_ROWS = 6, NUM_COLS = 7; // Number of rows and columns
    private final JButton[][] BUTTONS = new JButton[NUM_ROWS][NUM_COLS];

    private final Player X = new Player('X');
    private final Player O = new Player('O');
    private Player currentPlayer = X;
    private boolean isGameOver = false;
    //endregion

    //region Main Window
    public ConnectFour() {
        super("Connect Four"); // Title

        // Frame Size
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int width = (int)(maxWidth * 0.25f); // Base size on % of monitor width
        int height = width + 1;
        setSize(width, height);

        // Layout
        setLayout(new BorderLayout());

        // Button Panel
        JPanel buttonPanel = new JPanel();
        int hGap = 0, vGap = 0;
        buttonPanel.setLayout(new GridLayout(NUM_ROWS, NUM_COLS, hGap, vGap));

        // Button Matrix
        buildButtonMatrix();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                buttonPanel.add(BUTTONS[row][col]);
            }
        }
        add(buttonPanel, BorderLayout.CENTER);

        // Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(e -> {handleReset();});
        add(resetButton, BorderLayout.SOUTH);

        // Formatting
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }
    //endregion

    //region Private Methods
    private void buildButtonMatrix() {
        char colChar = 'A'; // Top left char
        int rowNum = 6;     // Top left num
        int color = 1;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                // Build button
                String name = "Button" + colChar + rowNum;
                JButton button = new JButton(" ");
                button.setName(name);
                button.addActionListener(e -> {if(!isGameOver) handleButtonClick(button);});
                // Alternate button colors
                if (color % 2 == 0) {button.setBackground(Color.LIGHT_GRAY);}
                else {button.setBackground(Color.GRAY);}
                color++;

                // Add to matrix
                BUTTONS[row][col] = button;

                colChar++;
            }
            colChar = 'A';
            rowNum--;
        }
    }

    private void handleButtonClick(JButton button) {
        int colIndex = getColumnIndex(button);

        // Add player move to the last empty spot in the selected column
        for (int row = NUM_ROWS - 1; row >= 0; row--) {
            if (BUTTONS[row][colIndex].getText().equals(" ")) {
                BUTTONS[row][colIndex].setText(Character.toString(currentPlayer.getLetter()));
                checkForWinner(BUTTONS[row][colIndex]);
                changePlayer();
                break;
            }
        }
    }

    private void handleReset() {
        // Reset starting player
        currentPlayer = X;

        // Reset button text & color
        int color = 1;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                BUTTONS[row][col].setText(" ");
                if (color % 2 == 0) BUTTONS[row][col].setBackground(Color.LIGHT_GRAY);
                else BUTTONS[row][col].setBackground(Color.GRAY);
                color++;
            }
        }

        isGameOver = false;
    }

    private void checkForWinner(JButton button) {
        if (hasFourVertical(button) || hasFourHorizontal(button) || hasFourDiagonal(button)) {
            isGameOver = true;
        }
    }

    private boolean hasFourVertical(JButton button) {
        int colIndex = getColumnIndex(button);
        String playerLetter = Character.toString(currentPlayer.getLetter());
        JButton[] connectedButtons = new JButton[4];

        int numConnected = 0;
        for (int row = 0; row < NUM_ROWS; row++) {
            if (BUTTONS[row][colIndex].getText().equals(playerLetter)) {
                connectedButtons[numConnected] = BUTTONS[row][colIndex];
                numConnected++;
            }
            else numConnected = 0;
            if (numConnected == 4) {
                changeButtonColors(connectedButtons);
                return true;
            }
        }
        return false;
    }

    private boolean hasFourHorizontal(JButton button) {
        int rowIndex = getRowIndex(button);
        String playerLetter = Character.toString(currentPlayer.getLetter());
        JButton[] connectedButtons = new JButton[4];

        int numConnected = 0;
        for (int col = 0; col < NUM_COLS; col++) {
            if (BUTTONS[rowIndex][col].getText().equals(playerLetter)) {
                connectedButtons[numConnected] = BUTTONS[rowIndex][col];
                numConnected++;
            }
            else numConnected = 0;
            if (numConnected == 4) {
                changeButtonColors(connectedButtons);
                return true;
            }
        }
        return false;
    }

    private boolean hasFourDiagonal(JButton button) {
        int rowIndex = getRowIndex(button), colIndex = getColumnIndex(button);
        String playerLetter = Character.toString(currentPlayer.getLetter());
        JButton[] connectedButtons = new JButton[4];
        int numConnected = 0;

        // Positive slope "/"
        int startRow = rowIndex, startCol = colIndex;
        while (startRow < NUM_ROWS - 1 && startCol > 0) {
            startRow++;
            startCol--;
        }
        int row = startRow, col = startCol;
        while (row >= 0 && col < NUM_COLS) {
            if (BUTTONS[row][col].getText().equals(playerLetter)) {
                connectedButtons[numConnected] = BUTTONS[row][col];
                numConnected++;
            }
            else {
                numConnected = 0;
            }
            if (numConnected == 4) {
                changeButtonColors(connectedButtons);
                return true;
            }
            row--;
            col++;
        }

        // Negative slope "\"
        startRow = rowIndex;
        startCol = colIndex;
        while (startRow > 0 && startCol > 0) {
            startRow--;
            startCol--;
        }
        row = startRow;
        col = startCol;
        while (row < NUM_ROWS && col < NUM_COLS) {
            if (BUTTONS[row][col].getText().equals(playerLetter)) {
                connectedButtons[numConnected] = BUTTONS[row][col];
                numConnected++;
            }
            else numConnected = 0;
            if (numConnected == 4) {
                changeButtonColors(connectedButtons);
                return true;
            }
            row++;
            col++;
        }

        return false;
    }

    /**
     * Used to change the color of the game winning buttons
     * @param buttons List of buttons to change colors for
     */
    private void changeButtonColors(JButton[] buttons) {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                BUTTONS[row][col].setBackground(Color.white);
            }
        }
        for (JButton button : buttons) {
            button.setBackground(Color.green);
        }
    }

    private char getColumn(JButton button) {
        return button.getName().charAt(6); // Button name example: "ButtonA1"
    }

    private int getColumnIndex(JButton button) {
        return getColumn(button) - 'A';
    }

    private int getRow(JButton button) {
        return Character.getNumericValue(button.getName().charAt(7)); // Button name example: "ButtonA1"
    }

    private int getRowIndex(JButton button) {
        return Math.abs(getRow(button) - 6);
    }

    private void changePlayer() {
        if (currentPlayer == X) currentPlayer = O;
        else currentPlayer = X;
    }
    //endregion
}