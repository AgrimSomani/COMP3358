package client.ui;

import javax.swing.*;
import java.awt.*;

public class LoginErrorDialog extends JDialog {
    public LoginErrorDialog(JFrame parent) {
        super(parent, "Error", true);

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

        // Error Icon Panel
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        iconPanel.setOpaque(false);
        JLabel errorIcon = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
        iconPanel.add(errorIcon);

        // Error Message Panel
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setOpaque(false);
        JLabel errorMessage = new JLabel("Login name should not be empty");
        errorMessage.setForeground(Color.WHITE);
        errorMessage.setFont(new Font("Arial", Font.BOLD, 14));
        messagePanel.add(errorMessage);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        JButton okButton = new JButton("OK");
        styleButton(okButton);
        okButton.addActionListener(e -> dispose());
        buttonPanel.add(okButton);

        // Add panels to main panel
        mainPanel.add(iconPanel, BorderLayout.NORTH);
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to dialog
        add(mainPanel);

        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    // Helper method to style buttons
    private void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setPreferredSize(new Dimension(100, 35));
    }
}