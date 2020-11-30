package frontend;

import javax.swing.*;
import backend.Account;

public class ActionButton extends JButton {

    private Account account;
    private final int actionType;
    /*
    0 - view profile
    1 - remove friend
    2 - cancel friend request
    3 - accept friend request
    4 - decline friend request
    5 - send friend request
     */

    public ActionButton(String action, Account account, int actionType) {
        super(action);
        this.account = account;
        this.actionType = actionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getActionType() {
        return actionType;
    }

}
