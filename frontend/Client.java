﻿package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.Flow;

import backend.Account;

/**
 *  Client class
 *  
 *  Connects to the server as a client instance
 *  Performs a login or account creation
 *  Loads a GUI to view friends and a profile
 *  
 *  @author Team 15-3 CS 180 - Merge
 *  @version November 26, 2020
 */

public class Client { //TODO create friend and profile menus, establish all server requests
	//user account variables
	private static String accountName;
	private static String pass;
	private static Account user;


	//static request arrays for requests with no parameters
	public static final String[] createSession = {"createSession"};
	public static final String[] closeSession = {"closeSession"};
	
	//io declaration
	public static Socket socket;
	public static BufferedReader reader;
	public static PrintWriter writer;
	public static ObjectInputStream objectInput;
	public static ObjectOutputStream objectOut;

	public static ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof UsernameButton) {
				UsernameButton clickedButton = (UsernameButton) e.getSource();
				String friendType = clickedButton.getFriendType();
				switch (friendType) {
					case "friend":
						
						break;
					case "friendRequest":

						break;
					case "requestedFriend":
						break;
				}
			}
		}
	};

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		
		//find the server info so that the server host can be remote, or on a different port
		String hostname = JOptionPane.showInputDialog(null, "Server Hostname", JOptionPane.QUESTION_MESSAGE);
		int port = Integer.parseInt(JOptionPane.showInputDialog(null, "Server Port", JOptionPane.QUESTION_MESSAGE));
		//connects to the server and shows confirmation
		socket = new Socket(hostname, port);
		JOptionPane.showInternalMessageDialog(null, "Successfully connected to server", "Connection Established", JOptionPane.INFORMATION_MESSAGE);
		
		//establishes IO method with server
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        objectInput = new ObjectInputStream(socket.getInputStream());
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        
        //creates server session
        objectOut.writeObject(createSession);
		
		boolean hasAccount = false;
		//creates a login or account creation request to send to the server
		String[] makeNew = {"Login", "Create new Account"};
		do {
			String response = (String) JOptionPane.showInputDialog(null, "Login Prompt", "", JOptionPane.QUESTION_MESSAGE, null, makeNew,
					makeNew[0]);
			accountName = JOptionPane.showInputDialog(null, "Account Name", JOptionPane.QUESTION_MESSAGE);
			pass = JOptionPane.showInputDialog(null, "Password", JOptionPane.QUESTION_MESSAGE);
			
			
			
			if (response.equals("Login")) {
				//send account name and pass to login
				String[] accountInfo = {"loginUser", accountName, pass};
				objectOut.writeObject(accountInfo);
				String success = reader.readLine();
				switch (success) {
				case "Success":
					hasAccount = true;
					user = (Account) objectInput.readObject();
					break;
				case "usernameNotFound":
					JOptionPane.showInternalMessageDialog(null, "Username does not exist", "User Error!", JOptionPane.ERROR_MESSAGE);
					break;
				case "incorrectPassword":
					JOptionPane.showInternalMessageDialog(null, "Incorrect Password", "Password Error!", JOptionPane.ERROR_MESSAGE);
				}
				
			
			} else {
				//send account name and pass and do account setup
				//section for contact info
				String email = JOptionPane.showInputDialog(null, "Please enter your email", JOptionPane.QUESTION_MESSAGE);
				String phone = JOptionPane.showInputDialog(null, "Please enter your phone number", JOptionPane.QUESTION_MESSAGE);
				
				//section to gain all other needed account info to create the Account
				String bio = JOptionPane.showInputDialog(null, "Please enter your bio", JOptionPane.QUESTION_MESSAGE);
				String interests = JOptionPane.showInputDialog(null, "Please enter some of your interests", JOptionPane.QUESTION_MESSAGE);
				
				String[] accountParams = {"createAccount", accountName, pass, email, phone, bio, interests};
				objectOut.writeObject(accountParams);
				String result = reader.readLine();
				switch (result) {
				case "success":
					JOptionPane.showInternalMessageDialog(null, "Successfully created account", "Account Created!", JOptionPane.INFORMATION_MESSAGE);
					user = (Account) objectInput.readObject();
					hasAccount = true;
					break;
				case "usernameExists":
					JOptionPane.showInternalMessageDialog(null, "Username already exists", "User Error!", JOptionPane.ERROR_MESSAGE);
					break;
				case "emptyFields":
					JOptionPane.showInternalMessageDialog(null, "You must fill every field to create an account", "User Error!", JOptionPane.ERROR_MESSAGE);
					break;
				}
				
			}
			
			// if connection is established, and account login is valid / valid account creation, then hasAccount becomes true.
		
		} while(!hasAccount);
		
		//at this point client is connected to server and logged in
		
		//base menu for client, when running the client is still running
		//once the Close option is chosen, the client closes all resources and terminates
		String[] menus = {"Friends", "Profile", "Search", "Close Client", "Delete Account"};
		
		
		do {
			String menuChoice = (String) JOptionPane.showInputDialog(null, "Home", "", JOptionPane.QUESTION_MESSAGE, null, menus,
				menus[0]);
			switch(menuChoice) {
			case "Friends":
				friendMenu();
				break;
			case "Profile": 
				ownProfileMenu();
				break;
			case "Search":
				searchMenu();
				break;
			case "Delete Account":
				int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?", "Confirmation Required", JOptionPane.YES_NO_OPTION);
				if (confirmation == JOptionPane.YES_OPTION) {
					String[] deleteAccount = {"deleteAccount", accountName, pass};
					objectOut.writeObject(deleteAccount);
					closeClient();
					return;
				}
			case "Close Client":
				//close all client resources here
				
				closeClient();
				return;
			} 
		
		} while(true);

	}

	public static void searchMenu() {

	}
	
	//method to open a window with the friend menu
	public static void friendMenu() {
		JFrame friendFrame = new JFrame();
		Container content = friendFrame.getContentPane();
		content.setLayout(new BorderLayout());

		JPanel headerGUI = new JPanel();
		JLabel friendRequestsHeader = new JLabel("Incoming Friend Requests", SwingConstants.CENTER);
		JLabel requestedFriendsHeader = new JLabel("Outgoing Friend Requests", SwingConstants.CENTER);
		JLabel friendsHeader = new JLabel("Friends", SwingConstants.CENTER);
		headerGUI.add(friendsHeader);
		headerGUI.add(friendRequestsHeader);
		headerGUI.add(requestedFriendsHeader);
		content.add(headerGUI, BorderLayout.NORTH);


		JPanel friendRequestsGUI = new JPanel(new FlowLayout());
		ArrayList<Account> friendRequestsList = user.getFriendRequests();
		if (friendRequestsList.size() != 0) {
			for (int i = 0; i < friendRequestsList.size(); i++) {
				UsernameButton eachFriendRequest = new UsernameButton(friendRequestsList.get(i).getUsername(),
						"friendRequest", friendRequestsList.get(i));
				eachFriendRequest.addActionListener(actionListener);
				friendRequestsGUI.add(eachFriendRequest);
			}
		} else {
			JLabel emptyList = new JLabel("You currently have no incoming friend requests.");
			friendRequestsGUI.add(emptyList);
		}
		content.add(friendRequestsGUI, BorderLayout.CENTER);

		JPanel requestedFriendsGUI = new JPanel(new FlowLayout());
		ArrayList<Account> requestedFriendsList = user.getRequestedFriends();
		if (requestedFriendsList.size() != 0) {
			for (int i = 0; i < requestedFriendsList.size(); i++) {
				UsernameButton eachRequestedFriend = new UsernameButton(requestedFriendsList.get(i).getUsername(),
						"requestedFriend", requestedFriendsList.get(i));
				eachRequestedFriend.addActionListener(actionListener);
				requestedFriendsGUI.add(eachRequestedFriend);
			}
		} else {
			JLabel emptyList = new JLabel("You currently have no outgoing friend requests.");
			requestedFriendsGUI.add(emptyList);
		}
		content.add(requestedFriendsGUI, BorderLayout.EAST);

		JPanel friendsGUI = new JPanel(new FlowLayout());
		ArrayList<Account> friendsList = user.getFriends();
		if (friendsList.size() != 0) {
			for (int i = 0; i < friendsList.size(); i++) {
				UsernameButton eachFriend = new UsernameButton(friendsList.get(i).getUsername(), "friend",
						friendsList.get(i));
				eachFriend.addActionListener(actionListener);
				friendsGUI.add(eachFriend);
			}
		} else {
			JLabel emptyList = new JLabel("You currently have no friends.");
			friendsGUI.add(emptyList);
		}
		content.add(friendsGUI, BorderLayout.WEST);

		friendFrame.setSize(600, 400);
		friendFrame.setLocationRelativeTo(null);
		friendFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // might need to remove this later
		friendFrame.setVisible(true);


		//sendFriendRequest goes here
		//cancelFriendRequest goes here
		//acceptFriendRequest goes here
		//declineFriendRequest goes here
		//removeFriend goes here
	}
	
	
	//method to open a window with the profile menu
	public static void ownProfileMenu() {
		JFrame profileFrame = new JFrame();
		//updateAccount goes here
	}
	
	public static void closeClient() throws IOException {
		objectOut.writeObject(closeSession);
		writer.close();
		reader.close();
		objectInput.close();
		objectOut.close();
		socket.close();
	}
	
	

}
