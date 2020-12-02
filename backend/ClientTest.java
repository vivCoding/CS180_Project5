package backend;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
    * Client testing
    *
    * A dummie client program to ensure that
    * server works as intended
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class ClientTest {

    static Socket socket;

    static ObjectInputStream objectIn;
    static ObjectOutputStream objectOut;

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        String message;
        do {
            System.out.print("Send message to server: ");
            message = scan.nextLine();
            connectServer();
            objectOut.writeObject(message.split(" "));
            Object[] response = (Object[]) objectIn.readObject();
            disconnectServer();
            String status = (String) response[0];

            // modify this depending on what u want
            Account user = (Account) response[1];
            System.out.println(user.getFriends().size());
            System.out.println("\n");
        } while (!message.equals("closeClient"));

        scan.close();
    }

    static void connectServer() throws IOException, UnknownHostException {
        socket = new Socket("localhost", 4242);
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectIn = new ObjectInputStream(socket.getInputStream());
    }

    static void disconnectServer() throws IOException {
        socket.close();
        objectOut.close();
        objectIn.close();
    }
}
