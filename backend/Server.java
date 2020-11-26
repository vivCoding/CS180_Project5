package backend;

import java.io.*;
import java.net.*;

/**
    * Server
    *
    * Handles the server creation, requests, and authentication
    * The controller of the whole backend system
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Server {
    
    public static void main(String[] args) throws IOException {
        /// server creation
        ServerSocket serverSocket = new ServerSocket(4242);
        System.out.println("Waiting for clients to connect...");
        // client connection
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        // instantiating our readers and writers
        // We send our status codes via the writer, and any object data via the objectWriter
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        ObjectOutputStream objectWriter = new ObjectOutputStream(socket.getOutputStream());

        String message = "";
        do {
            // read any request codes from the client
            message = reader.readLine();
            System.out.println("Received from client: " + message);
        } while (!message.equals("close server")); // this is probably temporarily

        serverSocket.close();
        reader.close();
        writer.close();
    }

    public static void sendToClient (PrintWriter writer, String message) {
        writer.write(message);
        writer.println();
        writer.flush();
    }
}
