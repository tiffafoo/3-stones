package TSServer;

import org.slf4j.LoggerFactory;

public class Game {
    private boolean playerTurn; // ?
    private int piecesPlayed = 0; // max 30 game stops
    private int lastColumn = -1;
    private int lastRow = -1;
    private InnerBoard innerBoard;
    private Slot[][] gameBoard;
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Constructor
     */
    public Game() {
        innerBoard = new InnerBoard();
        gameBoard = innerBoard.getBoardArray();
    }

    /**
     * Add a piece to the boardArray
     */
    public void addPiece(int row, int column, Slot cellState) {
        log.debug("Attempting to adding piece: row ->" + row + " column->" + column);
        if (validatePiece(row, column, lastRow, lastColumn)) {
            innerBoard.add(row, column, cellState);
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
        if (gameBoard[row][column] == Slot.NOT_OCCUPIED && (row == lastRow || column == lastColumn)) {
            return true;
        } else {
            // Checks if there is a free adjacent cell that the player
            // Could have placed his piece in. If not, the move is valid.
            if (gameBoard[row + 1][column] == Slot.NOT_OCCUPIED || gameBoard[row - 1][column] == Slot.NOT_OCCUPIED
                    || gameBoard[row][column + 1] == Slot.NOT_OCCUPIED || gameBoard[row][column + 1] == Slot.NOT_OCCUPIED
                    || gameBoard[row][column + 1] == Slot.NOT_OCCUPIED || gameBoard[row - 1][column] == Slot.NOT_OCCUPIED) {
                return false;
            }

            return true;
        }
    }

    /**
     * Clears the gameBoard and restarts
     */
    public void endGame() {

    }

    /**
     * Checks the gameBoard with the new added piece.
     *
     * 1 point = 3 adjacent same colored pieces
     *
     */
    public int calculatePoints(int row, int column, Slot cellState) {
        log.debug("Calculating points...");
        int scoreCounter = 0;

        for (int i = 0; i <=2; i++) {
            // Check each row, if full +1 point each
            if (gameBoard[row + i][column] == cellState && gameBoard[row - i][column] == cellState) {
                scoreCounter++;
            }
            // Check each column, if full +1 point each
            if (gameBoard[row][column + i] == cellState && gameBoard[row][column + i] == cellState) {
                scoreCounter++;
            }
            // Check each diagonal, if full +1 point each
            if (gameBoard[row][column + i] == cellState && gameBoard[row - i][column] == cellState) {
                scoreCounter++;
            }
        }

        return scoreCounter;
    }

    /**
     * Find the next move for the computer
     * WIP
     */
    public void getNextMove(int lastUserRow, int lastUserColumn, Slot cellState) {
        int scoreHolder = 0;
        int calculatedHolder = 0;
        int bestRowHolder = -1;
        int bestColumnHolder = -1;

        if (validatePiece(lastUserRow + 1, lastUserColumn, lastRow, lastColumn)) {
            bestRowHolder = lastUserRow + 1;
            bestColumnHolder = lastUserColumn;
            calculatedHolder = calculatePoints(bestRowHolder,bestColumnHolder, cellState);
            if (calculatedHolder > scoreHolder) {
                scoreHolder = calculatedHolder;
            }
        }
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
