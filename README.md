## Overview ⛰️
The object of the game is to score points by getting stones of your color into three pockets in a row, be it across, up and down, or diagonally. There are black and white stones for the players and clear stones that count for both players.

The game can be played with an element of luck, drawing stones from a bag, or as pure skill with each player taking equal amounts of the colored stones. Timed tournament play and handicapping is also an option.

## Specifications
It is a client-server TCP-based system in Java that will allow a person using the client to play game(s) of 3 Stones with the computer server.

At initiation, the server software will display its IP address. The client will allow the user to input the IP address of the server. The machines will then connect to begin the session, after which:

* The **client** program will allow the user to start a game, or to quit the client; the client will allow the user the same two choices at the end of each game; it is desirable, but not essential, for the client to support a feature that allows the user to quit the client at any time, via the mouse or the keyboard;
* The **server** will continue servicing the client that began the session until the client terminates the session by closing the socket, at which point the server will resume listening for new clients (i.e. the server will be an iterative server, servicing one client at a time).

Both the client and the server are to display appropriate messages while running. The client will display the game board while a game is in progress. The display is text based.

The 3S protocol will have the server listening on port `50,000`. 

All 3S messages must be fixed-length, multibyte packets. Design your packet layout carefully.

A 3S session consists of zero or more games.

The client initiates a game by sending the server a request-game-start message (e.g. `0x00000000`). The server replies with a game-start message (e.g. `0x0000000A`), after which the client draws the board...

## Game rules
3 Stones is a strategy board game akin to tic-tac-toe. This version of this game will have the client play the first stone onto the modified board (see bottom of document), and then the server and client will alternate playing stones. Each will try to score more points than the opponent by the end of the game, which occurs when all stones have been used. The client starts with 15 white stones and the server starts with 15 black stones. At the end of the game the client displays a message showing the 2 scores and who the winner is, or whether a draw occurred. The server is responsible for all of the scoring logic.


1. A stone placement scores 1 point for EVERY three-stones-in-a-line that it completes, vertical and/or horizontal and/or diagonal
2. The first stone may be played in any slot
3. Any other stone must be played into an empty slot that is either in the same row or the same column as the last stone played
4. If there are empty slots neither in the same row nor in the same column as the last stone played, the next stone may be played in any empty slot
5. Slots on opposite sides of the center are considered to be part of the same row or column for the purposes of next-stone placement; however, three-stones-in-a-line cannot be scored through the centre.
