package TSServer;

public class InnerBoard {
    private Slot[][] boardArray; // 11 x 11 with [-1, 0, 1, 2] or strings

    // Initializes the board
    public InnerBoard() {
        // First two columns (0, 1)
        fillSlots(0, 2, 0, 11, Slot.FORBIDDEN_SPACE);

        // Column 2
        fillSlots(2,3, 0, 3, Slot.FORBIDDEN_SPACE);
        fillSlots(2, 3, 3, 7, Slot.NOT_OCCUPIED);
        fillSlots(2, 3, 7, 11, Slot.FORBIDDEN_SPACE);

        // Column 3
        fillSlots(3,4, 0, 3, Slot.FORBIDDEN_SPACE);
        fillSlots(3, 4, 3, 8, Slot.NOT_OCCUPIED);
        fillSlots(3, 4, 8, 11, Slot.FORBIDDEN_SPACE);

        // Rows 4, 5, 6
        fillSlots(4, 7, 0, 1, Slot.FORBIDDEN_SPACE);
    }

    /**
     * Fills slots of the boardArray with
     * a slot value from a given start and end position
     * @param xStart
     * @param xEnd
     * @param yStart
     * @param yEnd
     * @param value
     */
    private void fillSlots(int xStart, int xEnd, int yStart, int yEnd, Slot value) {
        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                boardArray[x][y] = value;
            }
        }
    }

    public Slot[][] getBoardArray() {
        return boardArray;
    }

    public void add(int row, int column, Slot value) {
        boardArray[row][column] = value;
    }

}
