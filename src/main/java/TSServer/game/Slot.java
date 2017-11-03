package TSServer.game;

/**
 * Four possibilities
 * NOT_OCCUPIED -- Slot that has yet to be filled
 * FORBIDDEN_SPACE -- Slot that is unavailable to be filled
 * HUMAN_MOVE -- Slot that has been filled by the human player
 * COMPUTER_MOVE -- Slot that has been filled by our server (AI)
 *
 * For a better visualisation,
 * @see {https://docs.google.com/spreadsheets/d/1q4YJqO1bJM5GT5l1uWZM6o30g9JAq-dL5PLapqrUkqo/edit?usp=sharing}
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public enum Slot {
    NOT_OCCUPIED, COMPUTER_MOVE, HUMAN_MOVE, FORBIDDEN_SPACE
}
