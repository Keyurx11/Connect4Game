package org.example;

import java.util.Scanner;

public class Connect4Game {

    // Constants for the game board
    public static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final char PLAYER_1_TOKEN = 'X';
    public static final char PLAYER_2_TOKEN = 'O';
    public static final char EMPTY_SPACE = '-';
    public static final String BLITZ_KEY = "B";
    public static final String TIME_BOMB_KEY = "T";
    public static final char TIME_BOMB_SYMBOL = '*';
    public static final Scanner scanner = new Scanner(System.in);


    // 2D array to store the game board
    public static char[][] board = new char[ROWS][COLUMNS];


    // Special move variables for Blitz
    public static boolean player1Turn = true;
    private static boolean player1BlitzUsed = false;
    private static boolean player2BlitzUsed = false;


    // Special move variables for Time Bomb
    private static int timeBombRow = -1;
    private static int timeBombCol = -1;
    private static int timeBombCounter = -1;
    public static boolean player1TimeBombUsed = false;
    private static boolean player2TimeBombUsed = false;


    //Store the coordinates of the token to place on the game board
    private static int lastRow = -1;
    private static int lastCol = -1;


    public static void main(String[] args) {
        runGame();
    }

    //This function contains the logic to run the game
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
            System.out.print("Player " + currentPlayer + ", Enter column number (1-" + COLUMNS + ") or special move (B for Blitz, T for Time Bomb): ");
            String move = scanner.nextLine();

            // Validate the input to make sure it is a valid integer or special move
            if (!move.isEmpty() && (move.equalsIgnoreCase(BLITZ_KEY) || move.equalsIgnoreCase(TIME_BOMB_KEY) || move.matches("^\\d+$"))) {
                if (move.equalsIgnoreCase(BLITZ_KEY)) {
                    // Player is using the Blitz special move
                    if (player1Turn && !player1BlitzUsed) {
                        while (true) {
                            System.out.print("Enter column number to clear: ");
                            if (scanner.hasNextInt()) {
                                int column = scanner.nextInt() - 1;
                                if (clearColumn(column)) {
                                    player1BlitzUsed = true;
                                    break;
                                }
                            } else {
                                System.out.println("Sorry, that is not a valid column. Try again!");
                                scanner.next();
                            }
                        }
                    } else if (!player1Turn && !player2BlitzUsed) {
                        while (true) {
                            System.out.print("Enter column number to clear: ");
                            if (scanner.hasNextInt()) {
                                int column = scanner.nextInt() - 1;
                                if (clearColumn(column)) {
                                    player2BlitzUsed = true;
                                    break;
                                }
                            } else {
                                System.out.println("Sorry, that is not a valid column. Try again!");
                                scanner.next();
                            }
                        }
                    } else {
                        System.out.println("Sorry, you have already used the Blitz special move.");
                        continue;
                    }
                } else if (move.equalsIgnoreCase(TIME_BOMB_KEY)) {
                    if (timeBombCounter > 0) {
                        System.out.printf("Sorry but one time bomb is already in deployed.");
                    } else if (player1Turn && !player1TimeBombUsed) {
                        while (true) {
                            System.out.print("Enter column number to place the Time Bomb: ");
                            if (scanner.hasNextInt()) {
                                int column = scanner.nextInt() - 1;
                                if (placeToken(column, TIME_BOMB_SYMBOL)) {
                                    player1TimeBombUsed = true;
                                    break;
                                }
                            } else {
                                System.out.println("Sorry, that is not a valid column. Try again!");
                                scanner.next();
                            }
                        }
                        timeBombCounter = 3;
                        timeBombRow = lastRow;
                        timeBombCol = lastCol;
                        System.out.println("Time Bomb has been placed!");
                    } else if (!player1Turn && !player2TimeBombUsed) {
                        while (true) {
                            System.out.print("Enter column number to place the Time Bomb: ");
                            if (scanner.hasNextInt()) {
                                int column = scanner.nextInt() - 1;
                                if (placeToken(column, TIME_BOMB_SYMBOL)) {
                                    player2TimeBombUsed = true;
                                    break;
                                }
                            } else {
                                System.out.println("Sorry, that is not a valid column. Try again!");
                                scanner.next();
                            }
                        }
                        timeBombCounter = 2;
                        timeBombRow = lastRow;
                        timeBombCol = lastCol;
                        System.out.println("Time Bomb has been placed!");
                    } else {
                        System.out.println("Sorry, you have already used the Time Bomb special move.");
                        continue;
                    }
                } else {
                    // Player is making a normal move
                    int column = Integer.parseInt(move) - 1;
                    if (!placeToken(column, currentPlayer)) {
                        continue;
                    }

                    // Check if the player has won
                    if (checkForWin(lastRow, lastCol)) {
                        printBoard();
                        System.out.println("Player " + currentPlayer + " wins! Game over.");
                        replay();
                        return;
                    }

                    // Check if the game is drawn
                    if (checkForDraw()) {
                        printBoard();
                        System.out.println("Game is drawn! Game over.");
                        replay();
                        return;
                    }

                    // Check if time bomb counter has ended
                    if (timeBombCounter > 0) {
                        timeBombCounter--;
                    } else if (timeBombCounter == 0) {
                        clearSurrounding(timeBombRow, timeBombCol);
                        timeBombCounter--;
                    }
                }
                // Switch to the other player
                if (currentPlayer == PLAYER_1_TOKEN) {
                    currentPlayer = PLAYER_2_TOKEN;
                    player1Turn = false;
                } else {
                    currentPlayer = PLAYER_1_TOKEN;
                    player1Turn = true;
                }

            } else { // If anything else apart from number, T or B is entered will throw error
                System.out.printf("Sorry, that is not a valid move. Try again!");
            }
        }
    }


    //This function is used to print the game board in the terminal
    private static void printBoard() {
        // Print the game board
        System.out.println();
        for (int row = 0; row < ROWS; row++) {
            System.out.print("| ");
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();

        }
        System.out.println("-----------------------------");
        System.out.print("| ");
        for (int col = 1; col < COLUMNS + 1; col++) {
            System.out.printf("%d | ", col);
        }
        System.out.println();
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
            System.out.println("Sorry, that is not a valid column. Try again!");
        } else if (board[0][col] != EMPTY_SPACE) {
            System.out.println("Oh no, that column is full. Try different column!");
        } else {
            isValidMove = true;
        }
        return isValidMove;
    }

    /**
     * This function places a token at the first empty space in a given column
     *
     * @param col   The column in which to place the token
     * @param token The token to place in the column
     * @return Returns a boolean indicating if the operation was successful or not
     */
    public static boolean placeToken(int col, char token) {
        if (!checkIfValidCol(col)) {
            return false;
        }

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
     * Blitz Move - Clears the given column of the game board
     *
     * @param column column number to clear
     */
    public static boolean clearColumn(int column) {
        if (!checkIfValidCol(column)) {
            return false;
        }

        for (int i = 0; i < ROWS; i++) {
            board[i][column] = EMPTY_SPACE;
            return true;
        }
        return false;
    }

    /**
     * TimeBomb Move - Clears the cells surrounding a given location on the game board
     *
     * @param row The row number of the location on the board
     * @param col The column number of the location on the board
     */
    public static void clearSurrounding(int row, int col) {
        // clear the cells around the time bomb
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS) {
                    board[r][c] = EMPTY_SPACE;
                }
            }
        }
    }

}