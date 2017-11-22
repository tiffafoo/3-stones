package TSServer;

import TSServer.game.Game;
import TSServer.game.Slot;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public class Session {

    private Game game;
    private boolean started = true;
    private boolean playAgain = true;
    private Packet packet = new Packet();

    public void playSession(Socket clntSock) throws IOException 
    {
        while (playAgain) 
        {
            byte[] response = new byte[8];
            byte[] input = packet.read(clntSock);
            if (started) {
                input[0] = 0;
                started = false;
            }

            if (!(input.length == 0)) {
                switch (input[0]) {
                    //Start a new game
                    case 0:
                        game = new Game();
                        //Log.debug("Starting a new game here");
                        response[0] = 0;
                        response[1] = 0;
                        response[2] = 0;
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 1;
                        response[6] = 0;
                        response[7] = 0;
                        packet.write(response, clntSock);
                        break;
                    //Validate player move. If good, play it, else respond with invalid see 2nd else
                    case 1:
                        System.out.println("Pieces played: " + game.getPiecesPlayed());
                        boolean validate = game.addPiece(input[1], input[2], Slot.HUMAN_MOVE);
                        if (validate) {
                            if (game.getPiecesPlayed() >= 30) {
                                response[0] = 2;
                                response[1] = game.getPlayerPoints();
                                response[2] = game.getCompPoints();
                                response[3] = 0;
                                response[4] = 0;
                                response[5] = 1;
                                response[6] = 0;
                                response[7] = 0;
                            } else {
                                response[0] = 1;
                                byte[] compMove = game.getNextMove();
                                response[1] = compMove[0];
                                response[2] = compMove[1];
                                response[3] = input[1];
                                response[4] = input[2];
                                response[5] = 1;
                                response[6] = game.getPlayerPoints();
                                response[7] = game.getCompPoints();
                            }
                            packet.write(response, clntSock);
                            break;
                        } else {
                            response[0] = 1;
                            response[1] = 0;
                            response[2] = 0;
                            response[3] = 0;
                            response[4] = 0;
                            response[5] = 4;
                            response[6] = 0;
                            response[7] = 0;
                            packet.write(response, clntSock);
                        }
                        break;
                    //Game ended naturally
                    case 2:
                        response[0] = 1;
                        response[1] = game.getPlayerPoints();
                        response[2] = game.getCompPoints();
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 1;
                        response[6] = 0;
                        response[7] = 0;
                        packet.write(response, clntSock);
                        break;
                    //Player does not want to play again
                    case 3:
                        playAgain = false;
                        clntSock.close();
                        break;
                }
            }
        }
    }
}
