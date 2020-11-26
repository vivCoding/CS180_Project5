package backend;

import java.io.*;
import java.net.*;

/**
    * Server
    *
    * Handles the server creation, requests, and authentication
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
        /// server creation
        ServerSocket serverSocket = new ServerSocket(4242);
        System.out.println("Waiting for clients to connect...");
        // client connection
        Socket socket = serverSocket.accept();
        System.out.println("Client connected!");

        // instantiating our readers and writers
        // Request bodies we receive will be String[]
        // We send our status codes via the writer, and any object data via the objectWriter
        reader = new ObjectInputStream(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream());
        objectWriter = new ObjectOutputStream(socket.getOutputStream());

        // Starting up our database/manageer/model
        Manager manager = new Manager();

        String requestType = "";
        do {
            // read request and the request type
            // The first element in the array will always be the request type
            String[] requestBody = (String[]) reader.readObject();
            requestType = requestBody[0];
            // handle the request type, read parameters, call the appropriate functions
            boolean fieldsEmpty = false;
            for (String field : requestBody) {
                if (field.length() == 0) {
                    sendStatus("emptyFields");
                    fieldsEmpty = true;
                    break;
                }
            }
            if (!fieldsEmpty) {
                switch (requestType) {
                    case ("createAccount"):
                        int createStatus = manager.createAccount(requestBody[1], requestBody[2], requestBody[3], requestBody[4], requestBody[5], requestBody[6]);
                        if (createStatus == 1) {
                            sendStatus("success");
                            sendData(manager.getUser(requestBody[1]));
                        } else if (createStatus == -1) {
                            sendStatus("invalidUsername");
                        } else {
                            sendStatus("usernameExists");
                        }
                        break;
                    case ("updateAccount"):
                        if (requestBody.length == 6) {
                            int updateStatus = manager.updateAccount(requestBody[1], requestBody[2], requestBody[3], requestBody[4], requestBody[5]);
                            if (updateStatus == 1) {
                                sendStatus("success");
                                sendData(manager.getUser(requestBody[1]));
                            } else if (updateStatus == -1) {
                                sendStatus("usernameNotFound");
                            }
                        } else if (requestBody.length == 9) {
                            int updateStatus = manager.updateAccount(requestBody[1], requestBody[2], requestBody[3], requestBody[4], requestBody[5], requestBody[6], requestBody[7], requestBody[8]);
                            if (updateStatus == 1) {
                                sendStatus("success");
                                sendData(manager.getUser(requestBody[1]));
                            } else if (updateStatus == -1) {
                                sendStatus("invalidUsername");
                            } else if (updateStatus == -2) {
                                sendStatus("usernameExists");
                            } else if (updateStatus == -3) {
                                sendStatus("incorrectPassword");
                            } else {
                                sendStatus("usernameNotFound");
                            }
                        }
                        break;
                    case ("deleteAccount"):
                        int deleteStatus = manager.deleteAccount(requestBody[1], requestBody[2]);
                        sendStatus(deleteStatus == 1 ? "success" : (deleteStatus == -1 ? "incorrectPassword" : "usernameNotFound"));
                        break;
                    case ("loginUser"):
                        break;
                    case ("getUser"):
                        break;
                    case ("isFriendsWith"):
                        break;
                    case ("hasRequested"):
                        break;
                    case ("sendFriendRequest"):
                        break;
                    case ("cancelFriendRequest"):
                        break;
                    case ("acceptFriendRequest"):
                        break;
                    case ("declineFriendRequest"):
                        break;
                    case ("removeFriend"):
                        break;
                    default:
                        break;
                }
            }
        } while (!requestType.equals("close server")); // this is probably temporarily

        serverSocket.close();
        reader.close();
        writer.close();
    }

    public static void sendStatus (String message) {
        writer.write(message);
        writer.println();
        writer.flush();
    }

    public static void sendData (Object data) throws IOException {
        objectWriter.writeObject(data);
        objectWriter.flush();
    }

    public static void sendData (boolean data) throws IOException {
        objectWriter.writeBoolean(data);
        objectWriter.flush();
    }
}
