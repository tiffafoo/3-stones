package TSServer.game;

/**
 * Class responsible for creating and filling the 11X11 array which will be keeping
 * track of all the moves the computer and play make. Used to simplify the calculation 
 * of moves and points.
 * 
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 * 
 */
public class InnerBoard{
    //Set the size of the array
    private Slot[][] boardArray = new Slot[11][11];

    // Initializes the board
    public InnerBoard(){        
        // First two columns (0, 1)
        fillSlots(0, 2, 0, 11, Slot.FORBIDDEN_SPACE);

        // Last two columns (9,10)
        fillSlots(9, 11, 0, 11, Slot.FORBIDDEN_SPACE);

        // Column 2
        fillSlots(2, 3, 0, 4, Slot.FORBIDDEN_SPACE);
        fillSlots(2, 3, 4, 7, Slot.NOT_OCCUPIED);
        fillSlots(2, 3, 7, 11, Slot.FORBIDDEN_SPACE);

        // Column 3
        fillSlots(3,4, 0, 3, Slot.FORBIDDEN_SPACE);
        fillSlots(3, 4, 3, 8, Slot.NOT_OCCUPIED);
        fillSlots(3, 4, 8, 11, Slot.FORBIDDEN_SPACE);

        // Column 4-5-6
        for (int i = 0; i <= 2; i++ ) {
            fillSlots(4+i, 5+i, 0, 2, Slot.FORBIDDEN_SPACE);
            fillSlots(4+i, 5+i, 2, 9, Slot.NOT_OCCUPIED);
            fillSlots(4+i, 5+i, 9, 11, Slot.FORBIDDEN_SPACE);
        }

        // Column 7
        fillSlots(7,8, 0, 3, Slot.FORBIDDEN_SPACE);
        fillSlots(7, 8, 3, 8, Slot.NOT_OCCUPIED);
        fillSlots(7, 8, 8, 11, Slot.FORBIDDEN_SPACE);

        // Column 8
        fillSlots(8, 9, 0, 4, Slot.FORBIDDEN_SPACE);
        fillSlots(8, 9, 4, 7, Slot.NOT_OCCUPIED);
        fillSlots(8, 9, 7, 11, Slot.FORBIDDEN_SPACE);

        // Middle empty forbidden
        boardArray[5][5] = Slot.FORBIDDEN_SPACE;
    }

    /**
     * Fills slots of the boardArray with
     * a slot value from a given start and end position
     * 
     * @param xStart (inclusive)
     * @param xEnd (non-inclusive)
     * @param yStart (inclusive)
     * @param yEnd (non-inclusive)
     * @param value
     */
    private void fillSlots(int xStart, int xEnd, int yStart, int yEnd, Slot value) {
        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                boardArray[x][y] = value;
            }
        }
    }

    /**
     * Method which returns the boardArray
     * 
     * @return 
     */
    public Slot[][] getBoardArray() {
        return boardArray;
    }

    /**
     * Method which will add the value to the selected position in the board array.
     * 
     * @param row
     * @param column
     * @param value 
     */
    public void add(int row, int column, Slot value) {
        boardArray[row][column] = value;
    }
}
