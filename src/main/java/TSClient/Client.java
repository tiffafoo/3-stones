package TSClient;

import TSServer.Packet;
import TSServer.game.Slot;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

// Given by teacher
public class Client {

    //Commented out to test the playSession
    public static void main(String[] args) throws IOException {

        Packet packet = new Packet();
        Board board = new Board();
        Scanner reader = new Scanner(System.in);
        Boolean keepPlaying = true;
        Boolean started = true;
        byte[] input = new byte[0];
        byte[] firstPlay = new byte[6];
        firstPlay[0] = 0;
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

        while (keepPlaying) {
            if(input.length == 0)
                input = firstPlay;

            if(!started)
                input = packet.read(socket);
            started = false;

            byte[] playerMove;
            byte[] response = new byte[6];

//            if(gameStarted == true) {
//                input = packet.read(socket);
//                System.out.println("Am I even reading?");
//            }
//
//            if(input.length != 0 || gameStarted == false) {
//                System.out.println("Input shit: " + input[0]);
                switch (input[0]) {
                    //Error received
                    case -1:
                        System.out.println("ERROR");
                        keepPlaying = false;
                        break;
                    //New game
                    case 0:
                        System.out.println("I'm in zero");
                        board = new Board();
                        board.showClientBoard();
                        response[0] = 1;
                        playerMove = board.getPlayerMove();
                        response[1] = playerMove[0];
                        response[2] = playerMove[1];
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        packet.write(response, socket);
                        break;
                    //Valid move was played and new piece played
                    case 1:
                        System.out.println("response"+input[0]+input[1]+input[2]+input[3]+input[4]);
                        if(input[3] != 0)
                            board.changeBoardPiece(input[3] - 1, input[4] - 1, Slot.HUMAN_MOVE);
                        if(input[1] != 0)
                            board.changeBoardPiece(input[1] - 2, input[2] - 2, Slot.COMPUTER_MOVE);
                        board.showClientBoard();
                        response[0] = 1;
                        playerMove = board.getPlayerMove();
                        response[1] = playerMove[0];
                        response[2] = playerMove[1];
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        packet.write(response, socket);
                        break;
                    //End game
                    case 2:
                        System.out.println("End Game");
                        board.endGame(input[1], input[2]);
                        byte playAgain = board.playSession();
                        if (playAgain == 1)
                            response[0] = 0;
                        else
                            response[0] = 3;
                        response[1] = 0;
                        response[2] = 0;
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        packet.write(response, socket);
                        break;
                    //Restart the game and play first move
                    case 3:
                        board = new Board();
                        board.startGame();
                        response[0] = 1;
                        playerMove = board.getPlayerMove();
                        response[1] = playerMove[0];
                        response[2] = playerMove[1];
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        packet.write(response, socket);
                        break;
                    //Make a new valid play
                    case 4:
                        System.out.println("Your move was invalid, please play again");
                        board.showClientBoard();
                        playerMove = board.getPlayerMove();
                        response[0] = 1;
                        response[1] = playerMove[0];
                        response[2] = playerMove[1];
                        response[3] = 0;
                        response[4] = 0;
                        response[5] = 0;
                        break;
                }
            }
        socket.close();         // Close the socket and its streams
    }
}