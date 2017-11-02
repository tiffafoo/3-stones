package TSServer.game;

import java.util.Scanner;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for creating the session and checking if the player wants 
 * to play another match. If the player does, create a new Game object and start
 * the game, otherwise, leave the while loop and end the session.
 * 
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 * 
 */
public class Session{
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Scanner keyBoard;
    boolean playAgain;
    String userChoice;

    /**
     * No parameter constructor which initializes the Scanner object, the choice
     * of the player and the boolean playAgain which will determine if the loop
     * should be exited or not.
     * 
     */
    public Session(){
        keyBoard = new Scanner(System.in);   
        playAgain = true;
        userChoice = "y";
    }
}
