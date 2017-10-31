package TSClient;

import TSServer.game.Slot;

public class Board 
{
    private Slot[][] gameBoard = new Slot[7][7]; 
    
    public Board()
    {
        fillClientBoard();
    }
    
    //Call method to fill the 7x7 board
    public void fillClientBoard()
    {
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
    
    //Fill the slots of the 7x7 board 
    private void fillSlots(int xStart, int xEnd, int yStart, int yEnd, Slot value) 
    {
        for (int x = xStart; x < xEnd; x++)
        {
            for (int y = yStart; y < yEnd; y++) 
            {
                gameBoard[x][y] = value;
            }
        }
    }
    
    //Show the contents of the board
    public void showClientBoard()
    {
        for(int i = 0; i < gameBoard.length; i++)
        {
            for(int j = 0; j < gameBoard.length; j++)
                System.out.print(gameBoard[i][j] + " ");
            System.out.println();
        }
    }
    
    //Change a piece in the 7x7 board
    public void changeBoardPiece(int row, int col, Slot cellState) 
    {
        gameBoard[row][col] = cellState;
    }
    
    // 7 x 7 board for display
    // last played row and column
    // validate move client side
}

