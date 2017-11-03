package TSClient;

import TSServer.Packet;
import TSServer.game.Slot;
//import com.oracle.tools.packager.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Connects the server and the UI together.
 * Start this and the server to see the game running.
 *
 * @author Tiffany Le-Nguyen
 * @author Trevor Eames
 * @author Alessandro Ciotola
 */
public class Client {

    //Commented out to test the playSession
    public static void main(String[] args) throws IOException {

        Packet packet = new Packet();
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        Boolean keepPlaying = true;
        Boolean started = true;
        byte[] input = new byte[0];
        byte[] firstPlay = new byte[8];
        firstPlay[0] = 0;
        //Regex pattern for validating ip addrss
        final Pattern PATTERN = Pattern.compile(
                "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

        System.out.println("Hello, welcome to 3-stones. May I have the ip number of the server you wish to connect to?");
        String server = reader.next();
        while(!PATTERN.matcher(server).matches()){
            System.out.println("I need a valid IP address please.");
            server = reader.next();
        }

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, 50000);

        //Game loop
        while (keepPlaying) {
            if(input.length == 0)
                input = firstPlay;

            if(!started)
                input = packet.read(socket);
            started = false;

            byte[] playerMove;
            byte[] response = new byte[8];

                switch (input[0]) {
                    //Error received
                    case -1:
                        //Log.debug("ERROR");
                        keepPlaying = false;
                        break;
                    //New game starts here
                    case 0:
                        board = new Board();
                        response[0] = 1;
                        playerMove = board.startGame();
                        response[1] = playerMove[0];
                        response[2] = playerMove[1];
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        response[6] = 0;
                        response[7] = 0;
                        packet.write(response, socket);
                        break;
                    //Valid move was played and new piece played
                    case 1:
                        if(input[5] == 4) {
                            System.out.println("-------------------------");
                            System.out.println("That's an invalid move. Try again.");
                        }
                        if(input[3] != 0)
                            board.changeBoardPiece(input[3] - 1, input[4] - 1, Slot.HUMAN_MOVE);
                        if(input[1] != 0) {
                            board.changeBoardPiece(input[1] - 2, input[2] - 2, Slot.COMPUTER_MOVE);
                            System.out.println("-------------------------");
                            System.out.println("Black played: (" +(input[1] - 1) +", " + (input[2] - 1) + ")");
                            System.out.println("Scores:\nPlayer Score: " + input[6] + " | "
                + "Computer Score: " + input[7]);
                            System.out.println("White to play");
                        }
                        board.showClientBoard();
                        response[0] = 1;
                        playerMove = board.getPlayerMove();
                        response[1] = playerMove[0];
                        response[2] = playerMove[1];
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        response[6] = 0;
                        response[7] = 0;
                        packet.write(response, socket);
                        break;
                    //End game
                    case 2:
                        //Log.debug("End Game");
                        board.endGame(input[1], input[2]);
                        byte playAgain = board.playSession();
                        if (playAgain == 1) {
                            response[0] = 0;
                            response[1] = 0;
                            response[2] = 0;
                            response[3] = 0;
                            response[4] = 0;
                            response[5] = 0;
                            response[6] = 0;
                            response[7] = 0;

                        }
                        else
                            response[0] = 3;
                        response[1] = 0;
                        response[2] = 0;
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        response[6] = 0;
                        response[7] = 0;
                        packet.write(response, socket);
                        break;
                    //Restart the game and play first move
                    case 3:
                    	board = new Board();
                        response[0] = 1;
                        playerMove = board.startGame();
                        response[1] = playerMove[0];
                        response[2] = playerMove[1];
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        response[6] = 0;
                        response[7] = 0;
                        packet.write(response, socket);
                        break;
                    //Player invoked exit
                    case 4:
                        keepPlaying = false;
                        break;
                }
            }
        socket.close();         // Close the socket and its streams
    }
}