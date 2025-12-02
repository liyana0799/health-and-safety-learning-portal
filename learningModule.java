// Created by Puteri Nur Liyana Imanina Abdullah (100804) - Draft 1 (2025-05-06)
// Created by Melisa Lai Wan Jiun (99663) - Draft 2 (2025-05-08), Draft 3 (2025-05-28), Draft 4 (2025-06-01)
// Tested by Albertina Ngu Yan Si (98025), 2025-06-01

import java.awt.*; // Import necessary AWT classes for GUI components
import java.awt.event.*; // Import necessary AWT event classes for handling mouse events
import java.io.File; // Import File class for file handling
import java.io.IOException; // Import IOException for handling file-related exceptions
import java.util.ArrayList; // Import ArrayList for dynamic array handling
import java.util.List; // Import List interface for list handling
import javax.swing.*; // Import necessary Swing classes for GUI components

// Class learningModule extends JFrame and implements LearningModuleInterface
// This class represents the main learning module application
public class learningModule extends JFrame implements LearningModuleInterface {
    private List<Lesson> lessons;// List to store all lessons
    private JPanel mainPanel;// Main panel to hold the content of the learning module
    private final Main parentMain; // Reference to the main application
    private final String currentUsername; // Store current user's username

    // Constructor with parent reference
    public learningModule(Main parentMain, String username) {
        this.parentMain = parentMain;
        this.currentUsername = username;
        initializeLessons();
        setupUI();
    }

    // Default constructor for backward compatibility
    public learningModule() {
        this.parentMain = null;
        this.currentUsername = null;
        initializeLessons();
        setupUI();
    }

    private void initializeLessons() {
        lessons = new ArrayList<>();
        lessons.add(new Lesson("1. Balanced Diet for Growth", """
                Nowadays, fast food has almost become people's first choice. This phenomenon poses a threat to our health. This is because fast food can lead us to consume excessive amounts of fat and calories, which over time can result in various diseases caused by obesity. For instance, hypertension, high cholesterol, high blood sugar, heart diseases and other issues. Thus, we should keep a balanced diet's habit in our daily lives to stay healthy.
                
                What is balanced diet?
                A balanced diet is essential for maintaining good health and well-being. It includes a variety of foods from different groups to meet all nutritional needs.
                
                A balanced diet should contain:

                i.Fruits and vegetables
                - Provide vitamins, minerals and fiber.

                ii.Proteins
                - Essential for growth and repair of tissues.

                iii.Grains
                - Important source of energy and nutrients.

                iv.Dairy
                - Provides calcium and other essential nutrients.

                v.Healthy Fats
                - Necessary for brain health and energy.

                You can also view the image below as a reference:
                (Click the image to view it in full size)
                """,
                "imageLearningModule/balanced diet.jpeg","videoLearningModule/Balanced Diet   Best food for health.mp4"));
        lessons.add(new Lesson("2. Regular Exercise", """
                Do you still remember the last time you exercised? In this busy era of work and study, people often forget the importance of exercise. In fact, regular exercise brings many benefits to people.
                
                Benefits of Regular Exercise:
                
                i.Improved physical health
                - Helps prevent / manage conditions like stroke, high blood pressure, diabetes and others issues.

                ii.Enhanced mental health
                - Reduces symptoms of depression and anxiety, and boosts mood by releasing endorphins.
                
                iii. Increased energy levels
                - Regular physical activity can help boost your overall energy and reduce fatigue.

                iv.Better sleep quality
                - Engaging in regular exercise can improve sleep patterns and help you fall asleep faster.

                v. Weight management
                - Aids in regulating weight and reducing the risk of obesity.
                
                So, let's start exercise regularly from Now!
                (Click the image to view it in full size)
                """,
                "imageLearningModule/regular exercise.png", "videoLearningModule/How exercise benefits your body.mp4")); 
        lessons.add(new Lesson("3. Danger of Sleep Deprivation","""
               Lack of sleep affects their mental focus, emotional stability, and physical health. 
               In today's world, many teens stay up late due to social media, gaming, and academic pressure. 
               This leads to poor concentration in school, mood swings, higher stress, and even long-term health issues. 
            
               So, let's start sleep early today!
               (Click the image to view it in full size)
                """,
                "imageLearningModule/effects of sleep deprivation.png", "Dangers of Sleep Deprivation - Health and Safety.mp4"));
        lessons.add(new Lesson("4. Manage Stress effectively","""
               Today, common stressors include academic pressure, social media, family expectations, and peer relationships. 
               By recognizing these triggers, we must respond in healthier ways, like practicing time management, setting boundaries online, and talking to someone they trust. 
               We can also use relaxation techniques like deep breathing, meditation, or physical activity to cope with stress.
               
               Let's Manage stress effectively will make your life easier and relax!
               (Click the image to view it in full size)
                """,         
                "imageLearningModule/stress_orig.png", "videoLearningModule/How to stay calm under pressure - Noa Kageyama and Pen-Pen Chen.mp4"));
        lessons.add(new Lesson("5. Hydration and its role","""
                Nowadays, sugary drinks or skip water is the first choice, especially with busy schedules and digital distractions. Hydration can help us stay healthy by preventing fatigue, headaches, and poor focus.
                
                What is hydration and the role?
                - Hydration means keeping the body well-supplied with water. It plays a vital role in health, brain function and emotional balance.
                
                (Click the image to view it in full size)
                """,
                "imageLearningModule/NewHydrationChart.jpg", "videoLearningModule/What would happen if you didn't drink water - Mia Nacamulli.mp4"));
        lessons.add(new Lesson("6. Eye Health","""
                With the rise in screen time due to smartphones, computers, and online learning, we face increasing risks of eye strain, dryness, and myopia (nearsightedness). So that, we should aware that the importance of taking breaks, proper lighting, and regular eye check-ups. 
                
                Let's protect your eyes from today!
                (Click the image to view it in full size)
                """,
                "imageLearningModule/Eye+Care+-+UCLA+Total+Wellness.png", "videoLearningModule/How Phone Screens Damage Our Eyes (And HOW TO BE SAFE).mp4"));
        lessons.add(new Lesson("7. Substance-Free Living","""
                Too stress in learning? 
                Facing peer pressure? 
                Do not let substance like drugs, alcohol or other harmful substances destroy your life. 
                Be aware about the effects for the substance and Focus on our health, goals, and future. 
                Let's live without substance!
                (Click the image to view it in full size)
                """,
                "imageLearningModule/substance-abuse-slide1.png", "videoLearningModule/Teen Substance Use & Abuse (Alcohol, Tobacco, Vaping, Marijuana, and More).mp4"));
        lessons.add(new Lesson("8. Online Gaming Safety","""
                In an era of advanced technology, games have almost become an indispensable part of our lives. 
                However, it comes with risks like cyberbullying, online predators, scams, and exposure to inappropriate content. 
                Therefore, we should aware that share personal info, fall for phishing links, or engage in toxic chats will bring several dangers. 
                We should learn how to protect privacy such as use strong passwords, report abusive behavior, and limit interactions with strangers.
                
                (Click the image to view it in full size)
                """,
                "imageLearningModule/Online Gaming Scams.jpeg", null));
        lessons.add(new Lesson("9. Social Media Awareness","""
                Social media is the platform how we socialize or communicates to others for nowadays. 
                Social media is affecting our behaviors, mental health, and privacy. 
                So that, we should aware the effects to help us use social media responsibly, and develop a healthy digital presence.
                
                Let's live in a healthy and safety environment!
                (Click the image to view it in full size)
                """,
                "imageLearningModule/social media safety.png", null));
        lessons.add(new Lesson("10. Cyberbullying Awareness","""
                Cyberbullying is not a seldom issue in nowadays, but it may occurs everywhen. 
                This is because we spend a lot of time on social media and messaging apps in daily lives. 
                We should aware what we can do if faced cyberbullying and avoid in cyberbullying. 
                
                (Click the image to view it in full size)
                """,
                "imageLearningModule/cyberbullying.jpeg", null));
        lessons.add(new Lesson("11. Self-Defense Awareness","""
                Self-defense awareness is never an inevitable issue in daily lives. We should aware that 
                
                - Recognizing unsafe situations (e.g., being followed, online scams).

                - Trusting instincts and avoiding people or places that feel wrong.

                - Knowing basic safety tips like walking in groups, keeping emergency contacts, and staying in well-lit areas.

                - Using self-defense techniques, if necessary, but also knowing that escaping is often the safest option.
                
                
                In short, let's emphasize self-defense issue and stay safety everywhen.
                (Click the image to view it in full size)
                """,
                "imageLearningModule/Self defense.jpeg", null));
        lessons.add(new Lesson("12. Emergency and Safety Procedures",
                "Knowing what to do in an emergency is crucial for our safety. Let's learn about emergency and safety procedures!\n(Click the image to view it in full size)",
                "imageLearningModule/emergency and safety.jpeg", null));
    }

    private void setupUI() {
        setTitle("Health & Safety Learning Module");// Set the title of the window
        setSize(360, 640);// Set the size of the window
        setResizable(false);// Prevent resizing of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close the application when the window is closed
        setLocationRelativeTo(null);// Center the window on the screen
        setLayout(new BorderLayout());
    
        mainPanel = new JPanel(new BorderLayout()); // Create a main panel with BorderLayout
        mainPanel.add(createMainMenuPanel(), BorderLayout.CENTER);

        add(mainPanel);// Add the main panel to the frame
    }

    // Method to create styled buttons with consistent appearance
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
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    // Method to create the main menu panel
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 248, 252)); // Light blue background

        // Header panel (title + subtitle + back button)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setBackground(new Color(248, 249, 250));

        // Title labels with enhanced styling
        JLabel titleLabel = new JLabel("Lists of Learning Modules");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(33, 37, 41));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Choose any one module by one-click");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setForeground(new Color(108, 117, 125));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitleLabel);

        panel.add(headerPanel, BorderLayout.NORTH);

        // List container with modern styling
        JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        listContainer.setBackground(new Color(248, 249, 250));

        // List model and JList with enhanced appearance
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Lesson l : lessons) {
            listModel.addElement(l.getTitle());
        }

        JList<String> lessonList = new JList<>(listModel);
        lessonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lessonList.setFont(new Font("Arial", Font.PLAIN, 18));
        lessonList.setFixedCellHeight(50);
        lessonList.setBackground(Color.WHITE);
        lessonList.setSelectionBackground(new Color(0, 123, 255, 50));
        lessonList.setSelectionForeground(new Color(0, 123, 255));
        
        // Custom cell renderer for better visual appearance
        lessonList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(222, 226, 230)),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
                
                if (isSelected) {
                    setBackground(new Color(0, 123, 255, 30));
                    setForeground(new Color(0, 123, 255));
                } else {
                    setBackground(Color.WHITE);
                    setForeground(new Color(33, 37, 41));
                }
                
                return this;
            }
        });

        JScrollPane listScroll = new JScrollPane(lessonList);
        listScroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        listScroll.getViewport().setBackground(Color.WHITE);

        listContainer.add(listScroll, BorderLayout.CENTER);
        panel.add(listContainer, BorderLayout.CENTER);

        // Back to Dashboard button (only show if parent exists)
        if (parentMain != null) {
            JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            footerPanel.setBackground(new Color(248, 249, 250));
            footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

            JButton backToDashboardButton = createStyledButton("Back to Homepage", new Color(41, 128, 185));
            backToDashboardButton.setMaximumSize(new Dimension(200, 35));
            backToDashboardButton.setPreferredSize(new Dimension(200, 35));
            backToDashboardButton.setFont(new Font("Arial", Font.BOLD, 12));
            backToDashboardButton.addActionListener(_ -> {
                this.dispose();
                parentMain.setVisible(true);
                parentMain.showDashboard(currentUsername);
            });
            
            footerPanel.add(backToDashboardButton);
            panel.add(footerPanel, BorderLayout.SOUTH);
        }

        // Add mouse click listener (single click)
        lessonList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    int index = lessonList.getSelectedIndex();
                    if (index >= 0) showLesson(lessons.get(index));
                }
            }
        });

        return panel;
    }

    // Method to show a specific lesson
    // This method is called when a lesson is selected from the list  
    @Override
    // Method to display the lesson content in a new panel
    public void showLesson(Lesson lesson) {
        JPanel lessonPanel = new JPanel();
        lessonPanel.setLayout(new BorderLayout()); // Use BorderLayout for better placement
        lessonPanel.setBackground(new Color(245, 248, 252));

        // Create a bottom panel for the back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = createStyledButton("Back to Lists", new Color(0,0,153));
        backButton.addActionListener(_ -> showMainMenu());
        bottomPanel.add(backButton);

        lessonPanel.add(bottomPanel, BorderLayout.SOUTH);// Add the bottom panel to the south of the lesson panel
        // Center panel for content area, image, and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Text content
        JTextArea contentArea = new JTextArea(lesson.getContent());
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setPreferredSize(new Dimension(320, 350));

        // Image display
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);// Center the image label

        if (lesson.hasImage()) {
            try {
                ImageIcon imageIcon = new ImageIcon(lesson.getImagePath());
                Image preview = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);// Scale the image to fit the label
                imageLabel.setIcon(new ImageIcon(preview));// Set the scaled image icon
                imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));// Change cursor to hand when hovering over the image
                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        openFile(lesson.getImagePath());
                    }
                });
            } catch (Exception e) {
                imageLabel.setText("Image could not be loaded.");
            }
        }

        // Button panel for Play Video (centered)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        if (lesson.hasVideo()) {
            JButton playVideo = createStyledButton("Play Video", new Color(30, 144, 255));
            playVideo.addActionListener(_ -> openFile(lesson.getVideoFile()));
            buttonPanel.add(playVideo);
        }

        // Add components to center panel vertically
        centerPanel.add(contentScroll);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(imageLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(buttonPanel);

        lessonPanel.add(centerPanel, BorderLayout.CENTER);

        // Refresh main panel
        mainPanel.removeAll();
        mainPanel.add(lessonPanel, BorderLayout.CENTER);
        revalidate();// Refresh the layout of the main panel
        repaint();// Repaint the main panel to show the new content
    }

    // Method to show the main menu
    // This method is called when the user clicks the back button in the lesson view
    @Override
    public void showMainMenu() {
        mainPanel.removeAll();// Clear the main panel
        mainPanel.add(createMainMenuPanel(), BorderLayout.CENTER);
        revalidate();// Refresh the layout of the main panel
        repaint();// Repaint the main panel to show the main menu
    }

    // Method to open a file (image or video) using the default application
    // This method uses the Desktop class to open the file
    private void openFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "File not found: " + path, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}