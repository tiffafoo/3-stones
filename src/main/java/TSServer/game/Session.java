package TSServer.game;

import java.util.Scanner;

public class Session
{
    private Scanner keyBoard;
    private Game tsGame;
    
    public Session()
    {
        tsGame = new Game();
    }
    
    public void playSession()
    {
        keyBoard = new Scanner(System.in);
        boolean playAgain = true;
        
        while(playAgain)
        {
            System.out.println("Do you want to play again? (y/n)");
            String userChoice = keyBoard.next();

            if(userChoice.equalsIgnoreCase("y"))
                tsGame.startGame();
            else
                playAgain = false;
        }        
    }
    
    // playSession(Socket s)
    // check end game
    // playAgain => boolean
    // gameover (Game)
}
