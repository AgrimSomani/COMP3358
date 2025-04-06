package client.ui;

import javax.swing.*;

import client.RMIClientFactory;
import shared.AuthService;

import java.awt.*;

public class RegisterScreen extends JFrame {
    private JTextField loginNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterScreen() {
        // Set up the frame
        setTitle("JPoker 24-Game - Register");
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        mainPanel.add(createRegistrationPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        setupNavigationListeners();

        setLocationRelativeTo(null);
    }

    private JPanel createRegistrationPanel() {
        // Registration Panel
        JPanel registrationPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        registrationPanel.setOpaque(false);

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

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 30));

        registrationPanel.add(loginNameLabel);
        registrationPanel.add(loginNameField);
        registrationPanel.add(passwordLabel);
        registrationPanel.add(passwordField);
        registrationPanel.add(confirmPasswordLabel);
        registrationPanel.add(confirmPasswordField);

        return registrationPanel;
    }

    private JPanel createButtonPanel() {
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 35));
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 35));

        // Style buttons
        styleButton(registerButton);
        styleButton(cancelButton);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
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
        cancelButton.addActionListener(e -> cancelButtonClickHandler());
    }

    private void registerButtonClickHandler() {

        String loginName = loginNameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (loginName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Call the login method on the AuthService
            AuthService authService = RMIClientFactory.getService(AuthService.class);
            boolean success = authService.register(loginName, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Username may already exist.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while registering.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Clear the fields after registration attempt
        loginNameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    private void cancelButtonClickHandler() {
        dispose();
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}