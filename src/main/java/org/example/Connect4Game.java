package org.example;

import java.util.Scanner;

public class Connect4Game {

    // Constants for the game board
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final char PLAYER_1_TOKEN = 'X';
    public static final char PLAYER_2_TOKEN = 'O';
    private static final char EMPTY_SPACE = ' ';
    public static char[][] board = new char[ROWS][COLUMNS];

    public static void main(String[] args) {
        // Create the game board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY_SPACE;
            }
        }
        //Reading player input
        Scanner scanner = new Scanner(System.in);

        // Initialize the current player
        char currentPlayer = PLAYER_1_TOKEN;

        // Main game loop
        while (true) {
            printBoard();

            // Prompt the player for their next move
            System.out.print("Player " + currentPlayer + ", enter column number: ");
            int col = scanner.nextInt();

            // Place the token in the specified column
            for (int row = ROWS - 1; row >= 0; row--) {
                if (board[row][col] == EMPTY_SPACE) {
                    board[row][col] = currentPlayer;
                    break;
                }
            }
        }
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
