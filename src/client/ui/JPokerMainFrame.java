package client.ui;

import javax.swing.*;

import client.RMIClientFactory;
import shared.AuthService;

import java.awt.*;

public class JPokerMainFrame extends JFrame {
    private JPanel contentPanel;
    private JButton userProfileButton;
    private JButton playGameButton;
    private JButton leaderBoardButton;
    private JButton logoutButton;
    private String currentUsername;

    public JPokerMainFrame(String username) {

        currentUsername = username;

        // Frame setup
        setTitle("JPoker 24-Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel with Tab Bar
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Content Panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(0, 150, 0));
        add(contentPanel, BorderLayout.CENTER);

        // Initial screen (User Profile)
        showUserProfile();

        setLocationRelativeTo(null);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(0, 100, 0));

        // Navigation Buttons Panel
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navigationPanel.setBackground(new Color(0, 100, 0));

        // Create buttons
        userProfileButton = createTabButton("User Profile");
        playGameButton = createTabButton("Play Game");
        leaderBoardButton = createTabButton("Leader Board");

        // Logout Button (aligned to right)
        logoutButton = new JButton("Logout");
        styleLogoutButton(logoutButton);

        // Add buttons to navigation panel
        navigationPanel.add(userProfileButton);
        navigationPanel.add(playGameButton);
        navigationPanel.add(leaderBoardButton);

        // Add navigation panel to top panel
        topPanel.add(navigationPanel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        // Add action listeners
        setupNavigationListeners();

        return topPanel;
    }

    private JButton createTabButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 100, 0));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    private void styleLogoutButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setPreferredSize(new Dimension(100, 30));
    }

    private void setupNavigationListeners() {
        userProfileButton.addActionListener(e -> showUserProfile());
        playGameButton.addActionListener(e -> showPlayGame());
        leaderBoardButton.addActionListener(e -> showLeaderBoard());
        logoutButton.addActionListener(e -> logout());
    }

    private void showUserProfile() {
        contentPanel.removeAll();
        UserProfilePanel profilePanel = new UserProfilePanel(currentUsername);
        contentPanel.add(profilePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showPlayGame() {
        contentPanel.removeAll();
        PlayGamePanel gamePanel = new PlayGamePanel(currentUsername);
        contentPanel.add(gamePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showLeaderBoard() {
        contentPanel.removeAll();
        LeaderboardPanel leaderboardPanel = new LeaderboardPanel();
        contentPanel.add(leaderboardPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void logout() {
        try {
            AuthService authService = RMIClientFactory.getService(AuthService.class);
            if (authService.logout(currentUsername)) {
                System.out.println("Logout successful");
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.setVisible(true);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}