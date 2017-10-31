package TSServer.game;

import java.util.Scanner;
import org.slf4j.LoggerFactory;

public class Session
{
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private Scanner keyBoard;
    boolean playAgain;
    String userChoice;
    
    public Session()
    {
        keyBoard = new Scanner(System.in);   
        playAgain = true;
        userChoice = "y";
    }
    
    public void playSession()
    {
        //As long as the user wants to play, keep looping and replaying the game for the player.
        while(playAgain)
        {
            //Ask the user if they want to play again. Enter y for yes, n for no.
            System.out.println("Do you want to play again? (y/n)");
            userChoice = keyBoard.nextLine();
            
            //If they chose yes, start a new game, otherwise, exit the loop.
            if(userChoice.equalsIgnoreCase("y"))
            {
                log.debug("Player says yes");
                Game tsGame = new Game();
                tsGame.startGame();
            }
            else
            {
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
