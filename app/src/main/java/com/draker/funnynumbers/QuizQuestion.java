package com.draker.funnynumbers;

/**
 * Класс, представляющий вопрос викторины
 */
public class QuizQuestion {
    private String questionText;
    private int correctAnswer;
    private QuestionType type;
    
    public enum QuestionType {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        DIVISION,
        SQUARE,
        RANDOM_FACT
    }
    
    public QuizQuestion(String questionText, int correctAnswer, QuestionType type) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.type = type;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public int getCorrectAnswer() {
        return correctAnswer;
    }
    
    public QuestionType getType() {
        return type;
    }
    
    public boolean checkAnswer(int userAnswer) {
        return userAnswer == correctAnswer;
    }
    
    @Override
    public String toString() {
        return questionText;
    }
}
