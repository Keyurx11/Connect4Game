# Connect 4 Game

## Overview
This is a simple implementation of the classic Connect 4 game in Java. The game has two game modes: player vs player and player vs computer. The game also includes special moves such as the Blitz and Time Bomb.

## How to Run
1. Make sure you have Java installed on your computer.
2. Download or clone the repository.
3. Open a terminal and navigate to the project's root directory.
4. Run the command `javac Main.java` to compile the code.
5. Run the command `java Main` to start the game.
6. Follow the on-screen prompts to select game mode and make your moves.

## Game Modes
1. Player vs Player: In this mode, two players take turns placing tokens on the game board in an attempt to connect four of their tokens in a row.
2. Player vs Computer: In this mode, the player takes on the computer. The computer makes moves based on a simple algorithm.

## How to play
1. Select game mode: The game will prompt you to select either player vs player or player vs computer mode.
2. Make your move: Take turns placing tokens on the game board by selecting a column number.
3. Connect four tokens: The goal of the game is to connect four of your tokens in a horizontal, vertical, or diagonal row.
4. Special moves: The player can use the Blitz special move to clear one column of tokens and the Time Bomb special move to clear a surrounding area of tokens. Each move can be used once per game.
5. Win or Draw: The game will end when a player connects four tokens in a row or when the game board is full and a draw is declared.
6. Replay: The game will prompt you to replay at the end of the game.

## Special Moves
1. Blitz: The player can use the Blitz special move to clear one column of tokens.
2. Time Bomb: The player can use the Time Bomb special move to place a token that will "blast" and clear a surrounding area after a certain number of turns. 

## Rules
1. Tokens can only be placed in a valid and non-full column.
2. The game board is 7 columns wide and 6 rows tall.
3. The game will end when a player connects four tokens in a row or when the game board is full and a draw is declared.
4. The player can use the Blitz special move to clear one column of tokens and the Time Bomb special move to clear a surrounding area of tokens. Each move can be used once per game.
5. The replay option is available at the end of the game.
6. Enjoy the game!


## Code Structure
The Connect4 class contains the main method and all of the game's logic, including the following methods:

**runGame()**: This method handles the overall flow of the game, including selecting the game mode, initializing the game board, and handling the game loop.

**multiplayerMode()**: This method contains all the logic for the player vs player game mode, including getting player moves, checking for wins and draws, and handling the replay option.

**vsComputerMode()**: This method contains all the logic for the player vs computer game mode, including the creation of the game board, initialization of the current player, the main game loop, and the handling of special moves such as the Blitz and Time Bomb.

**howToPlay()**: This method displays instructions on how to play the game.

**initializeBoard()**: This method initializes the game board with empty spaces.

**printBoard()**: This method prints the current game board to the console.

**checkIfValidCol(int col)**: This method checks if the given column is valid and not full.

**placeToken(int col, char token)**: This method places a token at the first empty space in a given column.

**checkForWin(int row, int col)**: This method checks if the last placed token resulted in a win for the player.

**checkForDraw()**: This method checks if the game is a draw.

**replay()**: This method handles the replay option after the game is finished.

**clearColumn(int col)**: This method clears the given column when the player uses the Blitz special move.

**clearSurrounding(int row, int col)**: This method clears the surrounding tokens when the player uses the Time Bomb special move.

## Enjoy your Game! 
