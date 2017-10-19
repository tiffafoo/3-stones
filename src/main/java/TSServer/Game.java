package TSServer;

import org.slf4j.LoggerFactory;

public class Game {
    private int piecesPlayed = 0; // max 30 game stops
    private int lastColumn = -1;
    private int lastRow = -1;
    private InnerBoard innerBoard;
    private Slot[][] gameBoard;
    private int playerPoints = 0;
    private int compPoints = 0;
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
     * @param row
     * @param column
     * @param cellState
     */
    public void addPiece(int row, int column, Slot cellState) {
        // Either while loop or check with other side
        log.debug("Attempting to adding piece: row ->" + row + " column->" + column);

        // validate, calculate points and add to globals
        if (validatePiece(row, column, lastRow, lastColumn)) {
            innerBoard.add(row, column, cellState);
            lastColumn = column;
            lastRow = row;
            if (cellState == Slot.COMPUTER_MOVE) {
                compPoints += calculatePoints(row, column, cellState);
            } else {
                playerPoints += calculatePoints(row, column, cellState);
            }
        } else {
            // throw or something
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
            for (int i = 0; i < gameBoard.length; i++) {
                if (gameBoard[lastRow][i] == Slot.NOT_OCCUPIED || gameBoard[i][lastColumn] == Slot.NOT_OCCUPIED) {
                    return false;
                }
            }
            // User's move was valid because no space around last played piece
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
     * @param lastRow
     * @param lastColumn
     * @param cellState
     * @return 
     */
    public int calculatePoints(int lastRow, int lastColumn, Slot cellState) {
        log.debug("Calculating points...");
        int scoreCounter = 0;

        // Check each lastRow, if full +1 point each
        if (gameBoard[lastRow + 1][lastColumn] == cellState && gameBoard[lastRow - 1][lastColumn] == cellState) {
            scoreCounter++;
        }
        // Check each lastColumn, if full +1 point each
        if (gameBoard[lastRow][lastColumn + 1] == cellState && gameBoard[lastRow][lastColumn + 1] == cellState) {
            scoreCounter++;
        }
        // Check first diagonal, if full +1 point each
        if (gameBoard[lastRow + 1][lastColumn - 1] == cellState && gameBoard[lastRow - 1][lastColumn + 1] == cellState) {
            scoreCounter++;
        }

        // Check second diagonal
        if (gameBoard[lastRow - 1][lastColumn - 1] == cellState && gameBoard[lastRow + 1][lastColumn + 1] == cellState) {
            scoreCounter++;
        }

        // Check at 2nd other thingy left and right
        if (gameBoard[lastRow + 1][lastColumn] == cellState && gameBoard[lastRow + 2][lastColumn] == cellState) {
            scoreCounter++;
        }

        if (gameBoard[lastRow - 1][lastColumn] == cellState && gameBoard[lastRow - 2][lastColumn] == cellState) {
            scoreCounter++;
        }

        // Check 2nd top and bottom
        if (gameBoard[lastRow + 1][lastColumn + 1] == cellState && gameBoard[lastRow + 2][lastColumn + 2] == cellState) {
            scoreCounter++;
        }

        if (gameBoard[lastRow - 1][lastColumn - 1] == cellState && gameBoard[lastRow - 2][lastColumn - 2] == cellState) {
            scoreCounter++;
        }

        // Check 2nd diagonal
        if (gameBoard[lastRow + 1][lastColumn -1] == cellState && gameBoard[lastRow + 2][lastColumn - 2] == cellState) {
            scoreCounter++;
        }

        if (gameBoard[lastRow - 1][lastColumn + 1] == cellState && gameBoard[lastRow - 2][lastColumn + 2] == cellState) {
            scoreCounter++;
        }

        return scoreCounter;
    }

    /**
     * Find the next move for the computer
     * WIP
     * @param lastRow
     * @param lastColumn
     */
    public void getNextMove() 
    {
        int scoreHolder = 0;
        int calculatedHolder = -1;
        int bestRowHolder = -1;
        int bestColumnHolder = -1;
        Slot cellState = Slot.COMPUTER_MOVE;

        for(int i = 0; i < gameBoard.length; i++)
        {
            if (validatePiece(i, lastColumn, lastRow, lastColumn)) 
            {
                calculatedHolder = calculatePoints(bestRowHolder,bestColumnHolder, cellState);
                if (calculatedHolder > scoreHolder) 
                {
                    scoreHolder = calculatedHolder;
                    bestRowHolder = lastRow + 1;
                    bestColumnHolder = lastColumn;
                }
            }
            
            if (validatePiece(lastRow, i, lastRow, lastColumn)) 
            {
                calculatedHolder = calculatePoints(bestRowHolder,bestColumnHolder, cellState);
                if (calculatedHolder > scoreHolder) 
                {
                    scoreHolder = calculatedHolder;
                    bestRowHolder = lastRow + 1;
                    bestColumnHolder = lastColumn;
                }
            }
            
        }
        if(bestRowHolder != -1)
            addPiece(bestRowHolder, bestColumnHolder, cellState);
        else
            getRandomSpot();
    }
    
    public void getRandomSpot()
    {
    
    }
}
