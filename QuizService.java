// Created by Albertina Ngu Yan Si (98025) - Draft 1 (2025-06-01)
// Tested by Thian Shing Hui (101214), 2025-06-01

/* The QuizService class serves as the business logic core for quiz operations,
 * managing question storage, answer validation, scoring, and quiz progression
 * through configurable quiz types (MCQ, True/False, Fill-in-the-blank).
 */

import java.util.Arrays;
import java.util.List;

public class QuizService {
    
    // Class to represent Fill-in-the-blank questions
    private static class FillInBlankQuestion {
        String question;
        List<String> answers;
        
        public FillInBlankQuestion(String question, List<String> answers) {
            this.question = question;
            this.answers = answers;
        }
    }
    
    // Class to represent True/False questions
    private static class TrueFalseQuestion {
        String question;
        boolean correctAnswer;
        
        public TrueFalseQuestion(String question, boolean correctAnswer) {
            this.question = question;
            this.correctAnswer = correctAnswer;
        }
    }
    
    // Quiz type enum
    public enum QuizType {
        ALL_QUESTIONS,
        MCQ_ONLY,
        TRUE_FALSE_ONLY,
        FILL_BLANK_ONLY
    }
    
    // Fill-in-the-blank questions 
    private final List<FillInBlankQuestion> fillInBlankQuestions = Arrays.asList(
        new FillInBlankQuestion("Social media affects our behaviors, mental health, and __________.", Arrays.asList("privacy")),
        new FillInBlankQuestion("Hydration helps lubricate your __________ and improves tissue mobility.", Arrays.asList("joints")),
        new FillInBlankQuestion("In case of an emergency, the first step is to _________ 999.", Arrays.asList("call")),
        new FillInBlankQuestion("It is important to stay ________ and say no to drugs, alcohol, and harmful substances.", Arrays.asList("substance-free")),
        new FillInBlankQuestion("Before you post or send something online, you should always stop and ________.", Arrays.asList("think"))
    );

    // Multiple choice questions
    private final String[][] mcqQuestions = {
        {"Which of the following nutrients is essential for building and repairing tissues?", "Fiber", "Protein", "Vitamin C", "Protein"},
        {"How does regular exercise contribute to overall wellness?", "By promoting social isolation", "By leading to weight gain", "By improving physical health", "By improving physical health"},
        {"Which of the following activities can help reduce stress levels?", "Engaging in hobbies and interests", "Excessive consumption of alcohol", "Avoiding sleep and rest", "Engaging in hobbies and interests"},
        {"What is one of the most immediate effects of sleep deprivation?", "Enhanced memory", "Impaired concentration", "Improved immune function", "Impaired concentration"},
        {"How can you protect yourself from online scams?", "Ignore warning and click on links", "Avoid providing personal information", "Share your credit card details", "Avoid providing personal information"},
        {"What is one risk of prolonged screen time without breaks?", "Dehydration", "Eye strain and myopia", "Hearing loss", "Eye strain and myopia"},
        {"What is a benefit of setting up two-factor authentication?", "Easier to access", "Extra layer of protection", "Disables third-party apps", "Extra layer of protection"},
        {"Which of the following is a consequence of substance abuse?", "Improved concentration", "Better health", "Psychological and social problems", "Psychological and social problems"}
    };

     // True/false questions
    private final List<TrueFalseQuestion> trueFalseQuestions = Arrays.asList(
        new TrueFalseQuestion("Taking a walk is one suggested alternative activity to give your eyes a break.", true),
        new TrueFalseQuestion("Deleting third-party apps you don't use can help protect your account.", true),
        new TrueFalseQuestion("Being dehydrated can lead to fatigue, muscle cramps, and headaches.", true),
        new TrueFalseQuestion("It is not necessary to provide specific information when calling 999.", false),
        new TrueFalseQuestion("Reporting serious cyberbullying to the police may be necessary if someone's safety is at risk.", true),
        new TrueFalseQuestion("Academic pressure, family expectations, social media, and peer relationships are common modern stressors.", true),
        new TrueFalseQuestion("Proteins help in tissue growth and repair.", true),
        new TrueFalseQuestion("Exercise improves heart health and reduces disease risk.", true)
    );

    // Quiz state
    private QuizType currentQuizType;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean answered = false;
    private String selectedMcqAnswer;
    private Boolean selectedTfAnswer;
    private String fillBlankAnswer;

    public QuizService(QuizType quizType) {
        this.currentQuizType = quizType;
    }

    // Getters for quiz data
    public int getTotalQuestions() {
        switch (currentQuizType) {
            case MCQ_ONLY:
                return mcqQuestions.length;
            case TRUE_FALSE_ONLY:
                return trueFalseQuestions.size();
            case FILL_BLANK_ONLY:
                return fillInBlankQuestions.size();
            case ALL_QUESTIONS:
            default:
                return mcqQuestions.length + trueFalseQuestions.size() + fillInBlankQuestions.size();
        }
    }

    public String getCurrentQuestion() {
        if (currentQuestionIndex >= getTotalQuestions()) {
            return null;
        }

        switch (currentQuizType) {
            case MCQ_ONLY:
                return mcqQuestions[currentQuestionIndex][0];
            case TRUE_FALSE_ONLY:
                return trueFalseQuestions.get(currentQuestionIndex).question;
            case FILL_BLANK_ONLY:
                return fillInBlankQuestions.get(currentQuestionIndex).question;
            case ALL_QUESTIONS:
            default:
                return getAllQuestionsSequentially().get(currentQuestionIndex);
        }
    }

    public String[] getCurrentMcqOptions() {
        if (currentQuizType == QuizType.MCQ_ONLY || currentQuizType == QuizType.ALL_QUESTIONS) {
            if (currentQuizType == QuizType.ALL_QUESTIONS) {
                if (currentQuestionIndex < mcqQuestions.length) {
                    return new String[]{
                        mcqQuestions[currentQuestionIndex][1],
                        mcqQuestions[currentQuestionIndex][2],
                        mcqQuestions[currentQuestionIndex][3]
                    };
                }
            } else {
                if (currentQuestionIndex < mcqQuestions.length) {
                    return new String[]{
                        mcqQuestions[currentQuestionIndex][1],
                        mcqQuestions[currentQuestionIndex][2],
                        mcqQuestions[currentQuestionIndex][3]
                    };
                }
            }
        }
        return null;
    }

    public boolean isCurrentQuestionMcq() {
        if (currentQuizType == QuizType.MCQ_ONLY) {
            return true;
        } else if (currentQuizType == QuizType.ALL_QUESTIONS) {
            return currentQuestionIndex < mcqQuestions.length;
        }
        return false;
    }

    public boolean isCurrentQuestionTrueFalse() {
        if (currentQuizType == QuizType.TRUE_FALSE_ONLY) {
            return true;
        } else if (currentQuizType == QuizType.ALL_QUESTIONS) {
            return currentQuestionIndex >= mcqQuestions.length && 
                   currentQuestionIndex < mcqQuestions.length + trueFalseQuestions.size();
        }
        return false;
    }

    public boolean isCurrentQuestionFillBlank() {
        if (currentQuizType == QuizType.FILL_BLANK_ONLY) {
            return true;
        } else if (currentQuizType == QuizType.ALL_QUESTIONS) {
            return currentQuestionIndex >= mcqQuestions.length + trueFalseQuestions.size();
        }
        return false;
    }

    // User answer handling
    public void selectMcqAnswer(String answer) {
        this.selectedMcqAnswer = answer;
        this.answered = true;
    }

    public void selectTrueFalseAnswer(boolean answer) {
        this.selectedTfAnswer = answer;
        this.answered = true;
    }

    public void setFillBlankAnswer(String answer) {
        this.fillBlankAnswer = answer;
        this.answered = !answer.trim().isEmpty();
    }

    // Quiz progression
    public boolean nextQuestion() {
        if (!answered) {
            return false;
        }

        evaluateCurrentAnswer();
        currentQuestionIndex++;
        resetAnswerState();
        
        return currentQuestionIndex < getTotalQuestions();
    }

    private void evaluateCurrentAnswer() {
        switch (currentQuizType) {
            case MCQ_ONLY:
                evaluateMcqAnswer();
                break;
            case TRUE_FALSE_ONLY:
                evaluateTrueFalseAnswer();
                break;
            case FILL_BLANK_ONLY:
                evaluateFillBlankAnswer();
                break;
            case ALL_QUESTIONS:
            default:
                evaluateAllQuestionsAnswer();
                break;
        }
    }

    private void evaluateMcqAnswer() {
        if (selectedMcqAnswer != null && 
            selectedMcqAnswer.equals(mcqQuestions[currentQuestionIndex][4])) {
            score++;
        }
    }

    private void evaluateTrueFalseAnswer() {
        if (selectedTfAnswer != null && 
            selectedTfAnswer == trueFalseQuestions.get(currentQuestionIndex).correctAnswer) {
            score++;
        }
    }

    private void evaluateFillBlankAnswer() {
        if (fillBlankAnswer != null && !fillBlankAnswer.trim().isEmpty()) {
            FillInBlankQuestion q = fillInBlankQuestions.get(currentQuestionIndex);
            if (q.answers.stream().anyMatch(ans -> ans.equalsIgnoreCase(fillBlankAnswer.trim()))) {
                score++;
            }
        }
    }

    private void evaluateAllQuestionsAnswer() {
        int mcqCount = mcqQuestions.length;
        int tfCount = trueFalseQuestions.size();

        if (currentQuestionIndex < mcqCount) {
            if (selectedMcqAnswer != null && 
                selectedMcqAnswer.equals(mcqQuestions[currentQuestionIndex][4])) {
                score++;
            }
        } else if (currentQuestionIndex < mcqCount + tfCount) {
            if (selectedTfAnswer != null && 
                selectedTfAnswer == trueFalseQuestions.get(currentQuestionIndex - mcqCount).correctAnswer) {
                score++;
            }
        } else if (currentQuestionIndex < mcqCount + tfCount + fillInBlankQuestions.size()) {
            if (fillBlankAnswer != null && !fillBlankAnswer.trim().isEmpty()) {
                FillInBlankQuestion q = fillInBlankQuestions.get(currentQuestionIndex - mcqCount - tfCount);
                if (q.answers.stream().anyMatch(ans -> ans.equalsIgnoreCase(fillBlankAnswer.trim()))) {
                    score++;
                }
            }
        }
    }

    private void resetAnswerState() {
        answered = false;
        selectedMcqAnswer = null;
        selectedTfAnswer = null;
        fillBlankAnswer = null;
    }

    // Helper methods
    private List<String> getAllQuestionsSequentially() {
        // This is just for getting the question text, not the full question data
        // Used by getCurrentQuestion() for ALL_QUESTIONS type
        return Arrays.asList(
            mcqQuestions[0][0], mcqQuestions[1][0], mcqQuestions[2][0], mcqQuestions[3][0],
            mcqQuestions[4][0], mcqQuestions[5][0], mcqQuestions[6][0], mcqQuestions[7][0],
            trueFalseQuestions.get(0).question, trueFalseQuestions.get(1).question,
            trueFalseQuestions.get(2).question, trueFalseQuestions.get(3).question,
            trueFalseQuestions.get(4).question, trueFalseQuestions.get(5).question,
            trueFalseQuestions.get(6).question, trueFalseQuestions.get(7).question,
            fillInBlankQuestions.get(0).question, fillInBlankQuestions.get(1).question,
            fillInBlankQuestions.get(2).question, fillInBlankQuestions.get(3).question,
            fillInBlankQuestions.get(4).question
        );
    }

    // Getters for quiz state
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getScore() {
        return score;
    }

    public boolean isAnswered() {
        return answered;
    }

    public QuizType getCurrentQuizType() {
        return currentQuizType;
    }

    public void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        resetAnswerState();
    }

    public void setQuizType(QuizType quizType) {
        this.currentQuizType = quizType;
        resetQuiz();
    }

    public boolean isQuizComplete() {
        return currentQuestionIndex >= getTotalQuestions();
    }

    public int calculatePercentage() {
        return (int) (((double) score / getTotalQuestions()) * 100);
    }
}