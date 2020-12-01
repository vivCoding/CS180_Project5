package backend;

import java.io.*;
import java.net.*;

/**
    * Server
    *
    * Handles the server creation, session creation,
    * and requests, and authentication (through session threads)
    * The "controller" of the whole backend system
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Server {
    
    static ObjectInputStream reader;
    static PrintWriter writer;
    static ObjectOutputStream objectWriter;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // server creation
        ServerSocket serverSocket = new ServerSocket(4242);
        System.out.println("Server running on port 4242");

        // Starting up our database/manageer/model
        Manager manager = new Manager();
        manager.createAccount("bob", "password", "bob@email.com", "number", "this is my bio yey", "pizza, sports, sleeping");
        manager.createAccount("sam", "password", "sam@purdue.edu", "number", "bio", "my interests bobob");

        while (true) {
            // client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            new ServerThread(socket, manager).start();
        }
    }
}
