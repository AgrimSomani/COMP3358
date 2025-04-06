package client.ui;

import javax.swing.*;
import java.awt.*;

public class PlayGamePanel extends JPanel {
    public PlayGamePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0, 150, 0));

        // Player Name
        JLabel playerNameLabel = new JLabel("Kevin");
        playerNameLabel.setForeground(Color.WHITE);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        add(playerNameLabel, BorderLayout.NORTH);

        // Card Panel
        JPanel cardPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        cardPanel.setOpaque(false);
        String[] cardNames = { "A♠", "7♥", "3♣", "Q♦" };

        for (String cardName : cardNames) {
            JLabel cardLabel = new JLabel(cardName, SwingConstants.CENTER);
            cardLabel.setOpaque(true);
            cardLabel.setBackground(Color.WHITE);
            cardLabel.setForeground(Color.BLACK);
            cardLabel.setFont(new Font("Arial", Font.BOLD, 24));
            cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cardLabel.setPreferredSize(new Dimension(100, 150));
            cardPanel.add(cardLabel);
        }
        add(cardPanel, BorderLayout.CENTER);

        // Game Status
        JLabel gameStatusLabel = new JLabel("Win: 20:35 avg: 10:4s");
        gameStatusLabel.setForeground(Color.WHITE);
        gameStatusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gameStatusLabel.setHorizontalAlignment(JLabel.CENTER);
        add(gameStatusLabel, BorderLayout.SOUTH);
    }
}