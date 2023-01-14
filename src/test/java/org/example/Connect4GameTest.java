package org.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class Connect4GameTest {

    @Test
    public void testCheckIfValidCol() {
// Initialize the game board
        for (int row = 0; row < Connect4Game.ROWS; row++) {
            for (int col = 0; col < Connect4Game.COLUMNS; col++) {
                Connect4Game.board[row][col] = Connect4Game.EMPTY_SPACE;
            }
        }
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

    @Test
    public void testPlaceToken() {
        // Initialize the game board
        for (int row = 0; row < Connect4Game.ROWS; row++) {
            for (int col = 0; col < Connect4Game.COLUMNS; col++) {
                Connect4Game.board[row][col] = Connect4Game.EMPTY_SPACE;
            }
        }

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

    //TODO Test for checkForWin
    @Test
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

    //TODO Test for clearColumn

    //TODO Test for clearSurrounding

}