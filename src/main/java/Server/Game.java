package Server;

public class Game {
    private boolean playerTurn; // ?
    private int piecesPlayed = 0; // max 30 game stops
    private int lastColumn = -1;
    private int lastRow = -1;

    /**
     * Add a piece to the boardArray
     */
    public void addPiece() {
        validatePiece();

//        boardArray.add()
    }

    /**
     * Validate the it follows the rules of
     * being within one column and row
     * of the last piece placed. If no available spots are available,
     * random is ok
     */
    public void validatePiece() {

    }

    /**
     * Clears the board and restarts
     */
    public void endGame() {

    }

    /**
     * Checks the board with the new added piece
     */
    public void calculatePoints() {

    }

    /**
     * playSession(... s) {
     *  loop on (play Again) {
     *      loop on (!gameOver) or playGame
     *      -- depends on where we place this session
     *  }
     * }
     */

    // Here the stone and slot enum can be placed

    // TODO: determineNextMove()
    // TODO: countScoreForMove(x,y)
}