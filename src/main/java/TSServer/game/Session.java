package TSServer.game;

import TSClient.Board;
import TSClient.Client;
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
    
    /**
     * Method which will ask the user if they want to play again. The players 
     * choice will either start a new game or end the session.
     * 
     */
    public void playSession(){
        //As long as the user wants to play, keep looping and replaying the game for the player.
        while (playAgain){
            //Ask the user if they want to play again. Enter y for yes, n for no.
            System.out.println("Do you want to play again? (y/n)");
            userChoice = keyBoard.nextLine();
            
            //If they chose yes, start a new game, otherwise, exit the loop.
            if (userChoice.equalsIgnoreCase("y")){
                log.debug("Player says yes");
                Game tsGame = new Game();
                Board b = new Board();
                b.startGame();
            }
            else{
                log.debug("Player says no");
                playAgain = false;
            }
        }        
    }
    // playSession(Socket s)
    // check end game
    // playAgain => boolean
    // gameover (Game)
}
