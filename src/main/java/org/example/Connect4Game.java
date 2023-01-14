package org.example;

import java.util.Random;
import java.util.Scanner;

public class Connect4Game {

    // Constants for the game board
    public static final int ROWS = 6;
    public static final int COLUMNS = 7; //board is relative and responsible till 3 digits column size
    public static final char PLAYER_1_TOKEN = 'X';
    public static final char PLAYER_2_TOKEN = 'O';
    public static final char EMPTY_SPACE = '-'; //change to any styling you like
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
    private static int blastTimeBombIn = 3; //write in how many moves you want time bomb to blast
    private static int timeBombCounter = -1;
    public static boolean player1TimeBombUsed = false;
    private static boolean player2TimeBombUsed = false;


    //Store the coordinates of the token to place on the game board
    private static int lastRow = 0;
    private static int lastCol = 0;


    public static void main(String[] args) {
        runGame();
    }


    public static void runGame() {
        System.out.println("\nWelcome to Connect 4!");
        System.out.println("Who would you playing with?");
        System.out.println("1. Player vs Player");
        System.out.println("2. Player vs Computer");
        System.out.println("3. Play Guide");

        // Prompt the user to select a game mode
        System.out.print("Please select a one of the option (1, 2 or 3): ");

        // Validate the user's input to ensure that it is 1 or 2
        String gameMode = scanner.nextLine();
        while (!gameMode.equals("1") && !gameMode.equals("2") && !gameMode.equals("3")) {
            System.out.print("Invalid input. Please select a game mode (1, 2 or 3): ");
            gameMode = scanner.nextLine();
        }

        if (gameMode.equals("1")) {
            multiplayerMode();
        } else if (gameMode.equals("2")) {
            vsComputerMode();
        } else if (gameMode.equals("3")) {
            howToPlay();
        }
    }


    // Player vs Player game mode
    public static void multiplayerMode() {
        System.out.println("\nGame's On! Player X first");
        // Create the game board
        initializeBoard();

        // Initialize the current player
        char currentPlayer = PLAYER_1_TOKEN;

        // Main game loop
        while (true) {
            printBoard();

            // Get the player's move
            System.out.print("Player " + currentPlayer + ", Enter column number (1-" + COLUMNS + ") or special move (B for Blitz, T for Time Bomb): ");
            String move = scanner.next();

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
                }
                // Player is using the Time Bomb special move
                else if (move.equalsIgnoreCase(TIME_BOMB_KEY)) {
                    if (timeBombCounter > 0) {
                        System.out.printf("Sorry but one time bomb is already deployed.");
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
                        timeBombCounter = blastTimeBombIn;
                        timeBombRow = lastRow;
                        timeBombCol = lastCol;
                        try {
                            System.out.println("\nTime Bomb has been placed! Will blast in 3 moves.");
                            Thread.sleep(1300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
                        timeBombCounter = blastTimeBombIn;
                        timeBombRow = lastRow;
                        timeBombCol = lastCol;
                        try {
                            System.out.println("\nTime Bomb has been placed! Will blast in 3 moves.");
                            Thread.sleep(1300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
                        System.out.println("Player " + currentPlayer + " wins! GAME OVER.");
                        replay();
                        return;
                    }

                    // Check if the game is drawn
                    if (checkForDraw()) {
                        replay();
                        return;
                    }
                }
                // Check if time bomb counter has ended
                if (timeBombCounter > 0) {
                    timeBombCounter--;
                } else if (timeBombCounter == 0) {
                    System.out.printf("\nTick Tick ...\n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("\nBOOM!!!\n");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clearSurrounding(timeBombRow, timeBombCol);
                    timeBombCounter--;
                }

                // Switch to the other player
                if (currentPlayer == PLAYER_1_TOKEN) {
                    currentPlayer = PLAYER_2_TOKEN;
                    player1Turn = false;
                } else {
                    currentPlayer = PLAYER_1_TOKEN;
                    player1Turn = true;
                }

                //Calls player's turn
                System.out.println("\nPlayer " + currentPlayer + " turn");

            }
            // If anything else apart from number, T or B is entered will throw error
            else {
                System.out.printf("Sorry, that is not a valid move. Try again!");
            }
        }
    }

    // Player vs Computer game mode
    public static void vsComputerMode() {

        System.out.println("\nComputer says 'after you' a like gentleman, it's your turn to make the first move.");
        // Create the game board
        initializeBoard();

        // Initialize the current player
        char currentPlayer = PLAYER_1_TOKEN;
        boolean isBlitzModeActive = false; // check if blitz is being used for every round, turns only true when blitz is used and then turns back to false. Keep it or else it triggers checkForWin to pass

        // Main game loop
        while (true) {
            printBoard();
            if (currentPlayer == PLAYER_1_TOKEN) {
                // Get the player's move
                System.out.print("Player " + currentPlayer + ", Enter column number (1-" + COLUMNS + ") or special move (B for Blitz, T for Time Bomb): ");
                String move = scanner.next();

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
                                        isBlitzModeActive = true;
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

                    }
                    // Player is using the Time Bomb special move
                    else if (move.equalsIgnoreCase(TIME_BOMB_KEY)) {
                        if (timeBombCounter > 0) {
                            System.out.print("Sorry but one time bomb is already deployed.");
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
                            timeBombCounter = blastTimeBombIn;
                            timeBombRow = lastRow;
                            timeBombCol = lastCol;
                            try {
                                System.out.println("\nTime Bomb has been placed! Will blast in 3 moves.");
                                Thread.sleep(1300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Sorry, you have already used the Time Bomb special move.");
                            continue;
                        }
                    }
                    // Player is making a normal move
                    else {
                        int column = Integer.parseInt(move) - 1;
                        if (!placeToken(column, currentPlayer)) {
                            continue;
                        }
                    }

                }
                // If anything else apart from number, T or B is entered will throw error
                else {
                    System.out.printf("Sorry, that is not a valid move. Try again!");
                    continue;
                }
            } else if (currentPlayer == PLAYER_2_TOKEN) {
                // Computer's move
                //Adding little pause to make gameplay realistic
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Using math random to generate random number
                Random random = new Random();
                int column = random.nextInt(COLUMNS) + 1;
                System.out.println(column);
                while (!placeToken(column, currentPlayer)) {
                    column = random.nextInt(COLUMNS) + 1;
                    System.out.println(column);
                }
            }

            //For Both User & Computer Move-------
            //Check if anyone won the game
            if (checkForWin(lastRow, lastCol) && !isBlitzModeActive) {
                // Check if the player has won
                if (currentPlayer == PLAYER_1_TOKEN) {
                    printBoard();
                    System.out.println("GAME OVER! You emerged victorious in the battle of wits against the computer, well done!");
                    replay();
                    return;
                }
                //Check if the computer has won
                if (currentPlayer == PLAYER_2_TOKEN) {
                    printBoard();
                    System.out.println("GAME OVER! Looks like the computer outsmarted you this time, better luck next time Einstein!");
                    System.out.printf("Taking you back to main menu.");
                    try {
                        Thread.sleep(3000);//Just wanted to have some fun creating loading animation :)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.printf(".");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(".\n");
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runGame();
                    return;
                }
            }

            // Check if the game is drawn
            if (checkForDraw()) {
                replay();
                return;
            }

            //deactivating blitz mode
            isBlitzModeActive = false;

            // Check if time bomb counter has ended
            if (timeBombCounter > 0) {
                timeBombCounter--;
            } else if (timeBombCounter == 0) {
                try {
                    System.out.printf("\nTick Tick ...\n");
                    Thread.sleep(1300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.printf("\nBOOM!!!\n");
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clearSurrounding(timeBombRow, timeBombCol);
                timeBombCounter--;
            }

            // Switch to the other player
            if (currentPlayer == PLAYER_1_TOKEN) {
                currentPlayer = PLAYER_2_TOKEN;
                System.out.println("\nComputer's turn");
            } else {
                currentPlayer = PLAYER_1_TOKEN;
                System.out.println("\nYour turn");
            }
        }
    }

    //This function is just guide on how to play the game
    private static void howToPlay() {
        System.out.println("\nWelcome to Connect 4 with special moves! In this version of the game, \nplayers can select the column they wish to drop their counter (O or X) into \nand also use two optional special moves: BLITZ and TIME BOMB. BLITZ removes all tokens in a selected column, \nwhile TIME BOMB clears surrounding tokens after 2 more turns. \n\nTo play, select game mode, take turns dropping counters and use special moves by entering B or T followed by a column number. \nThe game will declare a winner when a line of 4 matching counters is formed or end in a draw if the board is full. \nNote that special moves can only be used once per player. Good luck!\n");

        System.out.println("1. Main menu");

        // Prompt the user to select a game mode
        System.out.print("Please enter 1 to go back to the main menu: ");

        // Validate the user's input to ensure that it is 1 or 2
        String gameMode = scanner.nextLine();
        while (!gameMode.equals("1")) {
            System.out.print("Invalid input. Please enter 1 to go back to the main menu: ");
            gameMode = scanner.nextLine();
        }

        if (gameMode.equals("1")) {
            runGame();
        }
    }

    //This function creates the game-board at start of the game
    static void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board[row][col] = EMPTY_SPACE;
            }
        }
    }


    //This function is used to print the game board in the terminal
    private static void printBoard() {
        // Prints out the playable columns for the board
        System.out.println();
        for (int row = 0; row < ROWS; row++) {
            System.out.print("| ");
            for (int col = 0; col < COLUMNS; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
        }
        // Prints out the dashed line relative to variable Column
        System.out.print("-");
        for (int col = 1; col < COLUMNS + 1; col++) {
            System.out.print("----");
        }
        // Prints out the numbered columns in the end
        System.out.print("\n|");
        for (int col = 1; col < COLUMNS + 1; col++) {
            System.out.printf("%2d |", col);
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
    static boolean checkForDraw() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == EMPTY_SPACE) {
                    return false; // if there is at least one empty space, the game is not a draw
                }
            }
        }
        printBoard();
        System.out.println("GAME OVER! It's a draw.\n");
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
        }
        try {
            System.out.println("\nBlitzzzzed, pew pew..");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
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