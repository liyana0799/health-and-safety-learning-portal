// Created by Thian Shing Hui (101214) - Draft 1 (2025-05-25), Draft 2 (2025-06-08)
// Tested by Puteri Nur Liyana Imanina Abdullah (100804), 2025-06-13

// GUI-based interface that implements the Achievement interface
// displays the user achievements, rank, badges, points and leaderboard
import java.awt.*;
import javax.swing.*;

public class AchievementModule extends JFrame implements Achievement {
    private final AchievementDataManager dataManager;
    private final String username;
    private final Main mainApp;

    // Implements method from Achievement interface (returns user stats as String array)
    @Override
    public String[] calculateStats(String username) {
        UserStats stats = dataManager.calculateUserStats(username);
        return stats.toStringArray();
    }
    
    // Implements method from Achievement interface (makes the window visible)
    @Override
    public void displayRewards() {
        this.setVisible(true);
    }
    
    // Constructor with just username (no return to main window functionality)
    public AchievementModule(String username) {
        this(username, null);
    }

    // Constructor with username and reference to main app (for back button)
    public AchievementModule(String username, Main mainApp) {
        this.username = username;
        this.mainApp = mainApp;
        this.dataManager = new AchievementDataManager();
        
        initializeWindow(); // Window config
        createUI(); // Create UI layout
    }
    
    // Configure basic window properties
    private void initializeWindow() {
        setTitle("Achievement Center");
        setSize(360, 640);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 248, 252));
    }
    
    // Build the entire UI by assembling various components
    private void createUI() {
        JPanel mainPanel = createMainPanel();
        UserStats userStats = dataManager.calculateUserStats(username);
        java.util.List<LeaderboardEntry> leaderboardData = dataManager.getLeaderboardData();
        
        // Create UI components for the panel
        JPanel helloPanel = createHelloPanel(username);
        JPanel statsPanel = createStatsPanel(userStats);
        JLabel messageLabel = createMotivationalMessage();
        JPanel leaderboardHeaderPanel = createLeaderboardHeader();
        JScrollPane scrollPane = createLeaderboardTable(leaderboardData);
        JButton backButton = createBackButton();
        
        // Add components to main panel
        mainPanel.add(helloPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        mainPanel.add(statsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        mainPanel.add(leaderboardHeaderPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(backButton);
        
        add(mainPanel);
    }
    
    // Main vertical layout panel with padding
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 248, 252));
        return mainPanel;
    }
    
    // Greeting section at top
    private JPanel createHelloPanel(String username) {
        JPanel helloPanel = new JPanel(new GridBagLayout());
        helloPanel.setBackground(new Color(255, 255, 255));
        helloPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        GridBagConstraints helloGbc = new GridBagConstraints();
        helloGbc.gridx = 0;
        helloGbc.gridy = 0;
        helloGbc.insets = new Insets(0, 0, 10, 0);

        JLabel helloLabel = new JLabel("Hello, " + username + "!");
        helloLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        helloLabel.setForeground(new Color(0, 0, 128));
        helloPanel.add(helloLabel, helloGbc);

        helloGbc.gridy = 1;
        JLabel infoLabel = new JLabel("You're doing great — check out your achievements!");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(52, 73, 94));
        helloPanel.add(infoLabel, helloGbc);
        
        return helloPanel;
    }
    
    // Displays rank, badge and point data
    private JPanel createStatsPanel(UserStats userStats) {
        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setBackground(new Color(255, 255, 255));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        GridBagConstraints statsGbc = new GridBagConstraints();
        statsGbc.anchor = GridBagConstraints.WEST;
        statsGbc.gridx = 0;
        statsGbc.gridy = 0;
        statsGbc.insets = new Insets(5, 0, 5, 0);

        // Load and resize icons
        ImageIcon pointIcon = createResizedIcon("imageGamification/points.png");
        ImageIcon badgeIcon = createResizedIcon("imageGamification/badge.png");
        ImageIcon trophyIcon = createResizedIcon("imageGamification/trophy.jpg");

        Font infoFont = new Font("Segoe UI", Font.PLAIN, 12);
        
        JLabel statsLabel = new JLabel("My Achievements");
        statsLabel.setFont(infoFont.deriveFont(Font.BOLD));
        statsLabel.setForeground(new Color(0, 0, 128));
        statsLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(41, 128, 185)));
        statsPanel.add(statsLabel, statsGbc);
        statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statsGbc.gridy = 1;
        JLabel rankLabel = new JLabel("Rank: " + userStats.getRank(), trophyIcon, JLabel.LEFT);
        rankLabel.setFont(infoFont.deriveFont(Font.BOLD));
        rankLabel.setForeground(new Color(230, 126, 34));
        statsPanel.add(rankLabel, statsGbc);

        statsGbc.gridy = 2;
        JLabel badgesLabel = new JLabel("Badges: " + userStats.getBadge(), badgeIcon, JLabel.LEFT);
        badgesLabel.setFont(infoFont.deriveFont(Font.BOLD));
        badgesLabel.setForeground(new Color(155, 89, 182));
        statsPanel.add(badgesLabel, statsGbc);

        statsGbc.gridy = 3;
        JLabel pointsLabel = new JLabel("Points: " + userStats.getTotalPoints(), pointIcon, JLabel.LEFT);
        pointsLabel.setFont(infoFont.deriveFont(Font.BOLD));
        pointsLabel.setForeground(new Color(39, 174, 96));
        statsPanel.add(pointsLabel, statsGbc);
        
        return statsPanel;
    }
    
    // Builds leaderboard as a non-editable JTable
    private JScrollPane createLeaderboardTable(java.util.List<LeaderboardEntry> leaderboardData) {
        String[] columnNames = {"Rank", "Username", "Badge", "Points"};
        Object[][] tableData = new Object[leaderboardData.size()][4];
        
        for (int i = 0; i < leaderboardData.size(); i++) {
            LeaderboardEntry entry = leaderboardData.get(i);
            tableData[i][0] = i + 1;
            tableData[i][1] = entry.getUsername();
            tableData[i][2] = entry.getBadge();
            tableData[i][3] = entry.getPoints();
        }

        JTable table = new JTable(tableData, columnNames);
        table.setEnabled(false);
        table.setRowHeight(22);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 230, 240));
        table.getTableHeader().setBackground(new Color(0, 0, 128));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(174, 214, 241));
        
        // Alternating row background
        table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 140));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 240), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        return scrollPane;
    }
    
    // Utility to resize and load icons
    private ImageIcon createResizedIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        return new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
    }
    
    // Motivational text message
    private JLabel createMotivationalMessage() {
        JLabel messageLabel = new JLabel("Keep going, you're getting better every day!");
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        messageLabel.setForeground(new Color(127, 140, 141));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return messageLabel;
    }
    
    // Leaderboard title header
    private JPanel createLeaderboardHeader() {
        JPanel leaderboardHeaderPanel = new JPanel();
        leaderboardHeaderPanel.setBackground(new Color(245, 248, 252));
        JLabel leaderboardTitle = new JLabel("Leaderboard");
        leaderboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        leaderboardTitle.setForeground(new Color(0, 0, 128));
        leaderboardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardHeaderPanel.add(leaderboardTitle);
        return leaderboardHeaderPanel;
    }
    
    // Back button that returns to main app or closes the window
    private JButton createBackButton() {
        JButton backButton = createStyledButton("Back to Homepage", new Color(41, 128, 185));
        
        // Add hover effect
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(52, 152, 219));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(41, 128, 185));
            }
        });
        
        backButton.addActionListener(_ -> {
            if (mainApp != null) {
                this.setVisible(false);
                mainApp.setVisible(true);
            } else {
                this.dispose(); // close window if no mainApp is passed
            }
        });
        
        return backButton;
    }
    
    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 40));
        button.setPreferredSize(new Dimension(300, 40));
        return button;
    }

    // Launch version without returning to homepage
    public void launchAchievement(String username) {
        SwingUtilities.invokeLater(() -> {
            AchievementModule gui = new AchievementModule(username);
            gui.setVisible(true);
        });
    }

    // Launch version that returns to homepage on close
    public void launchAchievement(String username, Main mainApp) {
        SwingUtilities.invokeLater(() -> {
            AchievementModule gui = new AchievementModule(username, mainApp);
            gui.setVisible(true);
        });
    }
}