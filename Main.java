/* Project Assignment
Theme: Awareness
Topic: Health and Safety */

// Created by Puteri Nur Liyana Imanina Abdullah (100804) - Draft 1 (2025-05-06), Draft 2 (2025-06-03)
// Tested by Melisa Lai Wan Jiun (99663), 2025-05-11

import java.awt.*;
import javax.swing.*;

/* Main class for Health & Safety Learning Portal application. 
 * This class serves as the main entry and manages the user interface navigation
 * between different panels.
 */

public class Main extends JFrame {

    private UserStorage storage;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    //Panel names for navigation
    private static final String WELCOME_PANEL = "Welcome";
    private static final String SIGNUP_PANEL = "SignUp";
    private static final String LOGIN_PANEL = "Login";
    private static final String DASHBOARD_PANEL = "Dashboard";

    /* constructor initializes the application by setting up user storage and
     * initializing the GUI
     */

    public Main() {
        storage = new UserStorage();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Health & Safety Learning Portal");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //Use CardLayout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //Create all panels
        mainPanel.add(createWelcomePanel(), WELCOME_PANEL);
        mainPanel.add(createSignUpPanel(), SIGNUP_PANEL);
        mainPanel.add(createLoginPanel(), LOGIN_PANEL);

        add(mainPanel);

        //show welcome panel first
        cardLayout.show(mainPanel, WELCOME_PANEL);
    }

    // Creates the welcome/home panel with navigation options
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        panel.setBackground(new Color(245, 248, 252));

        // Title
        JLabel titleLabel = new JLabel("Health & Safety Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(0, 102, 204));

        // Subtitle components
        JLabel subtitleLabel = new JLabel("Learn. Practice. Stay Safe.");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(Color.GRAY);

        // Navigation Buttons 
        JButton signUpButton = createStyledButton("Sign Up", new Color(0, 153, 76));
        JButton loginButton = createStyledButton("Log In", new Color(0, 102, 204));
        JButton exitButton = createStyledButton("Exit", new Color(204, 0, 0));

        // Button actions
        signUpButton.addActionListener(_ -> cardLayout.show(mainPanel, SIGNUP_PANEL));
        loginButton.addActionListener(_ -> {
            if (storage.loadUsers().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No users found. Please sign up first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                cardLayout.show(mainPanel, LOGIN_PANEL);
            }
        });
        exitButton.addActionListener(_ -> {
            //Confirm before exiting appliation
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        //Add components
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(signUpButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(exitButton);

        return panel;
    }

    /* Creates the user registration panel with form fields and validation guidance.
     * Includes input fields for username, email and password with format requirements
     */
    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 248, 252));

        //Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Form fields
        JTextField usernameField = createStyledTextField("Username");
        JTextField emailField = createStyledTextField("Email");
        JPasswordField passwordField = createStyledPasswordField("Password");

        //Buttons
        JButton signUpButton = createStyledButton("Sign Up", new Color(0, 153, 76));
        JButton backButton = createStyledButton("Back", Color.GRAY);

        //Button actions
        signUpButton.addActionListener(_ -> handleSignUp(usernameField, emailField, passwordField));
        backButton.addActionListener(_ -> {
            clearFields(usernameField, emailField, passwordField);
            cardLayout.show(mainPanel, WELCOME_PANEL);
        });

        //Add components
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0,80)));
        panel.add(createFieldPanelWithGuidance("Username:", usernameField, "(ONLY Alphabet and Numbers are allowed)"));
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(createFieldPanelWithGuidance("Email:", emailField, "(@gmail.com)"));
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(createFieldPanelWithGuidance("Password:", passwordField, "(MUST INCLUDE Alphabet, Numbers, and @/ !/ &)"));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(signUpButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(backButton);

        return panel;
    }

    /* Creates the user login panel with username and password fields.
     * Includes options for password recovery and navigation back to welcome screen.
     */
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        panel.setBackground(new Color(245, 248, 252));
        
        // Title
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Form fields
        JTextField usernameField = createStyledTextField("Username");
        JPasswordField passwordField = createStyledPasswordField("Password");
        
        // Buttons
        JButton loginButton = createStyledButton("Log In", new Color(0, 102, 204));
        JButton forgotButton = createStyledButton("Forgot Password?", Color.ORANGE);
        JButton backButton = createStyledButton("Back", Color.GRAY);
        
        // Button actions
        loginButton.addActionListener(_ -> handleLogin(usernameField, passwordField));
        forgotButton.addActionListener(_ -> handleForgotPassword());
        backButton.addActionListener(_ -> {
            clearFields(usernameField, passwordField);
            cardLayout.show(mainPanel, WELCOME_PANEL);
        });
        
        // Add components
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(createFieldPanel("Username:", usernameField));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(createFieldPanel("Password:", passwordField));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(forgotButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(backButton);
        
        return panel;
    }

    /* Creates and displays the dashboard panel for autheticated users.
     * This method is called after successful login to show the main application features
     */
    public void showDashboard(String username) {
        // Create and show dashboard panel
        JPanel dashboardPanel = createDashboardPanel(username);
        if (dashboardPanel != null) {
            mainPanel.remove(dashboardPanel); // Remove old one
        }
        mainPanel.add(dashboardPanel, DASHBOARD_PANEL);
        cardLayout.show(mainPanel, DASHBOARD_PANEL);
        this.setVisible(true); // Show the main window again
    }

    /* Creates the main dashboard panel with access to all application modules.
     * This is the central hub where users can access learning materials, quizzes, scores,
     * achievements and logout functionalibility. 
     */
    private JPanel createDashboardPanel(String username) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 248, 252));
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("Health and Safety Learning Portal");
        descLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descLabel.setForeground(Color.GRAY);
        
        // Action buttons
        JButton learningButton = createStyledButton("Learning Module", new Color(0, 153, 76));
        JButton quizButton = createStyledButton("Quiz", new Color(255, 153, 0));
        JButton scoresButton = createStyledButton("Past Quiz Scores", new Color(102, 51, 153));
        JButton achievementButton = createStyledButton("Achievement", new Color(0, 0, 128));
        JButton logoutButton = createStyledButton("Logout", Color.GRAY);
        
        // Button actions
        learningButton.addActionListener(_ -> {
            learningModule module = new learningModule(this, username);
            this.setVisible(false); // Hide the main window
            module.setVisible(true);
        });
        
        quizButton.addActionListener(_ -> {
            takeQuizModule quiz = new takeQuizModule(); // Pass required parameters
            quiz.setMainApp(this);
            quiz.startQuiz(username); // Pass the username here
            
            QuizScore scoreManager = new QuizScore();

            quiz.setQuizCompleteListener((user, score, total) -> {
                scoreManager.saveScoreToFile(user, score, total);
            });
    
            this.setVisible(false); // Hide main window
            quiz.setVisible(true);  // Show quiz window
        });
        
        scoresButton.addActionListener(_ -> {
            // Display user's past quiz scores
            QuizScore scoreManager = new QuizScore();
            scoreManager.displayScoresFromFile(username);
        });
        
        achievementButton.addActionListener(_ -> {
            // Launch achievement/gamification module
            AchievementModule gamify = new AchievementModule(username, this);
            this.setVisible(false); // Hide main window first
            gamify.setVisible(true); // Then show achievement window
        });
        
        logoutButton.addActionListener(_ -> {
            // Confirm logout and return to welcome screen
            int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                cardLayout.show(mainPanel, WELCOME_PANEL);
            }
        });
        
        // Add components
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(descLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(learningButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(quizButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(scoresButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(achievementButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(logoutButton);
        
        return panel;
    }
    
    // To create a consistent styled button
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 40));
        button.setPreferredSize(new Dimension(300, 40));
        return button;
    }
    
    // To create a consistent styled text input field
    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(300, 30));
        field.setPreferredSize(new Dimension(300, 30));
        return field;
    }
    
    // To create a consistent styled password input field
    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(300, 30));
        field.setPreferredSize(new Dimension(300, 30));
        return field;
    }

    // To create a form field panel with label and input validation guidance text
    // Used in the sign-up form to provide users with input format requirements
    private JPanel createFieldPanelWithGuidance(String labelText, JComponent field, String guidanceText) { 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 248, 252));

        //Create a horizontal panel for label and guidance
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        labelPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel guidance = new JLabel(guidanceText);
        guidance.setFont(new Font("Arial", Font.ITALIC, 10));
        guidance.setForeground(new Color(100, 100, 100));

        labelPanel.add(label);
        labelPanel.add(guidance);

        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(labelPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);

        return panel;
    }
    
    /* To create a simple form field panel with label and input field.
     * Used in the login form and other places where guidance text is not needed.
     */
    private JPanel createFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 248, 252));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        
        return panel;
    }

    /* Handles the user registration process
     * Validates input fields, creates new user accounts and saves to storage.
     */
    private void handleSignUp(JTextField usernameField, JTextField emailField, JPasswordField passwordField) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        // Validate that all fields are filled
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Attempt to create a new user account
        User newUser = new User();
        
        if (newUser.signUp(username, email, password)) {
            //save successful registration to storage
            storage.saveUser(newUser);
            JOptionPane.showMessageDialog(this, 
                "Sign up successful! You can now log in.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            //Clear form fields and navigate to login panel
            clearFields(usernameField, emailField, passwordField);
            cardLayout.show(mainPanel, LOGIN_PANEL);
        } else {
            // Display error message for failed registration
            JOptionPane.showMessageDialog(this, 
                "Sign up failed. Please check your input and try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /* Handles the user login process.
     * Validates credential and navigates to dashboard on successful authentication
     */
    private void handleLogin(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        //Validate that both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both username and password.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Attempt to find user and authenticate
        User foundUser = storage.findUserByUsername(username);
        
        if (foundUser != null && foundUser.logIn(username, password)) {
            //Successful login - show success message
            JOptionPane.showMessageDialog(this, 
                "Login successful! Welcome, " + username + "!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // clear form fields
            clearFields(usernameField, passwordField);
            
            // Create and show dashboard
            JPanel dashboardPanel = createDashboardPanel(username);
            mainPanel.add(dashboardPanel, DASHBOARD_PANEL);
            cardLayout.show(mainPanel, DASHBOARD_PANEL);
        } else {
            //Failed login - show options dialog
            int choice = JOptionPane.showOptionDialog(this,
                "Login failed! Incorrect username or password.\nWhat would you like to do?",
                "Login Failed",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new String[]{"Try Again", "Forgot Password?", "Cancel"},
                "Try Again");
            
            if (choice == 1) { // Forgot Password
                handleForgotPassword();
            } else if (choice == 2) { // Cancel
                clearFields(usernameField, passwordField);
                cardLayout.show(mainPanel, WELCOME_PANEL);
            }
            // If choice == 0 (Try Again), do nothing - stay on login panel
        }
    }
    
    /* Handles password recovery functionality
     * Prompts user for email address and displays account information if found
    */
    private void handleForgotPassword() {
        // Prompt user for email address
        String email = JOptionPane.showInputDialog(this, 
            "Enter your registered email address:", 
            "Password Recovery", 
            JOptionPane.QUESTION_MESSAGE);
        
        // Process recovery request if email was provided
        if (email != null && !email.trim().isEmpty()) {
            // Attempt to recover account information by email
            String recoveredUsername = storage.recoverUsernameByEmail(email.trim());
            String recoveredPassword = storage.recoverPasswordByEmail(email.trim());
            
            if (recoveredUsername != null && recoveredPassword != null) {
                // Display recovered account information
                JOptionPane.showMessageDialog(this,
                    "Account found!\nUsername: " + recoveredUsername + "\nPassword: " + recoveredPassword,
                    "Account Recovery",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                // No account found with provided email
                JOptionPane.showMessageDialog(this,
                    "No account found with that email address.",
                    "Account Not Found",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearFields(JComponent... fields) {
        for (JComponent field : fields) {
            if (field instanceof JTextField) {
                ((JTextField) field).setText("");
            } else if (field instanceof JPasswordField) {
                ((JPasswordField) field).setText("");
            }
        }
    }

    /* Main method - application entry point
     * Creates and displays the main application window
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
