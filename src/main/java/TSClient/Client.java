package TSClient;

import TSServer.Packet;
import TSServer.game.Slot;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

// Given by teacher
public class Client {

    //Commented out to test the playSession
    public static void main(String[] args) throws IOException {

        Packet packet = new Packet();
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        Boolean keepPlaying = true;
        byte[] input = new byte[0];
        byte[] firstPlay = new byte[5];
        firstPlay[0] = 0;

        System.out.println("Hello, welcome to 3-stones. May I have the ip number of the server you wish to connect to?");

        String server = reader.next();

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, 50000);

        while (keepPlaying) {
            System.out.println("in the loop");
            //input = packet.read(socket);
            System.out.println("Past the read");
            System.out.println(firstPlay[0]);
            if(input.length == 0)
                input = firstPlay;
            System.out.println(input[0]);
            byte[] playerMove;
            byte[] response = new byte[10];

            switch (input[0]) {
                case -1:
                    System.out.println("ERROR");
                    keepPlaying = false;
                    break;
                case 0:
                    board = new Board();
                    board.showClientBoard();
                    response[0] = 2;
                    playerMove = board.getPlayerMove();
                    response[1] = playerMove[0];
                    System.out.println(playerMove[0]);
                    response[2] = playerMove[1];
                    System.out.println(playerMove[0]);
                    response[3] = 0;
                    response[4] = 0;
                    packet.write(response, socket);
                    break;
                case 1: board.endGame(input[1], input[2]);
                    byte playAgain = board.playSession();
                    if(playAgain == 1)
                        response[0] = 0;
                    else
                        response[0] = 3;
                    response[1] = 0;
                    response[2] = 0;
                    response[3] = 0;
                    response[4] = 0;
                    packet.write(response, socket);
                    break;
                case 2:
                    board.changeBoardPiece(input[1], input[2], Slot.COMPUTER_MOVE);
                    board.changeBoardPiece(input[3], input[4], Slot.HUMAN_MOVE);
                    response[0] = 2;
                    playerMove = board.getPlayerMove();
                    response[1] = playerMove[0];
                    response[2] = playerMove[1];
                    response[3] = 0;
                    response[4] = 0;
                    packet.write(response, socket);
                    break;
                case 3: board = new Board();
                    board.startGame();
                    response[0] = 2;
                    playerMove = board.getPlayerMove();
                    response[1] = playerMove[0];
                    response[2] = playerMove[1];
                    response[3] = 0;
                    response[4] = 0;
                    packet.write(response, socket);
                    break;
                case 4: System.out.println("Your move was invalid, please play again");
                    playerMove = board.getPlayerMove();
                    response[0] = 2;
                    response[1] = playerMove[0];
                    response[2] = playerMove[1];
                    response[3] = 0;
                    response[4] = 0;

            }

            socket.close();                                // Close the socket and its streams
        }
    }
}