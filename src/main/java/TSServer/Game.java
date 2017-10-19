package TSServer;

import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class Game {
    private boolean playerTurn; // ?
    private int piecesPlayed = 0; // max 30 game stops
    private int lastColumn = -1;
    private int lastRow = -1;
    private Slot[][] innerBoard;
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Constructor
     */
    public Game() {
        innerBoard = new InnerBoard().getBoardArray();
    }

    /**
     * Add a piece to the boardArray
     */
    public void addPiece(int row, int column) {
        log.debug("Attempting to adding piece: row ->" + row + " column->" + column);
        if (validatePiece(row, column, lastRow, lastColumn)) {
            addPiece(row, column);
        }
    }

    /**
     * Validate the move, follows the rules of
     * being within one column and row
     * of the last piece placed. If no available spots are available,
     * random is ok. Slot must be *empty* or *adjacent* for a move to be valid
     *
     * @param row row played
     * @param column column played
     * @param lastColumn last column played
     * @param lastRow last row played
     *
     * @return true if valid, false if not
     */
    public boolean validatePiece(int row, int column, int lastRow, int lastColumn) {
        log.debug("Validating piece: row-> " + row + " column-> " + column + "\nlastRow-> " + lastRow + " lastColumn->" + lastColumn);

        // Slot is empty and either the row or column is the same
        if (innerBoard[row][column] == Slot.NOT_OCCUPIED && (row == lastRow || column == lastColumn)) {
            return true;
        } else {
            // Checks if there is a free adjacent cell that the player
            // Could have placed his piece in. If not, the move is valid.
            for (int i = 0; i < innerBoard.length; i++) {
                if (innerBoard[lastRow][i] == Slot.NOT_OCCUPIED || innerBoard[i][lastColumn] == Slot.NOT_OCCUPIED) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Clears the innerBoard and restarts
     */
    public void endGame() {

    }

    /**
     * Checks the innerBoard with the new added piece
     */
    public void calculatePoints() {

    }

    // TODO: NextMove etc.

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
