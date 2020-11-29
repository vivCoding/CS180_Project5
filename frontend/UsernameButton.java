package frontend;

import javax.swing.*;
import backend.Account;

public class UsernameButton extends JButton {

    private String friendType;
    private Account account;

    public UsernameButton(String username, String friendType, Account account) {
        super(username);
        this.friendType = friendType; // {"friendRequest, requestedFriend, friend"}
        this.account = account;
    }

    public String getFriendType() {
        return friendType;
    }

    public void setFriendType(String friendType) {
        this.friendType = friendType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
