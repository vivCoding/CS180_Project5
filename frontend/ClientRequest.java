package frontend;

import java.io.*;
import java.net.*;

/**
    * ClientRequests class
    *
    * Stores functions of all requests necessary
    *
    * @author Team 15-3 CS 180 - Merge
    * @version December 2, 2020
*/

public class ClientRequest {

    // io declaration
    private static Socket socket;
    private static ObjectOutputStream writer;
    private static ObjectInputStream reader;

    // server connection constants
    private static final String serverHost = "localhost";
    private static final int serverPort = 4242;

    // try to send data. If connection failed, return connectionFailed status code
    public static Object[] sendToServer(Object[] request) {
        try {
            socket = new Socket(serverHost, serverPort);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            writer.writeObject(request);
            Object[] response = (Object[]) reader.readObject();

            socket.close();
            reader.close();
            writer.close();
            return response;
        } catch (UnknownHostException e) {
            return new String[] { "connectionFailed" };
        } catch (IOException e) {
            return new String[] { "connectionFailed" };
        } catch (ClassNotFoundException e) {
            return new String[] { "connectionFailed" };
        } finally {
            try {
                socket.close();
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
