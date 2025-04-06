package client.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class LeaderboardPanel extends JPanel {
    public LeaderboardPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0, 150, 0));

        // Leaderboard Table
        String[] columnNames = { "Rank", "Player", "Games won", "Games played", "Avg. winning time" };
        Object[][] data = {
                { 1, "Player 4", 20, 35, 10.4 },
                { 2, "Player 2", 18, 25, 13.2 },
                { 3, "Player 6", 18, 31, 15.1 },
                { 4, "Player 8", 16, 30, 12.8 },
                { 5, "Player 7", 10, 25, 10.2 },
                { 6, "Player 5", 4, 7, 10.2 },
                { 7, "Player 9", 1, 10, null },
                { 8, "Player 1", 0, 0, null }
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable leaderboardTable = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Customize table appearance
        leaderboardTable.setRowHeight(25);
        leaderboardTable.setBackground(Color.WHITE);
        leaderboardTable.setForeground(Color.BLACK);

        // Customize table header
        JTableHeader header = leaderboardTable.getTableHeader();
        header.setBackground(new Color(0, 100, 0));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        // Scroll Pane for Table
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);
    }
}