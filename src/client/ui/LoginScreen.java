package client.ui;

import javax.swing.*;

import client.RMIClientFactory;

import java.awt.*;
import shared.AuthService;

public class LoginScreen extends JFrame {

    private JButton registerButton;
    private JButton loginButton;
    private JTextField loginNameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        // Set up the frame
        setTitle("JPoker 24-Game");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with green background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 150, 0)); // Dark green
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add to main panel
        mainPanel.add(createLoginPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        setupNavigationListeners();

        setLocationRelativeTo(null);
    }

    private JPanel createLoginPanel() {
        // Login Panel
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        loginPanel.setOpaque(false);

        // Login Name
        JLabel loginNameLabel = new JLabel("Login Name:");
        loginNameLabel.setForeground(Color.WHITE);
        loginNameField = new JTextField();
        loginNameField.setPreferredSize(new Dimension(200, 30));

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));

        loginPanel.add(loginNameLabel);
        loginPanel.add(loginNameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        return loginPanel;
    }

    private JPanel createButtonPanel() {
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        // Create buttons
        loginButton = createButton("Login");
        registerButton = createButton("Register");

        // Style buttons
        styleButton(loginButton);
        styleButton(registerButton);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        return buttonPanel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        return button;
    }

    // Helper method to style buttons
    private void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
    }

    private void setupNavigationListeners() {
        registerButton.addActionListener(e -> registerButtonClickHandler());
        loginButton.addActionListener(e -> loginButtonClickHandler());
    }

    private void registerButtonClickHandler() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            RegisterScreen registerScreen = new RegisterScreen();
            registerScreen.setVisible(true);
        });
    }

    private void clearFields() {
        loginNameField.setText("");
        passwordField.setText("");
    }

    private void loginButtonClickHandler() {
        try {
            // Get the username and password from the text fields
            String username = loginNameField.getText();
            String password = new String(passwordField.getPassword());

            // Call the login method on the AuthService
            AuthService authService = RMIClientFactory.getService(AuthService.class);
            boolean success = authService.login(username, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                SwingUtilities.invokeLater(() -> {
                    JPokerMainFrame mainFrame = new JPokerMainFrame(username);
                    mainFrame.setVisible(true);
                });
                return;
            }

            throw new Exception("Login failed: Please Try Again");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login failed. Please try again.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            clearFields();
        }
    }
}