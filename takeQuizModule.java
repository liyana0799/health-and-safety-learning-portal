// Created by Puteri Nur Liyana Imanina Abdullah (100804) - Draft 1 (2025-05-06)
// Created by Albertina Ngu Yan Si (98025) - Draft 2 (2025-05-12) , Draft 3 (2025-05-25 by)
// Created & Debugged by Thian Shing Hui (101214) - Draft 4 (2025-06-08)
// Debugged by Albertina Ngu Yan Si (98025) - Draft 5 (2025-06-10)

/**
 * The takeQuizModule class implements Java Swing GUI for quiz presentation,
 * handling user interactions, screen, screen navigation, and delegating business logic
 * to QuizService while maintaining UI state and visual feedback.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class takeQuizModule extends JFrame implements QuizCompleteListener {

    // Store user info and UI components
    private String username;
    private QuizCompleteListener quizCompleteListener;
    private Main mainApp;
    private QuizScore quizScore = new QuizScore();
    
    // Quiz service instance
    private QuizService quizService;
    
    // UI Components
    private final JPanel mainPanel;
    private JTextPane questionTextPane;
    private JButton optionA, optionB, optionC;
    private JButton nextButton;
    private final JButton trueButton = new JButton("True");
    private final JButton falseButton = new JButton("False");
    private final JTextField fillBlankTextField = new JTextField();

    // Constructor to initialize the quiz module for a specific user
    public takeQuizModule(String username) {
        this.username = username;
        this.mainApp = null;
        this.quizService = new QuizService(QuizService.QuizType.ALL_QUESTIONS);
    
        // JFrame setup
        setTitle("Health and Safety Quiz");
        setSize(360, 640);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        // Create and configure the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        mainPanel.setBackground(new Color(245, 243, 255));

        // Initialize components
        initializeComponents();
    
        // Show initial screen
        showInitialScreen();
    
        add(mainPanel, BorderLayout.CENTER);
    }

    public takeQuizModule() {
        this("");
    }

    // Called when the quiz is completed to trigger result handling
    @Override
    public void onQuizComplete(String username, int score, int totalQuestions) {
        System.out.println("Quiz completed by " + username + " with score: " + score + "/" + totalQuestions);
        
        if (quizService.getCurrentQuizType() == QuizService.QuizType.ALL_QUESTIONS) {
        if (quizCompleteListener != null && quizCompleteListener != this) {
            quizCompleteListener.onQuizComplete(username, score, totalQuestions);
        }
    }
}

    // Initializes the reusable UI components
    private void initializeComponents() {

        // Question text display
        questionTextPane = new JTextPane();
        questionTextPane.setEditable(false);
        questionTextPane.setFont(new Font("Arial", Font.BOLD, 18));
        questionTextPane.setOpaque(false);
        questionTextPane.setMaximumSize(new Dimension(320, 100));
        questionTextPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // MCQ buttons
        optionA = createOptionButton();
        optionB = createOptionButton();
        optionC = createOptionButton();

        // Style and handle true/false buttons
        styleTFButton(trueButton);
        trueButton.addActionListener(_ -> handleTFAnswer(trueButton));

        styleTFButton(falseButton);
        falseButton.addActionListener(_ -> handleTFAnswer(falseButton));

        // Text field for fill-in-the-blank questions
        fillBlankTextField.setMaximumSize(new Dimension(300, 40));
        fillBlankTextField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Next button to go to next question
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.setBackground(new Color(148, 87, 235));
        nextButton.setForeground(Color.BLACK);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(new LineBorder(new Color(148, 87, 235), 2, true));
        nextButton.setOpaque(true);
        nextButton.setPreferredSize(new Dimension(300, 45)); // Wider and taller
        nextButton.setMaximumSize(new Dimension(300, 45));
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(_ -> {
            // Input validation
            if (quizService.isCurrentQuestionFillBlank()) {
                String userInput = fillBlankTextField.getText().trim();
                if (userInput.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter an answer before continuing.");
                    return;
                }
                quizService.setFillBlankAnswer(userInput);
            } else if (!quizService.isAnswered()) {
                JOptionPane.showMessageDialog(this, "Please select an answer before continuing.");
                return;
            }
            nextQuestion();
        });
    }

    // Displays the quiz type selection screen
    private void showInitialScreen() {
        mainPanel.removeAll();
        
        JLabel titleLabel = new JLabel("Quiz Module");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("Ready to begin? Try a short practice first!");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setForeground(Color.GRAY);

        // Create quiz type buttons
        JButton trueFalseButton = createStyledButton("True/False Questions", new Color(255, 140, 0));
        trueFalseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton mcqButton = createStyledButton("MCQ Questions", new Color(255, 140, 0));
        mcqButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton fillBlankButton = createStyledButton("Fill in Blank Questions", new Color(255, 140, 0));
        fillBlankButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel4 = new JLabel("Warning: No exit after questions begin.");
        infoLabel4.setFont(new Font("Arial", Font.ITALIC, 10));
        infoLabel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel4.setForeground(Color.RED);

        JLabel infoLabel2 = new JLabel("Up for a challenge? Full Quiz time!");
        infoLabel2.setFont(new Font("Arial", Font.ITALIC, 14));
        infoLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel2.setForeground(Color.GRAY);

        JButton allQuestionsButton = createStyledButton("Full Quiz", new Color(230, 74, 25));
        allQuestionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel infoLabel3 = new JLabel("Note: Leaderboard is for Full Quiz only.");
        infoLabel3.setFont(new Font("Arial", Font.ITALIC, 10));
        infoLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel3.setForeground(Color.RED);

        JLabel infoLabel5 = new JLabel("Warning: No exit after quiz starts.");
        infoLabel5.setFont(new Font("Arial", Font.ITALIC, 10));
        infoLabel5.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel5.setForeground(Color.RED);

        JButton backButton = createStyledButton("Back to Homepage", new Color(41, 128, 185));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Action: Start True/False only quiz
        trueFalseButton.addActionListener((ActionEvent _) -> {
            quizService.setQuizType(QuizService.QuizType.TRUE_FALSE_ONLY);
            startQuiz();
        });
        
        // Action: Start MCQ only quiz
        mcqButton.addActionListener((ActionEvent _) -> {
            quizService.setQuizType(QuizService.QuizType.MCQ_ONLY);
            startQuiz();
        });
        
        // Action: Start Fill in Blank only quiz
        fillBlankButton.addActionListener((ActionEvent _) -> {
            quizService.setQuizType(QuizService.QuizType.FILL_BLANK_ONLY);
            startQuiz();
        });

        // Action: Start quiz with all questions
        allQuestionsButton.addActionListener((ActionEvent _) -> {
            quizService.setQuizType(QuizService.QuizType.ALL_QUESTIONS);
            startQuiz();
        });

        // Back to dashboard
        backButton.addActionListener(_ -> {
            if (mainApp != null) {
                mainApp.setVisible(true);
            }
            this.dispose();
        });

        // Add all components to the panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(trueFalseButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(mcqButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(fillBlankButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(infoLabel4);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(infoLabel2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(allQuestionsButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(infoLabel5);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(infoLabel3);
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 90)));
        mainPanel.add(backButton);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    // Starts the quiz and displays the first question
    private void startQuiz() {
        quizService.resetQuiz();
        displayQuestion();
    }
    
    // Shows the screen after quiz completion with final score and options
    private void showCompletionScreen() {
        mainPanel.removeAll();
        
        int totalQuestions = quizService.getTotalQuestions();
        int score = quizService.getScore();
        int percentage = quizService.calculatePercentage();
        
        // Completion message
        JLabel titleLabel = new JLabel("Quiz Completed!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String quizTypeName = quizService.getCurrentQuizType().toString().replace("_", " ");
        JLabel typeLabel = new JLabel("Quiz Type: " + quizTypeName);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        typeLabel.setForeground(Color.DARK_GRAY);

        JLabel scoreLabel = new JLabel("Your Score: " + score + "/" + totalQuestions + " (" + percentage + "%)");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(new Color(0, 153, 76));

        JButton finishQuizButton = createStyledButton("Finish Quiz", new Color(0, 153, 76));
        JButton retakeButton = createStyledButton("Retake Quiz", Color.ORANGE);
        JButton backToMenuButton = createStyledButton("Back to Quiz Menu", new Color(0, 102, 204));
        JButton backButton = createStyledButton("Back to Dashboard", Color.GRAY);

        // Action: Finish quiz and save score
        finishQuizButton.addActionListener((ActionEvent _) -> {
            this.onQuizComplete(username, score, totalQuestions);
    
            if (mainApp != null) {
                mainApp.setVisible(true);
            }
            this.dispose();
        });

        // Action: Retake same quiz type
        retakeButton.addActionListener(_ -> {
            startQuiz();
        });
        
        // Action: Back to quiz selection menu
        backToMenuButton.addActionListener(_ -> {
            showInitialScreen();
        });

        // Back to dashboard
        backButton.addActionListener(_ -> {
            if (mainApp != null) {
                mainApp.setVisible(true);
            }
            this.dispose();
        });

        // Add completion UI to main panel
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(typeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(finishQuizButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(retakeButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backToMenuButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(backButton);
        mainPanel.add(Box.createVerticalGlue());

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // Displays the next question based on its type
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
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new LineBorder(Color.WHITE, 2, true));
        return button;
    }

    private void centerAlignText(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    private JButton createOptionButton() {
        JButton button = new JButton();
        button.setFont(new Font("SansSerif", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(300, 50));
        button.setMaximumSize(new Dimension(300, 50));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBorder(new LineBorder(new Color(100, 50, 150), 1, true));
        button.addActionListener(_ -> handleMCQAnswer(button));
        return button;
    }

    private void styleTFButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(245, 243, 255));
        button.setForeground(new Color(51, 51, 51));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 50));
        button.setOpaque(true);
        button.setBorder(new LineBorder(new Color(100, 50, 150), 1, true));
    }

    // Setters and accessors
    public void startQuiz(String username) {
        this.username = username;
        this.setVisible(true);
    }

    private void displayQuestion() {
        mainPanel.removeAll();
        nextButton.setText("Next");
        
        if (quizService.isQuizComplete()) {
            showCompletionScreen();
            return;
        }

        String question = quizService.getCurrentQuestion();
        questionTextPane.setText(question);
        centerAlignText(questionTextPane);

        if (quizService.isCurrentQuestionMcq()) {
            displayMCQQuestion();
        } else if (quizService.isCurrentQuestionTrueFalse()) {
            displayTrueFalseQuestion();
        } else if (quizService.isCurrentQuestionFillBlank()) {
            displayFillBlankQuestion();
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    private void displayMCQQuestion() {
        String[] options = quizService.getCurrentMcqOptions();
        optionA.setText(options[0]);
        optionB.setText(options[1]);
        optionC.setText(options[2]);
        resetButtons(optionA, optionB, optionC);

        JPanel mcqPanel = new JPanel();
        mcqPanel.setLayout(new BoxLayout(mcqPanel, BoxLayout.Y_AXIS));
        mcqPanel.setBackground(new Color(245, 243, 255));
        mcqPanel.add(questionTextPane);
        mcqPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mcqPanel.add(optionA);
        mcqPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mcqPanel.add(optionB);
        mcqPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mcqPanel.add(optionC);
        mcqPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mcqPanel.add(nextButton);

        mainPanel.add(mcqPanel);
    }
    
    private void displayTrueFalseQuestion() {
        resetButtons(trueButton, falseButton);

        mainPanel.add(questionTextPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(trueButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(falseButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(nextButton);
    }
    
    private void displayFillBlankQuestion() {
        fillBlankTextField.setText("");

        mainPanel.add(questionTextPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(fillBlankTextField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(nextButton);
    }

    private void resetButtons(JButton... buttons) {
        for (JButton btn : buttons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }
    }

    private void handleMCQAnswer(JButton selected) {
        resetButtons(optionA, optionB, optionC);
        selected.setBackground(new Color(232, 223, 250));
        selected.setForeground(Color.BLACK);
        quizService.selectMcqAnswer(selected.getText());
    }

    private void handleTFAnswer(JButton selected) {
        resetButtons(trueButton, falseButton);
        selected.setBackground(new Color(232, 223, 250));
        selected.setForeground(Color.BLACK);
        quizService.selectTrueFalseAnswer(selected == trueButton);
    }

    private void nextQuestion() {
        if (quizService.nextQuestion()) {
            displayQuestion();
        } else {
            showCompletionScreen();
        }
    }

    public QuizScore getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(QuizScore quizScore) {
        this.quizScore = quizScore;
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setQuizCompleteListener(QuizCompleteListener listener) {
        this.quizCompleteListener = listener;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            takeQuizModule quiz = new takeQuizModule("TestUser");
            quiz.setVisible(true);
        });
    }
}