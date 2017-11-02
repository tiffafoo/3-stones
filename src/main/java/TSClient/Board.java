package TSClient;

import TSServer.game.Slot;
import java.lang.reflect.Array;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for creating and filling the 7X7 array which will be displayed on the console
 * for the player to see any changes made to the inner board will be reflected on this board.
 * 
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 * 
 */
public class Board {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Scanner keyBoard = new Scanner(System.in);    
    private String[][] gameBoard = new String[7][7]; 
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    
    /**
     * No parameter constructor which calls the fillClientBoard method to fill the
     * 7X7 array.
     * 
     */
    public Board(){
        fillClientBoard();
    }    
    
    /**
     * Method which is responsible for setting up the initial 7X7 board which will
     * be displayed for the user to see.
     * 
     */
    public void fillClientBoard(){
        //First row
        fillSlots(0, 1, 0, 2, Slot.FORBIDDEN_SPACE);
        fillSlots(0, 1, 2, 5, Slot.NOT_OCCUPIED);
        fillSlots(0, 1, 5, 7, Slot.FORBIDDEN_SPACE);
        
        //Second row
        fillSlots(1, 2, 0, 1, Slot.FORBIDDEN_SPACE);
        fillSlots(1, 2, 1, 6, Slot.NOT_OCCUPIED);
        fillSlots(1, 2, 6, 7, Slot.FORBIDDEN_SPACE);
        
        //Third/Fourth/Fifth row
        fillSlots(2, 5, 0, 7, Slot.NOT_OCCUPIED);
        
        //Sixth row
        fillSlots(5, 6, 0, 1, Slot.FORBIDDEN_SPACE);
        fillSlots(5, 6, 1, 6, Slot.NOT_OCCUPIED);
        fillSlots(5, 6, 6, 7, Slot.FORBIDDEN_SPACE);
        
        //Seventh row
        fillSlots(6, 7, 0, 2, Slot.FORBIDDEN_SPACE);
        fillSlots(6, 7, 2, 5, Slot.NOT_OCCUPIED);
        fillSlots(6, 7, 5, 7, Slot.FORBIDDEN_SPACE);
        
        //Empty middle slot
        fillSlots(3, 4, 3, 4, Slot.FORBIDDEN_SPACE);
    }
    
    /**
     * Method which takes in a row start, row end, column start and column end
     * number to display the appropriate Slot value on the board. The positions
     * on the board will be updated using the values WALL for FORBIDDEN_SPACES
     * and BLANK for NOT_OCCUPIED SPACES.
     * 
     * @param xStart
     * @param xEnd
     * @param yStart
     * @param yEnd
     * @param value 
     */
    private void fillSlots(int xStart, int xEnd, int yStart, int yEnd, Slot value){
        for (int x = xStart; x < xEnd; x++){
            for (int y = yStart; y < yEnd; y++){
                gameBoard[x][y] = value.name();
            }
        }
    }
    
    /**
     * Method which will display the current version of the board to the console.
     * 
     */
    public void showClientBoard(){
        System.out.println("0|1|2|3|4|5|6|7|");
         for(int i = 0; i < gameBoard.length; i++){
            System.out.print(i + 1 + "|");
            for(int j = 0; j < gameBoard.length; j++)
            {
                if(gameBoard[i][j] == Slot.FORBIDDEN_SPACE.name())
                    gameBoard[i][j] = "X";
                else if(gameBoard[i][j] == Slot.NOT_OCCUPIED.name())
                    gameBoard[i][j] = "O";
                
                System.out.print(gameBoard[i][j] + "|");
            }
            System.out.println();
        }
    }
    
    /**
     * Method which is called when a change is made to the inner board, in order
     * to show the player how the board looks like in a 7X7 grid.
     * 
     * @param row
     * @param col
     * @param cellState 
     */
    public void changeBoardPiece(int row, int col, Slot cellState)
    {
        String move = "";
        if(cellState == Slot.COMPUTER_MOVE)
            move = "B";
        if(cellState == Slot.HUMAN_MOVE)
            move = "W";
        gameBoard[row][col] = ANSI_RED + move + ANSI_RESET;
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
        showClientBoard();
        System.out.println("Players turn!");
        //Get the player row and column choicce
        getPlayerMove();
    }
    
    /**
     * Method which will ask the user to enter a row and column and check if it 
     * is valid.
     * 
     */
    public byte[] getPlayerMove(){
        byte row, col;
        byte[] move = new byte[2];
        
        System.out.println("Please select a row: ");
        while (!keyBoard.hasNextInt())
            keyBoard.next();        
        row = keyBoard.nextByte();
        System.out.println("Please select a column: ");
        while (!keyBoard.hasNextInt())
            keyBoard.next();        
        col = keyBoard.nextByte();  
        
        move[0] = row;
        move[1] = col;
        
        return move;
    }

    public byte playSession(){
            //Ask the user if they want to play again. Enter y for yes, n for no.
            System.out.println("Do you want to play again? (y/n)");
            String userChoice = keyBoard.nextLine();

            //If they chose yes, start a new game, otherwise, exit the loop.
            if (userChoice.equalsIgnoreCase("y")){
                log.debug("Player says yes");
                return 1;
            }
            else{
                log.debug("Player says no");
                return 0;
            }
    }
    
    /**
     * Method which displays who won the game and how many points each player got.
     * 
     * @param playerScore
     * @param computerScore 
     */
    public void endGame(byte playerScore, byte computerScore){
        int pScore = 0, cScore = 0;
        pScore = (int) playerScore;
        cScore = (int) computerScore;
        System.out.println("Final Scores:\nPlayer Score: " + pScore + " | "
                + "Computer Score: " + cScore);
        
        if (pScore == cScore)
            System.out.println("Tie game");
        else if (pScore > cScore)
            System.out.println("Player wins");
        else
            System.out.println("Computer wins");
        
    }       
}

