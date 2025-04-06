package client;

import javax.swing.SwingUtilities;
import client.ui.LoginScreen;

public class Client {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
