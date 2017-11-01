package TSServer.game;

/**
 * Four possibilities
 * BLANK and WALL will be what is displayed inside of the Client board rather than 
 * having NOT_OCCUPIED and FORBIDDEN_SPACE (Can be replaced with anything)
 * 
 */
public enum Slot {
    NOT_OCCUPIED, COMPUTER_MOVE, HUMAN_MOVE, FORBIDDEN_SPACE
}
