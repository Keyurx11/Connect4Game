package org.example;

public class Connect4Game {

    public static void main(String[] args) {
        // Create the game board
        char[][] board = new char[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                board[row][col] = '*';
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }

}
