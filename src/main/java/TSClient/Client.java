package TSClient;

import TSServer.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

// Given by teacher
public class Client
{

    public static void main(String[] args) throws IOException
    {
        // Create an instance of the server
        // start the server
        // TSServer server = bla
        Packet packet = new Packet();
        Board board = new Board();

        if ((args.length < 2) || (args.length > 3))	// Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <TSServer> <Word> [<Port>]");

        String server = args[0];					// TSServer name or IP address
        // Convert input String to bytes using the default character encoding
        byte[] byteBuffer = args[1].getBytes();

        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server...sending echo string");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(byteBuffer);						// Send the encoded string to the server

        // Receive the same string back from the server
        int totalBytesRcvd = 0;						// Total bytes received so far
        int bytesRcvd;								// Bytes received in last read
        while (totalBytesRcvd < byteBuffer.length)
        {
            if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,
                    byteBuffer.length - totalBytesRcvd)) == -1)
                throw new SocketException("Connection close prematurely");
            totalBytesRcvd += bytesRcvd;
            byte[] input = packet.read(socket);
            switch (input[0]) {
                case -1: //need to send a new move?
                    // continue;

                case 0: game = new Game();
                    break;
                case 1: //End game logic
                    break;
                case 2: game.addPiece(input[1], input[2], /*Cell state here */);
                    //return a message indicating that it worked or not
                    break;
                case 3: game.

        }

        System.out.println("Received: " + new String(byteBuffer));

        socket.close();								// Close the socket and its streams
    }
}