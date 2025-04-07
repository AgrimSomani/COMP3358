package client.ui;

import javax.swing.*;
import java.awt.*;

public class UserProfilePanel extends JPanel {
    public UserProfilePanel(String username) {
        setLayout(new GridBagLayout());
        setBackground(new Color(0, 150, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Profile Information Labels
        String[] profileInfo = {
                username,
                "Number of wins: 10",
                "Number of games: 20",
                "Average time to win: 12.5s",
                "Rank: #10"
        };

        for (String info : profileInfo) {
            JLabel label = new JLabel(info);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label, gbc);
        }
    }
}