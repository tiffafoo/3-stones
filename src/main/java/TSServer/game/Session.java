package TSServer.game;

import java.util.Scanner;

public class Session
{
    private Scanner keyBoard;
    
    public Session()
    {        
    }
    
    public void playSession()
    {
        keyBoard = new Scanner(System.in);
        boolean playAgain = true;
        
        //As long as the user wants to play, keep looping and replaying the game for the player.
        while(playAgain)
        {
            //Ask the user if they want to play again. Enter y for yes, n for no.
            System.out.println("Do you want to play again? (y/n)");
            String userChoice = keyBoard.next();

            //If they chose yes, start a new game, otherwise, exit the loop.
            if(userChoice.equalsIgnoreCase("y"))
            {
                Game tsGame = new Game();
                tsGame.startGame();
            }
            else
                playAgain = false;
        }        
    }
    
    // playSession(Socket s)
    // check end game
    // playAgain => boolean
    // gameover (Game)
}
