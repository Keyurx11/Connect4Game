package org.example;

import java.util.Scanner;

public class Connect4Game {

    // Constants for the game board
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final char PLAYER_1_TOKEN = 'X';
    public static final char PLAYER_2_TOKEN = 'O';
    static final char EMPTY_SPACE = '-';
    public static final char BLITZ_KEY = 'B';
    //public static final char BLITZ_KEY = 'T';

    // 2D array to store the game board
    public static char[][] board = new char[ROWS][COLUMNS];
    public static Scanner scanner = new Scanner(System.in);

    // Special move variables
    public static boolean player1Turn = true;
    private static boolean player1BlitzUsed = false;
    private static boolean player2BlitzUsed = false;

    //These row-col variables are used as argument in calling into checkWin function. We are getting that from placeToken function.
    private static int lastRow = -1;
    private static int lastCol = -1;

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

            // Get the player's move
            System.out.println("Player" + currentPlayer + ", Enter column number (1-7) or special move (B for Blitz, T for Time Bomb): ");
            String move = scanner.nextLine();

            // Validate the input to make sure it is a valid integer or special move
            if (!move.isEmpty() && (move.equalsIgnoreCase("B") || move.equalsIgnoreCase("T") || move.matches("^\\d+$"))) {
                if (move.equalsIgnoreCase("B")) {
                    // Player is using the Blitz special move
                    if (player1Turn && !player1BlitzUsed) {
                        player1BlitzUsed = true;
                        System.out.print("Enter column number to clear: ");
                        int column = scanner.nextInt() - 1;
                        clearColumn(column);
                    } else if (!player1Turn && !player2BlitzUsed) {
                        player2BlitzUsed = true;
                        System.out.print("Enter column number to clear: ");
                        int column = scanner.nextInt() - 1;
                        clearColumn(column);
                    } else {
                        System.out.println("Sorry, you have already used the Blitz special move or it is not your turn.");
                        continue;
                    }
                } else if (move.equalsIgnoreCase("T")) {
                    //TODO timebomb feature
                } else {
                    // Player is making a normal move
                    int column = Integer.parseInt(move) - 1;
                    if (!checkIfValidCol(column) || !placeToken(column, currentPlayer)) {
                        continue;
                    }
                    // Check if the player has won
                    if (checkForWin(lastRow, lastCol)) {
                        printBoard();
                        System.out.println("Player " + currentPlayer + " wins!");
                        replay();
                        break;
                    }
                }
            } else {
                System.out.println("Sorry, that is not a valid move. Please enter a column number (1-" + COLUMNS + ") or special move (B for Blitz, T for Time Bomb).");
                continue;
            }

            // Check if the game is drawn
            if (checkForDraw()) {
                printBoard();
                System.out.println("It's a draw!");
                replay();
                break;
            }
            // Switch to the other player
            if (currentPlayer == PLAYER_1_TOKEN) {
                currentPlayer = PLAYER_2_TOKEN;
                player1Turn = false;
            } else {
                currentPlayer = PLAYER_1_TOKEN;
                player1Turn = true;
            }
        }
    }

    //This function is used to print the game board in the terminal
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

    /**
     * This function places a token at the first empty space in a given column
     *
     * @param col   The column in which to place the token
     * @param token The token to place in the column
     * @return Returns a boolean indicating if the operation was successful or not
     */
    public static boolean placeToken(int col, char token) {
        // Place the token in the first empty space in the column
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY_SPACE) {
                board[row][col] = token;
                lastRow = row;
                lastCol = col;
                return true;
            }
        }

        // The column is full, return false
        return false;
    }

    /**
     * This function checks if the last placed token resulted in a win for the player
     *
     * @param row The row where the last token was placed
     * @param col The column where the last token was placed
     * @return Returns a boolean indicating if the last placed token resulted in a win
     */
    public static boolean checkForWin(int row, int col) {
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

    /**
     * Checks if the board is full and the game is draw
     *
     * @return true if the game is draw, false otherwise
     */
    private static boolean checkForDraw() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == EMPTY_SPACE) {
                    return false; // if there is at least one empty space, the game is not a draw
                }
            }
        }
        return true; // if all spaces are filled, the game is a draw
    }

    // Ask if user wants to replay and calls the runGame function again to replay
    private static void replay() {
        //Asking the user if they want to play again
        System.out.println("Would you like to play again? y/n");
        String rematch = scanner.next();
        //Making sure user enters a valid selection
        while (!rematch.equalsIgnoreCase("y") && !rematch.equalsIgnoreCase("n")) {
            System.out.println("Invalid input. Please enter y or n:");
            rematch = scanner.next();
        }
        //If user wants to replay then calls the function to restart the game
        if (rematch.equalsIgnoreCase("y")) {
            //run game again
            runGame();
        }
    }

    /**
     * Clears the given column of the game board
     *
     * @param column column number to clear
     */
    public static void clearColumn(int column) {
        for (int i = 0; i < ROWS; i++) {
            board[i][column] = EMPTY_SPACE;
        }
    }

    /**
     * Checks if the given column is valid and not full
     *
     * @param col column number to check
     * @return true if the column is valid, false otherwise
     */
    public static boolean checkIfValidCol(int col) {
        boolean isValidMove = false;
        if (col < 0 || col >= COLUMNS) {
            System.out.println("Sorry, that is not a valid move. Please enter a column number (1-7).");
        } else if (board[0][col] != EMPTY_SPACE) {
            System.out.println("Oh no, that column is full. Try different column!");
        } else {
            isValidMove = true;
        }
        return isValidMove;
    }

}