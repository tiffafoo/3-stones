package TSClient;

import TSServer.game.Slot;

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
    private Slot[][] gameBoard = new Slot[7][7]; 
    
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
        fillSlots(0, 1, 0, 2, Slot.WALL);
        fillSlots(0, 1, 2, 5, Slot.BLANK);
        fillSlots(0, 1, 5, 7, Slot.WALL);
        
        //Second row
        fillSlots(1, 2, 0, 1, Slot.WALL);
        fillSlots(1, 2, 1, 6, Slot.BLANK);
        fillSlots(1, 2, 6, 7, Slot.WALL);
        
        //Third/Fourth/Fifth row
        fillSlots(2, 5, 0, 7, Slot.BLANK);
        
        //Sixth row
        fillSlots(5, 6, 0, 1, Slot.WALL);
        fillSlots(5, 6, 1, 6, Slot.BLANK);
        fillSlots(5, 6, 6, 7, Slot.WALL);
        
        //Seventh row
        fillSlots(6, 7, 0, 2, Slot.WALL);
        fillSlots(6, 7, 2, 5, Slot.BLANK);
        fillSlots(6, 7, 5, 7, Slot.WALL);
        
        //Empty middle slot
        fillSlots(3, 4, 3, 4, Slot.WALL);
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
                gameBoard[x][y] = value;
            }
        }
    }
    
    /**
     * Method which will display the current version of the board to the console.
     * 
     */
    public void showClientBoard(){
        for (int i = 0; i < gameBoard.length; i++){
            for (int j = 0; j < gameBoard.length; j++)
                System.out.print(gameBoard[i][j] + " ");
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
    public void changeBoardPiece(int row, int col, Slot cellState){
        gameBoard[row][col] = cellState;
    }
}

