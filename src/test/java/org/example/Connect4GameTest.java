package org.example;

import org.junit.Test;

import static org.example.Connect4Game.initializeBoard;
import static org.junit.Assert.*;

public class Connect4GameTest {

    @Test //This test checks the functionality of the checkIfValidCol method, including testing valid and invalid columns, as well as full columns.
    public void testCheckIfValidCol() {
        // Initialize the game board
        initializeBoard();

        // Test valid column
        assertTrue(Connect4Game.checkIfValidCol(0));

        // Test invalid column (negative number)
        assertFalse(Connect4Game.checkIfValidCol(-1));

        // Test invalid column (number greater than number of columns)
        assertFalse(Connect4Game.checkIfValidCol(Connect4Game.COLUMNS));

        // Test full column
        for (int row = 0; row < Connect4Game.ROWS; row++) {
            Connect4Game.board[row][1] = Connect4Game.PLAYER_1_TOKEN;
        }
        assertFalse(Connect4Game.checkIfValidCol(1));
    }

    @Test // Test if the placeToken function correctly places tokens in valid and invalid columns
    public void testPlaceToken() {
        // Initialize the game board
        initializeBoard();

        // Test placing a token in a valid column
        assertTrue(Connect4Game.placeToken(0, Connect4Game.PLAYER_1_TOKEN));
        assertEquals(Connect4Game.board[5][0], Connect4Game.PLAYER_1_TOKEN);

        // Test placing a token in a full column
        for (int row = 0; row < Connect4Game.ROWS; row++) {
            Connect4Game.board[row][1] = Connect4Game.PLAYER_1_TOKEN;
        }
        assertFalse(Connect4Game.placeToken(1, Connect4Game.PLAYER_1_TOKEN));

        // Test placing a token in an invalid column
        assertFalse(Connect4Game.placeToken(-1, Connect4Game.PLAYER_1_TOKEN));
        assertFalse(Connect4Game.placeToken(Connect4Game.COLUMNS, Connect4Game.PLAYER_1_TOKEN));
    }

    @Test // Test if the checkForWin function correctly checks different winning combinations
    public void testCheckForWin() {
        // Initialize the game board
        initializeBoard();

        // Test horizontal win for player 1
        for (int col = 0; col < 4; col++) {
            Connect4Game.board[5][col] = Connect4Game.PLAYER_1_TOKEN;
        }
        assertTrue(Connect4Game.checkForWin(5, 0)); // X wins horizontally

        // Test vertical win for player 2
        for (int row = 2; row < 6; row++) {
            Connect4Game.board[row][1] = Connect4Game.PLAYER_2_TOKEN;
        }
        assertTrue(Connect4Game.checkForWin(5, 1)); // O wins vertically

        // Test diagonal win (top-left to bottom-right) for player 1
        for (int i = 0; i < 4; i++) {
            Connect4Game.board[i][i] = Connect4Game.PLAYER_1_TOKEN;
        }
        assertTrue(Connect4Game.checkForWin(3, 3)); // X wins diagonally

        // Test diagonal win (top-right to bottom-left) for player 2
        for (int row = 0, col = 3; row < 4; row++, col--) {
            Connect4Game.board[row][col] = Connect4Game.PLAYER_2_TOKEN;
        }
        assertTrue(Connect4Game.checkForWin(3, 0)); // player 2 wins diagonally

        // Test no win
        assertFalse(Connect4Game.checkForWin(5, 0));
    }


    @Test //This test function checks if the checkForDraw function correctly if in scenario of draw
    public void testCheckForDraw() {
        // Initialize the game board
        for (int row = 0; row < Connect4Game.ROWS; row++) {
            for (int col = 0; col < Connect4Game.COLUMNS; col++) {
                Connect4Game.board[row][col] = Connect4Game.PLAYER_1_TOKEN;
            }
        }    // Test for a draw with a full board
        assertTrue(Connect4Game.checkForDraw());

        // Test for a draw with an empty space on the board
        Connect4Game.board[0][0] = Connect4Game.EMPTY_SPACE;
        assertFalse(Connect4Game.checkForDraw());
    }

    @Test // Test if the clearColumn function correctly clears a given column
    public void testClearColumn() {
        // Initialize the game board
        initializeBoard();

        // Fill the first column with tokens
        for (int row = 0; row < Connect4Game.ROWS; row++) {
            Connect4Game.board[row][0] = Connect4Game.PLAYER_1_TOKEN;
        }

        // Clear the first column
        Connect4Game.clearColumn(0);

        // Try to clear an invalid column
        Connect4Game.clearColumn(-1);
        Connect4Game.clearColumn(Connect4Game.COLUMNS);
    }

    @Test // Test if the clearSurrounding function correctly clears cells around a given location
    public void testClearSurrounding() {
        // Initialize the game board
        initializeBoard();

        // Test clearing surrounding cells of a location on the game board
        Connect4Game.board[2][2] = Connect4Game.PLAYER_1_TOKEN;
        Connect4Game.clearSurrounding(2, 2);
        for (int r = 1; r <= 3; r++) {
            for (int c = 1; c <= 3; c++) {
                assertEquals(Connect4Game.board[r][c], Connect4Game.EMPTY_SPACE);
            }
        }
    }
}