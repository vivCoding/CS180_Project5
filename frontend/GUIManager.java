package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
    * GUI manager
    *
    * Responsible for showing GUIs
    *
    * @author Team 15-3 CS 180 - Merge
    * @version December 2, 2020
*/

public class GUIManager {


    public static int showStarting() {
        String[] options = { "Login", "Create New Account" };
        int result = JOptionPane.showOptionDialog(null, "Welcome to the Friends App!", "Friends App", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println(result);
        return result;
    }

    public static void showLogin() {

    }
}