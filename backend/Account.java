import java.util.ArrayList;

/**
    * Account class
    *
    * Holds info about a user account/profile
    * Username, password, interests/bio, contact
    * and a list of current friends and friend requests
    *
    * @author Team 15-3, CS 180 - Merge
    * @version November 23, 2020
*/

public class Account {
    
    // account info
    private String username;
    private String password;

    // profile info
    private ContactInfo contactInfo;
    private String bio;
    private String interests;

    // friends is the user's current friends
    private ArrayList<Account> friends;
    // friendRequests is a list of friend requests that others have sent to this use
    private ArrayList<Account> friendRequests;
    // friendRequests is a list of friend requests sent by this user
    private ArrayList<Account> requestedFriends;

    public Account(String username, String password, String email, String phoneNumber, String bio, String interests) {
        this.username = username;
        this.password = password;
        this.contactInfo = new ContactInfo(email, phoneNumber);
        this.bio = bio;
        this.interests = interests;
        this.friends = new ArrayList<Account>();
        this.friendRequests = new ArrayList<Account>();
        this.requestedFriends = new ArrayList<Account>();
    }

    // getters and setters for account info
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    // getters and setters for profile info
    public ContactInfo getContactInfo() { return contactInfo; }
    public String getBio() { return bio; }
    public String getInterests() { return interests; }
    public void setBio(String bio) { this.bio = bio; }
    public void setInterests(String interests) { this.interests = interests; }

    // getters for friend requests
    public ArrayList<Account> getFriends() { return friends; }
    public ArrayList<Account> getFriendRequests() { return friendRequests; }
    public ArrayList<Account> getRequestedFriends() { return requestedFriends; }

    // checks whether or not a user is friends with this user
    public boolean isFriendsWith(Account user) {
        return userInList(user, friends) != -1;
    }

    // send a friend request to other user (adding to current requestedFriends)
    public void sendFriendRequest(Account user) {
        requestedFriends.add(user);
        user.friendRequests.add(this);
    }

    // accept or decline a friend request in friendRequests
    // if function successful, return 1. Else, return -1
    public int acceptDeclineFriendRequest(Account user, boolean accepting) {
        // check if we have the other user in our friend requests
        int i = userInList(user, friendRequests);
        // check if the other user has requested this user
        int j = userInList(this, user.requestedFriends);
        if (i != -1 && j != -1) {
            // if this user is accepting friend request, add the users and remove from request lists
            // else, if this user is declining, only remove from friend requests lists
            if (accepting) {
                friends.add(user);
                user.friends.add(this);
            }
            friendRequests.remove(i);
            user.requestedFriends.remove(j);
            return 1;
        }
        return -1;
    }

    // remove a friend request that this user has made
    public int cancelFriendRequest(Account user) {
        int i = userInList(user, requestedFriends);
        int j = userInList(this, friendRequests);
        if (i != -1 && j != -1) {
            requestedFriends.remove(i);
            user.friendRequests.remove(j);
            return 1;
        }
        return -1;
    }

    // "unfriend" and remove a friend from user's friends list
    public int removeFriend(Account user) {
        int i = userInList(user, friends);
        int j = userInList(this, user.friends);
        if (i != -1 && j != -1) {
            friends.remove(i);
            user.friends.remove(j);
            return 1;
        }
        return -1;
    }

    // method to ease the finding of a user in a list
    // for example, when we need to check if a user exists in our friends list
    // if found, returns the index in list
    // if not found, return -1
    private int userInList(Account user, ArrayList<Account> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).username.equals(user.username)) {
                return i;
            }
        }
        return -1;
    }
}
