package Server;

import java.util.List;

public class Board {
    private List<String> boardArray; // 11 x 11 with [-1, 0, 1, 2] or strings
    private boolean playerTurn; // ?
    private int piecesPlayed = 0; // max 30 game stops
    private int lastColumn = -1;
    private int lastRow = -1;

    /**
     * Add a piece to the boardArray
     */
    public void addPiece(Piece piece) {
        validatePiece(piece);

//        boardArray.add()
    }

    /**
     * Validate the it follows the rules of
     * being within one column and row
     * of the last piece placed. If no available spots are available,
     * random is ok
     */
    public void validatePiece(Piece piece) {

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


}
