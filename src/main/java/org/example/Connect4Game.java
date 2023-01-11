package org.example;

import java.util.Scanner;

public class Connect4Game {

    // Constants for the game board
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final char PLAYER_1_TOKEN = 'X';
    public static final char PLAYER_2_TOKEN = 'O';
    static final char EMPTY_SPACE = ' ';
    public static char[][] board = new char[ROWS][COLUMNS];
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        runGame();
    }

    public static void runGame() {
        // Create the game board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY_SPACE;
            }
        }
        // Initialize the current player
        char currentPlayer = PLAYER_1_TOKEN;

        // Main game loop
        while (true) {
            printBoard();

            // Prompt the player for their next move
            System.out.print("Player " + currentPlayer + ", enter column number: ");
            int col = scanner.nextInt() - 1; //input - 1 as comp counts from 0 but our user will enter col 1-7
            scanner.nextLine();
            //Making sure the move is within the game board
            while (col < 0 || col >= COLUMNS) {
                System.out.print("Sorry, that is not a valid move. Please enter a column number (1-7):");
                col = scanner.nextInt() - 1; //input - 1 as comp counts from 0 but our user will enter col 1-7
            }

            // Place the token in the specified column
            int row = placeToken(board, col, currentPlayer);
            if (row == -1) {
                System.out.println("\nOh no, that column is full. Try different column!");
                continue;
            }

            // Check if the player has won the game
            if (checkForWin(board, row, col)) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                replay();
                break;
            }

            // Check if the game is drawn
            if (checkForDraw(board)) {
                printBoard();
                System.out.println("Its a draw");
                replay();
                break;
            }


            // Switch to the other player
            if (currentPlayer == PLAYER_1_TOKEN) {
                currentPlayer = PLAYER_2_TOKEN;
            } else {
                currentPlayer = PLAYER_1_TOKEN;
            }


        }
    }

    public static int placeToken(char[][] board, int col, char token) {
        // Check if the column is full
        if (board[0][col] != EMPTY_SPACE) {
            return -1;
        }

        // Place the token in the first empty space in the column
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY_SPACE) {
                board[row][col] = token;
                return row;
            }
        }

        // The column is full, return -1
        return -1;
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

    private static void replay() {
        //If players wants to play again
        System.out.println("Would you like to play again? y/n");
        String rematch = scanner.nextLine();
        //Making sure user enters a valid selection
        while (!rematch.equalsIgnoreCase("y") && !rematch.equalsIgnoreCase("n")) {
            System.out.println("Invalid input. Please enter y or n:");
            rematch = scanner.nextLine();
        }
        //If user wants to replay then calls the function to restart the game
        if (rematch.equalsIgnoreCase("y")) {
            // Reset the game board
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    board[i][j] = EMPTY_SPACE;
                }
            }
            //run game again
            runGame();
        }
    }

    public static boolean checkForWin(char[][] board, int row, int col) {
        // Check for a horizontal win
        int count = 0;
        for (int i = col - 3; i <= col + 3; i++) {
            if (i < 0 || i >= COLUMNS) {
                continue;
            }
            if (board[row][i] == board[row][col]) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check for a vertical win
        count = 0;
        for (int i = row - 3; i <= row + 3; i++) {
            if (i < 0 || i >= ROWS) {
                continue;
            }
            if (board[i][col] == board[row][col]) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check for a diagonal win (check left to right)
        count = 0;
        for (int i = row - 3, j = col - 3; i <= row + 3; i++, j++) {
            if (i < 0 || i >= ROWS || j < 0 || j >= COLUMNS) {
                continue;
            }
            if (board[i][j] == board[row][col]) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check for a diagonal win (check right to left)
        count = 0;
        for (int i = row - 3, j = col + 3; i <= row + 3; i++, j--) {
            if (i < 0 || i >= ROWS || j < 0 || j >= COLUMNS) {
                continue;
            }
            if (board[i][j] == board[row][col]) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }


        return false;
    }

    private static boolean checkForDraw(char[][] board) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == EMPTY_SPACE) {
                    return false; // if there is at least one empty space, the game is not a draw
                }
            }
        }
        return true; // if all spaces are filled, the game is a draw
    }

}