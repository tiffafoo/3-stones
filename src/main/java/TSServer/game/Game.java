package TSServer.game;

import TSClient.Board;

import java.util.Random;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for the 3-Stones game logic. It will start a new game, 
 * get user input, validate user input, make the computer find the best choice 
 * move a piece and calculate total points.
 * 
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 * 
 */
public class Game{
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private int piecesPlayed = 0; // max 30 game stops
    private int lastColumn = -1;
    private int lastRow = -1;
    private Board clientBoard;
    private InnerBoard innerBoard;
    private Slot[][] gameBoard;
    private int playerPoints = 0;
    private int compPoints = 0;
    private Scanner keyBoard = new Scanner(System.in);    

    /**
     * Constructor
     */
    public Game(){
        log.debug("Game Constructor");
        clientBoard = new Board();
        innerBoard = new InnerBoard();
        gameBoard = innerBoard.getBoardArray();
    }

    /**
     * Get current computer score
     * @return points of score
     */
    public byte getCompPoints() {
        return (byte) compPoints;
    }

    /**
     * Get current player score
     * @return points of player
     */
    public byte getPlayerPoints() {
        return (byte) playerPoints;
    }

    /**
     * Add a piece to the boardArray
     * 
     * @param row
     * @param column
     * @param cellState
     */
    public boolean addPiece(int row, int column, Slot cellState) {
        // Either while loop or check with other side
        log.debug("Attempting to adding piece: row ->" + row + " column->" + column);

        // validate, calculate points and add to globals
        log.debug(cellState.name());
        if (validatePiece(row, column, lastRow, lastColumn)){
            innerBoard.add(row, column, cellState);
            //Add piece to the client board
            clientBoard.changeBoardPiece(row - 2, column - 2, cellState);
            lastColumn = column;
            lastRow = row;
            piecesPlayed++;
            
            if (cellState == Slot.COMPUTER_MOVE) 
                compPoints += calculatePoints(row, column, cellState);
            else 
                playerPoints += calculatePoints(row, column, cellState);
            
            clientBoard.showClientBoard();
            return true;
        } 
        else{
           // Computer will constantly look for a new piece until it's valid,
           // so if you are here, then the human made an invalid move, so 
           // let them try again.
           if(cellState == Slot.HUMAN_MOVE){
                System.out.println("Invalid move, please try again!");
                getPlayerMove();
                return false;
           }
           return true;
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
    public boolean validatePiece(int row, int column, int lastRow, int lastColumn){
        log.debug("Validating piece: row-> " + row + " column-> " + column + "\nlastRow-> " + lastRow + " lastColumn->" + lastColumn);        
        //Check if the it is the first move, check if the user placed it in the right spot.
        if (piecesPlayed == 0 && gameBoard[row][column] == Slot.NOT_OCCUPIED)
            return true;
        if (piecesPlayed != 0){
            // Slot is empty and either the row or column is the same
            if (gameBoard[row][column] == Slot.NOT_OCCUPIED && (row == lastRow || column == lastColumn)) 
                return true;
            else {
                // Checks if there is a free adjacent cell that the player
                // Could have placed his piece in. If not, the move is valid.
                for (int i = 0; i < gameBoard.length; i++){
                    if (gameBoard[lastRow][i] == Slot.NOT_OCCUPIED || gameBoard[i][lastColumn] == Slot.NOT_OCCUPIED) 
                        return false;
                }
                // User's move was valid because no space around last played piece
                return true;
            }
        }
        return false;
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
     * Method which will find the position on the board that is valid and will
     * produce the highest result. This will be the computers move. If no moves
     * are possible, find an empty valid position.
     * 
     */
    public void getNextMove(){
        int scoreHolder = 0;
        int calculatedHolder = -1;
        int bestRowHolder = -1;
        int bestColumnHolder = -1;
        Slot cellState = Slot.COMPUTER_MOVE;

        for(int i = 0; i < gameBoard.length; i++){
            if (validatePiece(i, lastColumn, lastRow, lastColumn)){
                calculatedHolder = calculatePoints(bestRowHolder,bestColumnHolder, cellState);
                if (calculatedHolder > scoreHolder){
                    scoreHolder = calculatedHolder;
                    bestRowHolder = lastRow + 1;
                    bestColumnHolder = lastColumn;
                }
            }
            
            if (validatePiece(lastRow, i, lastRow, lastColumn)){
                calculatedHolder = calculatePoints(bestRowHolder,bestColumnHolder, cellState);
                if (calculatedHolder > scoreHolder){
                    scoreHolder = calculatedHolder;
                    bestRowHolder = lastRow + 1;
                    bestColumnHolder = lastColumn;
                }
            }
            
        }
        if(bestRowHolder != -1){
            log.debug("Best Row: " + bestRowHolder + " Best Column: " + bestColumnHolder);
            addPiece(bestRowHolder, bestColumnHolder, cellState);
        }
        else
            getRandomSpot(cellState);
    }
    
    /**
     * Method which will randomly generate numbers for the row and column. If the
     * number is valid, add the piece to the board.
     * 
     * @param cellState 
     */
    public void getRandomSpot(Slot cellState){
        //Get a random number generator
        Random randomSpace = new Random();    
        //Make 2 variables to hold the random numbers. I set them to 0 because
        //at position [0,0] the space should always be FORBIDDEN_SPACE.                
        int rdmRowHolder = 0, rdmColumnHolder = 0;        
        
        //Keep finding a random spot until it is valid. Will only exit the loop when
        //The piece is valid.
        while (!validatePiece(rdmRowHolder, rdmColumnHolder, lastRow, lastColumn)){
            //Gets a random space between 0 and 6 (7X7 game board), which will then be validated
            rdmRowHolder = randomSpace.nextInt(6);
            rdmColumnHolder = randomSpace.nextInt(6);
        }
        //Will only arrive here if the random piece is valid, so add to board!
        log.debug("Random Row: " + rdmRowHolder + " Random Column: " + rdmColumnHolder);
        addPiece(rdmRowHolder, rdmColumnHolder, cellState);        
    }
        
    /**
     * Method which will begin the game and call the methods required for user 
     * input and to display the initial board.
     * Probably will be called when both the server and client are ready to play
     * 
     */
    public void startGame(){
        log.debug("Game Start");
        System.out.println("Welcome, here is the game board: \n");
        //Uses the client 7x7 board to display results
        clientBoard.showClientBoard();
        System.out.println("Players turn!");
        //Get the player row and column choicce
        getPlayerMove();
        
        //Computers turn!!!      
        //Rest of game
    }
    
    /**
     * Method which will ask the user to enter a row and column and check if it 
     * is valid.
     * 
     */
    private void getPlayerMove(){
        int row = 0, col = 0;
        System.out.println("Please select a row: ");
        while (!keyBoard.hasNextInt())
            keyBoard.next();        
        row = keyBoard.nextInt();
        System.out.println("Please select a column: ");
        while (!keyBoard.hasNextInt())
            keyBoard.next();        
        col = keyBoard.nextInt();
        
        addPiece(row + 2, col + 2, Slot.HUMAN_MOVE);         
    }
}