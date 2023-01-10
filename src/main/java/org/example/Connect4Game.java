package org.example;

public class Connect4Game {

    // Constants for the game board
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static char[][] board = new char[ROWS][COLUMNS];

    public static void main(String[] args) {
        // Create the game board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = ' ';
            }
        }
        printBoard();
    }

    private static void printBoard() {
        // Print the game board
        for (int row = 0; row < ROWS; row++) {
            System.out.print("| ");
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();

        }
        System.out.println("-----------------------------");
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
    }
}
