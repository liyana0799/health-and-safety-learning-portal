// Created by Melisa Lai Wan Jiun (99663) - Draft 1 (2025-06-01)
// Tested by Albertina Ngu Yan Si (98025), 2025-06-01

// Lesson.java defines the structure of a lesson in the health and safety learning module.
// It includes properties for the title, content, image path, and video file.   
public class Lesson {
    private final String title;
    private final String content;
    private final String imagePath;
    private final String videoFile;

    // Constructor for Lesson class
    public Lesson(String title, String content, String imagePath, String videoFile) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.videoFile = videoFile;
    }

    // Overloaded constructor for Lesson class without image and video
    public Lesson(String title, String content) {
        this(title, content, null, null);
    }
    // Getters for Lesson class
    // Getters for title, content, imagePath, and videoFile
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImagePath() { return imagePath; }
    public String getVideoFile() { return videoFile; }
    public boolean hasImage() { return imagePath != null; }
    public boolean hasVideo() { return videoFile != null; }
}
