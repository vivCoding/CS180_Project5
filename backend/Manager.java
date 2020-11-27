package backend;

import java.util.ArrayList;

/**
    * Manager
    *
    * Acts as a "database", storing all accounts
    * and manages retrieval of specific accounts
    * The "model" of the entire system
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Manager {

    // the main list/database. Stores every single user
    private static ArrayList<Account> allUsers;

    public Manager() {
        allUsers = new ArrayList<Account>();
    }

    // getter method for all users
    public ArrayList<Account> getAllUsers() { return allUsers; }

    // getter method for a specific user
    public Account getUser(String username) {
        int i = findUser(username);
        if (i != -1) {
            return allUsers.get(i);
        }
        return null;
    }

    // creates a new account given info and adds it to our list/database
    // avoid duplicates of usernames and invalid username creation
    // return 1 if successful, -1 if invalid username, -2 if duplicate username
    public int createAccount (String username, String password, String email, String phoneNumber, String bio, String interests) {
        // check to make sure that username doesn't already exist in the list/database already
        int i = findUser(username);
        if (i == -1) {
            if (username.contains(" ")) return -1;
            Account newUser = new Account(username, password, email, phoneNumber, bio, interests);
            allUsers.add(newUser);
            return 1;
        }
        return -2;
    }

    // 1 if success, -1 if username could not be found
    public int updateAccount (String username, String email, String phoneNumber, String bio, String interests) {
        int i = findUser(username);
        if (i != -1) {
            allUsers.get(i).setEmail(email);
            allUsers.get(i).setPhoneNumber(phoneNumber);
            allUsers.get(i).setBio(bio);
            allUsers.get(i).setInterests(interests);
            return 1;
        }
        return -1;
    }

    // -1 if invalid username, -2 if new username exists
    // -3 if password is incorrect, -4 if username could not be found
    public int updateAccount(String username, String email, String phoneNumber, String bio, String interests, String currentPassword, String newUsername, String newPassword) {
        int i = findUser(username);
        if (i != -1) {
            if (newUsername.contains(" ")) {
                return -1;
            } else if (findUser(newUsername) != -1) {
                return -2;
            } else if (currentPassword.equals(allUsers.get(i).getPassword())) {
                allUsers.get(i).setEmail(email);
                allUsers.get(i).setPhoneNumber(phoneNumber);
                allUsers.get(i).setBio(bio);
                allUsers.get(i).setInterests(interests);
                return 1;
            }
            return -3;
            
        }
        return -4;
    }

    // removes user from our list/database
    // 1 if success, -1 if password incorrect, -2 if username not found
    public int deleteAccount(String username, String password) {
        // check to make sure that user exists in our list/database, and password is correct
        int i = findUser(username);
        if (i != -1 ) {
            if (allUsers.get(i).getPassword().equals(password)) {
                allUsers.remove(i);
                return 1;
            }
            return -1;
        }
        return -2;
    }

    // method to easily find users by username
    // if found, returns the index in list/database
    // if not found, return -1
    private int findUser(String username) {
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}
